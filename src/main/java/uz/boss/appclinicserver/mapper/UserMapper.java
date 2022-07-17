package uz.boss.appclinicserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import uz.boss.appclinicserver.dto.resp.UserRespDto;
import uz.boss.appclinicserver.entity.User;

/**
 * Author: Muhammad
 * Date: 19.06.2022
 * Time: 21:20
 */
@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "active", source = "enabled")
    @Mapping(target = "clinic", source = "clinic.name")
    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "roleId", source = "role.id")
    UserRespDto toUserDto(User user);
}
