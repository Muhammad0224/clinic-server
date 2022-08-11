package uz.boss.appclinicserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.PatientReqDto;
import uz.boss.appclinicserver.dto.req.PatientSearchReqDto;
import uz.boss.appclinicserver.dto.resp.PatientRespDto;
import uz.boss.appclinicserver.entity.Attachment;
import uz.boss.appclinicserver.entity.Patient;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.mapper.PatientMapper;
import uz.boss.appclinicserver.repository.AttachmentRepo;
import uz.boss.appclinicserver.repository.PatientRepo;
import uz.boss.appclinicserver.service.PatientService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Author: Muhammad
 * Date: 30.06.2022
 * Time: 11:30
 */
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final AttachmentRepo attachmentRepo;
    private final PatientMapper patientMapper;

    @Override
    public ApiResponse<List<PatientRespDto>> get() {
        return ApiResponse.successResponse(patientRepo.findAll(Sort.by(Sort.Order.desc("createdAt")))
                .stream().map(patientMapper::toPatientDto).collect(Collectors.toList()));
    }

    @Override
    public ApiResponse<List<PatientRespDto>> get(User user) {
        return ApiResponse.successResponse(
                patientRepo.findAllByClinicId(user.getClinicId(), Sort.by(Sort.Order.desc("createdAt")))
                        .stream().map(patientMapper::toPatientDto).collect(Collectors.toList())
        );
    }

    @Override
    public ApiResponse<PatientRespDto> get(UUID id) {
        Optional<Patient> optionalPatient = patientRepo.findById(id);
        if (optionalPatient.isEmpty()) {
            return ApiResponse.notFound("Patient");
        }
        Patient patient = optionalPatient.get();
        return ApiResponse.successResponse(patientMapper.toPatientDto(patient));
    }

    @Override
    public ApiResponse<PatientRespDto> cardNumber(String cardNumber) {
        Optional<Patient> optionalPatient = patientRepo.findByUniqueCode(cardNumber);
        if (optionalPatient.isEmpty()) {
            return ApiResponse.notFound("Patient");
        }
        Patient patient = optionalPatient.get();
        return ApiResponse.successResponse(patientMapper.toPatientDto(patient));
    }

    @Override
    public ApiResponse<List<PatientRespDto>> filter(PatientSearchReqDto dto) {
        List<Patient> patients = patientRepo.findAllByUniqueCodeStartsWith(dto.getCardNumber());
        return ApiResponse.successResponse(patients.stream().map(patientMapper::toPatientDto).collect(Collectors.toList()));
    }

    @Override
    public ApiResponse<PatientRespDto> create(User user, PatientReqDto dto) {
        if (patientRepo.existsByPnfl(dto.getPnfl())) {
            return ApiResponse.error("Pnfl has already existed", HttpStatus.CONFLICT);
        }
        if (Objects.nonNull(dto.getPassportId())) {
            Optional<Attachment> optionalAttachment = attachmentRepo.findById(dto.getPassportId());
            if (optionalAttachment.isEmpty()) {
                return ApiResponse.notFound("Passport");
            }
        }

        String uniqueCode = "";
        do {
            uniqueCode = generateUniqueCode();
        } while (patientRepo.existsByUniqueCode(uniqueCode));

        Patient patient = Patient.builder()
                .fullName(dto.getFullName())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .pnfl(dto.getPnfl())
                .clinicId(user.getClinicId())
                .uniqueCode(uniqueCode)
                .dateOfBirth(LocalDate.parse(dto.getDateOfBirth()))
                .passportId(dto.getPassportId())
                .build();
        patientRepo.save(patient);
        return ApiResponse.successResponse(patientMapper.toPatientDto(patient));
    }

    @Override
    public ApiResponse<?> edit(UUID id, PatientReqDto dto) {
        Optional<Patient> optionalPatient = patientRepo.findById(id);
        if (optionalPatient.isEmpty()) {
            return ApiResponse.notFound("Patient");
        }
        Patient patient = optionalPatient.get();
        if (patientRepo.existsByPnflAndIdNot(dto.getPnfl(), id)) {
            return ApiResponse.error("Pnfl has already existed", HttpStatus.CONFLICT);
        }
        Optional<Attachment> optionalAttachment = attachmentRepo.findById(dto.getPassportId());
        if (optionalAttachment.isEmpty()) {
            return ApiResponse.notFound("Passport");
        }

        patient.setAddress(dto.getAddress());
        patient.setFullName(dto.getFullName());
        patient.setPnfl(dto.getPnfl());
        patient.setPhoneNumber(dto.getPhoneNumber());
        patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        patient.setPassportId(dto.getPassportId());
        patientRepo.save(patient);
        return ApiResponse.successResponse(patientMapper.toPatientDto(patient));
    }

    private String generateUniqueCode() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            result.append((int) (Math.random() * 10));
        }
        return result.toString();
    }
}
