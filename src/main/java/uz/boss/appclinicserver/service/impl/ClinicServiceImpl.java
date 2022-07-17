package uz.boss.appclinicserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.ClinicReqDto;
import uz.boss.appclinicserver.dto.resp.ClinicRespDto;
import uz.boss.appclinicserver.entity.Clinic;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.mapper.ClinicMapper;
import uz.boss.appclinicserver.repository.ClinicRepo;
import uz.boss.appclinicserver.repository.UserRepo;
import uz.boss.appclinicserver.service.ClinicService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Author: Muhammad
 * Date: 30.06.2022
 * Time: 15:26
 */
@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {
    private final ClinicRepo clinicRepo;
    private final UserRepo userRepo;
    private final ClinicMapper clinicMapper;

    @Override
    public ApiResponse<List<ClinicRespDto>> get() {
        return ApiResponse.successResponse(
                clinicRepo.findAll(Sort.by(Sort.Order.desc("createdAt"))).stream().map(clinicMapper::toClinicDto).collect(Collectors.toList())
        );
    }

    @Override
    public ApiResponse<ClinicRespDto> create(ClinicReqDto dto) {
        Clinic clinic = new Clinic();
        clinic.setAvatar(dto.getAvatar());
        clinic.setName(dto.getName());
        clinic.setAvatarId(dto.getAvatarId());
        clinicRepo.save(clinic);
        return ApiResponse.successResponse(clinicMapper.toClinicDto(clinic));
    }

    @Override
    public ApiResponse<?> activate(UUID id) {
        Optional<Clinic> optionalClinic = clinicRepo.findById(id);
        if (optionalClinic.isEmpty()) {
            return ApiResponse.notFound("Cliinc");
        }
        Clinic clinic = optionalClinic.get();
        clinic.setActive(true);
        clinicRepo.save(clinic);
        return ApiResponse.successResponse("Activate");
    }

    @Override
    public ApiResponse<?> edit(UUID id, ClinicReqDto dto) {
        Optional<Clinic> optionalClinic = clinicRepo.findById(id);
        if (optionalClinic.isEmpty()) {
            return ApiResponse.notFound("Clinic");
        }
        Clinic clinic = optionalClinic.get();
        clinic.setAvatar(dto.getAvatar());
        clinic.setName(dto.getName());
        clinic.setAvatarId(dto.getAvatarId());
        clinicRepo.save(clinic);
        return ApiResponse.successResponse(clinicMapper.toClinicDto(clinic));
    }

    @Override
    public ApiResponse<?> delete(UUID id) {
        Optional<Clinic> optionalClinic = clinicRepo.findById(id);
        if (optionalClinic.isEmpty()) {
            return ApiResponse.notFound("Clinic");
        }
        Clinic clinic = optionalClinic.get();
        clinic.setActive(false);
        clinicRepo.save(clinic);
        List<User> users = userRepo.findAllByClinicId(id);
        users.forEach(user -> user.setEnabled(false));
        userRepo.saveAll(users);
        return ApiResponse.successResponse("Deleted");
    }
}
