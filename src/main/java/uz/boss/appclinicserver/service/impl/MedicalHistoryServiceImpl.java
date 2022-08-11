package uz.boss.appclinicserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.HistoryAnswerReqDto;
import uz.boss.appclinicserver.dto.req.MedicalHistoryEditReqDto;
import uz.boss.appclinicserver.dto.req.MedicalHistoryReqDto;
import uz.boss.appclinicserver.dto.resp.MedicalHistoryRespDto;
import uz.boss.appclinicserver.entity.*;
import uz.boss.appclinicserver.enums.DirType;
import uz.boss.appclinicserver.enums.HistoryStatus;
import uz.boss.appclinicserver.mapper.MedicalHistoryMapper;
import uz.boss.appclinicserver.repository.AttachmentRepo;
import uz.boss.appclinicserver.repository.DoctorRepo;
import uz.boss.appclinicserver.repository.MedicalHistoryRepo;
import uz.boss.appclinicserver.repository.PatientRepo;
import uz.boss.appclinicserver.service.AttachmentService;
import uz.boss.appclinicserver.service.MedicalHistoryService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Author: Muhammad
 * Date: 10.07.2022
 * Time: 13:57
 */
@Service
@RequiredArgsConstructor
public class MedicalHistoryServiceImpl implements MedicalHistoryService {
    private final AttachmentService attachmentService;
    private final MedicalHistoryRepo medicalHistoryRepo;
    private final DoctorRepo doctorRepo;
    private final AttachmentRepo attachmentRepo;
    private final PatientRepo patientRepo;
    private final MedicalHistoryMapper medicalHistoryMapper;

    @Override
    public ApiResponse<List<MedicalHistoryRespDto>> getByPatientId(UUID patientId) {
        List<MedicalHistory> medicalHistories = medicalHistoryRepo.findAllByPatientId(patientId, Sort.by(Sort.Order.desc("createdAt")));
        return ApiResponse.successResponse(
                medicalHistories.stream().map(medicalHistoryMapper::toHistoryDto).collect(Collectors.toList())
        );
    }

    @Override
    public ApiResponse<List<MedicalHistoryRespDto>> getFilteredPatientId(UUID patientId, Integer month, Integer year) {
        LocalDateTime from = LocalDateTime.of(LocalDate.of(year, Month.of(month), 1), LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(LocalDate.of(year, Month.of(month + 1), 1), LocalTime.MIN);

        List<MedicalHistory> histories = medicalHistoryRepo.findAllByPatientIdAndDateBeforeAndDateAfter(patientId, to, from,Sort.by(Sort.Order.desc("createdAt")));
        return ApiResponse.successResponse(
                histories.stream().map(medicalHistoryMapper::toHistoryDto).collect(Collectors.toList())
        );
    }

    @Override
    public ApiResponse<List<MedicalHistoryRespDto>> getByDoctorId(User user) {
        Optional<Doctor> optionalDoctor = doctorRepo.findByUserId(user.getId());
        if (optionalDoctor.isEmpty()) {
            return ApiResponse.notFound("Doctor");
        }
        Doctor doctor = optionalDoctor.get();
        List<MedicalHistory> medicalHistories = medicalHistoryRepo.findAllByDoctorId(doctor.getId(), Sort.by(Sort.Order.desc("createdAt")));
        return ApiResponse.successResponse(
                medicalHistories.stream().map(medicalHistoryMapper::toHistoryDto).collect(Collectors.toList())
        );
    }

    @Override
    public ApiResponse<?> getFiles(UUID id) throws IOException {
        Optional<MedicalHistory> optionalMedicalHistory = medicalHistoryRepo.findById(id);
        if (optionalMedicalHistory.isEmpty()) {
            return ApiResponse.notFound("History");
        }
        MedicalHistory history = optionalMedicalHistory.get();
        List<String> files = new ArrayList<>();
        List<String> images = new ArrayList<>();
        history.getFiles().forEach(attachment -> {
            if (attachment.getDirectory().equals(DirType.FILE)) {
                files.add(attachment.getName());
            } else {
                images.add(attachment.getName());
            }
        });

        Map<String, String> fileFromServer = attachmentService.getFileFromFtpServer(files, "file");
        Map<String, String> imageFromServer = attachmentService.getFileFromFtpServer(images, "image");

        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(fos);

        fileFromServer.forEach((key, value) -> {
            ZipEntry zipEntry = new ZipEntry(key);
            try {
                zip.putNextEntry(zipEntry);
                zip.write(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(value));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        imageFromServer.forEach((key, value) -> {
            ZipEntry zipEntry = new ZipEntry(key);
            try {
                zip.putNextEntry(zipEntry);
                zip.write(Base64.decodeBase64(value));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        zip.close();
        fos.close();

        return ApiResponse.successResponse(fos.toByteArray());
    }

    @Override
    public ApiResponse<?> create(User user, MedicalHistoryReqDto dto) {
        Optional<Doctor> optionalDoctor = doctorRepo.findByUserId(user.getId());
        if (optionalDoctor.isEmpty()) {
            return ApiResponse.notFound("Doctor");
        }
        Doctor doctor = optionalDoctor.get();
        MedicalHistory medicalHistory = MedicalHistory.builder()
                .complaint(dto.getComplaint())
                .disease(dto.getDisease())
                .doctorId(doctor.getId())
                .clinicId(doctor.getClinicId())
                .status(HistoryStatus.OPEN)
                .date(LocalDateTime.now())
                .build();

        if (Objects.nonNull(dto.getPatientId())) {
            Optional<Patient> optionalPatient = patientRepo.findById(dto.getPatientId());
            if (optionalPatient.isEmpty()) {
                return ApiResponse.notFound("Patient");
            }
            medicalHistory.setPatientId(dto.getPatientId());
        }
        medicalHistoryRepo.save(medicalHistory);

        return ApiResponse.successResponse(medicalHistoryMapper.toHistoryDto(medicalHistory));
    }

    @Override
    public ApiResponse<?> answer(UUID id, User user, HistoryAnswerReqDto dto) {
        Optional<MedicalHistory> optionalMedicalHistory = medicalHistoryRepo.findById(id);
        if (optionalMedicalHistory.isEmpty()) {
            return ApiResponse.notFound("History");
        }
        MedicalHistory history = optionalMedicalHistory.get();
        Optional<Doctor> optionalDoctor = doctorRepo.findByUserId(user.getId());
        if (optionalDoctor.isEmpty()) {
            return ApiResponse.notFound("Doctor");
        }
        if (!history.getDoctorId().equals(optionalDoctor.get().getId())) {
            return ApiResponse.error("This is not your patient", HttpStatus.BAD_REQUEST);
        }
        history.setDiagnosis(dto.getDiagnosis());
        history.setTreatment(dto.getTreatment());
        Set<Attachment> files = new HashSet<>();
        for (UUID fileId : dto.getFiles()) {
            Optional<Attachment> optionalAttachment = attachmentRepo.findById(fileId);
            optionalAttachment.ifPresent(files::add);
        }
        history.setFiles(files);
        history.setStatus(HistoryStatus.CLOSED);
        medicalHistoryRepo.save(history);

        return ApiResponse.successResponse(medicalHistoryMapper.toHistoryDto(history));
    }

    @Override
    public ApiResponse<?> edit(UUID id, User user, MedicalHistoryEditReqDto dto) {
        Optional<MedicalHistory> optionalMedicalHistory = medicalHistoryRepo.findById(id);
        if (optionalMedicalHistory.isEmpty()) {
            return ApiResponse.notFound("History");
        }
        MedicalHistory history = optionalMedicalHistory.get();
        Optional<Doctor> optionalDoctor = doctorRepo.findByUserId(user.getId());
        if (optionalDoctor.isEmpty()) {
            return ApiResponse.notFound("Doctor");
        }
        if (!history.getDoctorId().equals(optionalDoctor.get().getId())) {
            return ApiResponse.error("This is not your patient", HttpStatus.BAD_REQUEST);
        }
        history.setTreatment(dto.getTreatment());

        history.setDiagnosis(dto.getDiagnosis());
        history.setComplaint(dto.getComplaint());
        history.setDisease(dto.getDisease());
        Set<Attachment> files = new HashSet<>();
        for (UUID fileId : dto.getFiles()) {
            Optional<Attachment> optionalAttachment = attachmentRepo.findById(fileId);
            optionalAttachment.ifPresent(files::add);
        }
        history.setFiles(files);
        medicalHistoryRepo.save(history);
        return ApiResponse.successResponse(medicalHistoryMapper.toHistoryDto(history));
    }

    @Override
    public ApiResponse<?> delete(UUID id) {
        Optional<MedicalHistory> optionalMedicalHistory = medicalHistoryRepo.findById(id);
        if (optionalMedicalHistory.isEmpty()) {
            return ApiResponse.notFound("History");
        }
        MedicalHistory history = optionalMedicalHistory.get();
        for (Attachment file : history.getFiles()) {
            attachmentService.delete(file.getId());
        }
        medicalHistoryRepo.deleteById(id);
        return ApiResponse.successResponse("Deleted");
    }
}
