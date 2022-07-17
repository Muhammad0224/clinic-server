package uz.boss.appclinicserver.service;

import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.UserEditReqDto;
import uz.boss.appclinicserver.dto.req.UserReqDto;
import uz.boss.appclinicserver.dto.resp.UserRespDto;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.enums.Permission;
import uz.boss.appclinicserver.enums.RoleType;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 19.06.2022
 * Time: 21:16
 */

public interface UserService {
    ApiResponse<List<UserRespDto>> get();

    ApiResponse<UserRespDto> get(User user);

    ApiResponse<List<UserRespDto>> get(RoleType roleType);

    ApiResponse<Set<Permission>> getPermissions(User user);

    ApiResponse<UserRespDto> create(UserReqDto dto);

    ApiResponse<?> activate(UUID id);

    ApiResponse<?> edit(UUID id, UserEditReqDto dto);

    ApiResponse<?> delete(UUID id);
}
