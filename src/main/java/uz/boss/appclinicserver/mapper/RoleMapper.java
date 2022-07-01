package uz.boss.appclinicserver.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.boss.appclinicserver.dto.resp.RoleRespDto;
import uz.boss.appclinicserver.entity.Role;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 17:26
 */
@Component
@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleRespDto toRoleDto(Role role);
}
