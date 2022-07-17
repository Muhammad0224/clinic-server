package uz.boss.appclinicserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.DoctorEditReqDto;
import uz.boss.appclinicserver.dto.req.DoctorReqDto;
import uz.boss.appclinicserver.dto.resp.DoctorRespDto;
import uz.boss.appclinicserver.entity.Attachment;
import uz.boss.appclinicserver.entity.Doctor;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.enums.DirType;
import uz.boss.appclinicserver.mapper.DoctorMapper;
import uz.boss.appclinicserver.repository.AttachmentRepo;
import uz.boss.appclinicserver.repository.DoctorRepo;
import uz.boss.appclinicserver.repository.RoleRepo;
import uz.boss.appclinicserver.repository.UserRepo;
import uz.boss.appclinicserver.service.AttachmentService;
import uz.boss.appclinicserver.service.DoctorService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Author: Muhammad
 * Date: 04.07.2022
 * Time: 17:42
 */
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepo doctorRepo;
    private final AttachmentRepo attachmentRepo;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final AttachmentService attachmentService;
    private final DoctorMapper doctorMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<List<DoctorRespDto>> get() {
        return ApiResponse.successResponse(doctorRepo.findAll(Sort.by(Sort.Order.desc("createdAt"))).stream()
                .map(doctorMapper::toDoctorDto).collect(Collectors.toList()));
    }

    @Override
    public ApiResponse<List<DoctorRespDto>> get(User user) {
        return ApiResponse.successResponse(
                doctorRepo.findAllByClinicId(user.getClinicId(), Sort.by(Sort.Order.desc("createdAt")))
                        .stream().map(doctorMapper::toDoctorDto).collect(Collectors.toList()));
    }

    @SneakyThrows
    @Override
    public ApiResponse<?> getFiles(UUID id, HttpServletResponse response) {
        Optional<Doctor> optionalDoctor = doctorRepo.findById(id);
        if (optionalDoctor.isPresent()) {
            List<String> files = new ArrayList<>();
            List<String> images = new ArrayList<>();

            Doctor doctor = optionalDoctor.get();

            doctor.getFiles().forEach(attachment -> {
                if (attachment.getDirectory().equals(DirType.FILE)) {
                    files.add(attachment.getName());
                } else {
                    images.add(attachment.getName());
                }
            });

            Map<String, String> fileFromServer = attachmentService.getFileFromFtpServer(files, "file");
            Map<String, String> imageFromServer = attachmentService.getFileFromFtpServer(images, "image");

//            FileOutputStream fos = new FileOutputStream(doctor.getFullName() + ".zip");
            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(fos);
//            ZipOutputStream zip = new ZipOutputStream(fos);

            fileFromServer.forEach((key, value) -> {
                ZipEntry zipEntry = new ZipEntry(key);
                try {
                    zip.putNextEntry(zipEntry);
                    zip.write(Base64.decodeBase64(value));

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

//            FileCopyUtils.copy(fos.toByteArray(), response.getOutputStream());
            return ApiResponse.successResponse(fos.toByteArray());
        }
        return ApiResponse.notFound("Doktor");
    }

    @Override
    public ApiResponse<?> create(User user, DoctorReqDto dto) {
        if (doctorRepo.existsByPnfl(dto.getPnfl())) {
            return ApiResponse.error("Pnfl has already existed", HttpStatus.CONFLICT);
        }

        if (userRepo.existsByUsername(dto.getUsername())) {
            return ApiResponse.error("This username has already existed", HttpStatus.CONFLICT);
        }

        User doktor = User.builder()
                .clinicId(user.getClinicId())
                .role(roleRepo.findByName("DOKTOR").get())
                .password(passwordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername()).build();
        userRepo.save(doktor);

        Set<Attachment> files = new HashSet<>();
        for (UUID fileId : dto.getFiles()) {
            Optional<Attachment> optionalAttachment = attachmentRepo.findById(fileId);
            optionalAttachment.ifPresent(files::add);
        }

        Doctor doctor = Doctor.builder()
                .fullName(dto.getFullName())
                .clinicId(user.getClinicId())
                .pnfl(dto.getPnfl())
                .userId(doktor.getId())
                .files(files)
                .build();
        doctorRepo.save(doctor);
        return ApiResponse.successResponse(doctorMapper.toDoctorDto(doctor));
    }

    @Override
    public ApiResponse<?> activate(UUID id) {
        Optional<Doctor> optionalDoctor = doctorRepo.findById(id);
        if (optionalDoctor.isEmpty()) {
            return ApiResponse.notFound("Doktor");
        }
        User user = optionalDoctor.get().getUser();
        user.setEnabled(true);
        userRepo.save(user);
        return ApiResponse.successResponse("Activated");
    }

    @Override
    public ApiResponse<?> edit(UUID id, DoctorEditReqDto dto) {
        Optional<Doctor> optionalDoctor = doctorRepo.findById(id);
        if (optionalDoctor.isEmpty()) {
            return ApiResponse.notFound("Doktor");
        }

        if (doctorRepo.existsByPnflAndIdNot(dto.getPnfl(), id)) {
            return ApiResponse.error("Pnfl has already existed", HttpStatus.CONFLICT);
        }
        Doctor doctor = optionalDoctor.get();
        User user = doctor.getUser();

        if (userRepo.existsByUsernameAndIdNot(dto.getUsername(), user.getId())){
            return ApiResponse.error("Username has already existed", HttpStatus.CONFLICT);
        }

        Set<Attachment> files = new HashSet<>();
        for (UUID fileId : dto.getFiles()) {
            Optional<Attachment> optionalAttachment = attachmentRepo.findById(fileId);
            optionalAttachment.ifPresent(files::add);
        }


        doctor.setFiles(files);
        doctor.setFullName(dto.getFullName());
        doctor.setPnfl(dto.getPnfl());
        doctorRepo.save(doctor);


        user.setUsername(dto.getUsername());
        userRepo.save(user);
        return ApiResponse.successResponse(doctorMapper.toDoctorDto(doctor));
    }

    @Override
    public ApiResponse<?> delete(UUID id) {
        Optional<Doctor> optionalDoctor = doctorRepo.findById(id);
        if (optionalDoctor.isEmpty()) {
            return ApiResponse.notFound("Doctor");
        }
        User user = optionalDoctor.get().getUser();
        user.setEnabled(false);
        userRepo.save(user);
        return ApiResponse.successResponse("Deleted");
    }
}
