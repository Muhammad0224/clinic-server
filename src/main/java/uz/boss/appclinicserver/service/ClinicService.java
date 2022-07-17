package uz.boss.appclinicserver.service;

import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.ClinicReqDto;
import uz.boss.appclinicserver.dto.resp.ClinicRespDto;

import java.util.List;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 30.06.2022
 * Time: 15:24
 */

public interface ClinicService {
    ApiResponse<List<ClinicRespDto>> get();

    ApiResponse<ClinicRespDto> create(ClinicReqDto dto);
    ApiResponse<?> activate(UUID id);

    ApiResponse<?> edit(UUID id, ClinicReqDto dto);

    ApiResponse<?> delete(UUID id);
}
