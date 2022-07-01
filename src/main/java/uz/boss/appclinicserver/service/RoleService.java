package uz.boss.appclinicserver.service;

import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.RoleReqDto;
import uz.boss.appclinicserver.dto.resp.RoleRespDto;

import java.util.List;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 17:13
 */

public interface RoleService {
    ApiResponse<List<RoleRespDto>> get();

    ApiResponse<RoleRespDto> create(RoleReqDto dto);

    ApiResponse<?> delete(UUID id);

}
