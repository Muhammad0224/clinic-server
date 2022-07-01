package uz.boss.appclinicserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.PatientReqDto;
import uz.boss.appclinicserver.dto.resp.PatientRespDto;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.service.PatientService;

import java.util.List;

/**
 * Author: Muhammad
 * Date: 30.06.2022
 * Time: 11:30
 */
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    @Override
    public ApiResponse<List<PatientRespDto>> get() {
        return null;
    }

    @Override
    public ApiResponse<List<PatientRespDto>> get(User user) {
        return null;
    }

    @Override
    public ApiResponse<PatientRespDto> create(User user, PatientReqDto dto) {
        return null;
    }
}
