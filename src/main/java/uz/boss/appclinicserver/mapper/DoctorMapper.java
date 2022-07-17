package uz.boss.appclinicserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import uz.boss.appclinicserver.dto.resp.DoctorRespDto;
import uz.boss.appclinicserver.entity.Doctor;

/**
 * Author: Muhammad
 * Date: 04.07.2022
 * Time: 17:44
 */
@Mapper(componentModel = "spring")
@Component
public interface DoctorMapper {
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "active", source = "user.enabled")
    DoctorRespDto toDoctorDto(Doctor doctor);
}
