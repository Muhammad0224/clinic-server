package uz.boss.appclinicserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.ClinicReqDto;
import uz.boss.appclinicserver.dto.resp.ClinicRespDto;
import uz.boss.appclinicserver.entity.Clinic;
import uz.boss.appclinicserver.mapper.ClinicMapper;
import uz.boss.appclinicserver.repository.ClinicRepo;
import uz.boss.appclinicserver.service.ClinicService;

import java.util.List;
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
        clinicRepo.save(clinic);
        return ApiResponse.successResponse(clinicMapper.toClinicDto(clinic));
    }

    @Override
    public ApiResponse<?> delete(UUID id) {
        return null;
    }
}
