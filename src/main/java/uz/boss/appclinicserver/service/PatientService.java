package uz.boss.appclinicserver.service;

import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.PatientReqDto;
import uz.boss.appclinicserver.dto.resp.PatientRespDto;
import uz.boss.appclinicserver.entity.User;

import java.util.List;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 18:14
 */

public interface PatientService {
    ApiResponse<List<PatientRespDto>> get();

    ApiResponse<List<PatientRespDto>> get(User user);

    ApiResponse<PatientRespDto> create(User user, PatientReqDto dto);
}
