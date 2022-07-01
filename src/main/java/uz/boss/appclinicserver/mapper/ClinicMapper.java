package uz.boss.appclinicserver.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.boss.appclinicserver.dto.resp.ClinicRespDto;
import uz.boss.appclinicserver.entity.Clinic;

/**
 * Author: Muhammad
 * Date: 30.06.2022
 * Time: 15:34
 */
@Component
@Mapper(componentModel = "spring")
public interface ClinicMapper {
    ClinicRespDto toClinicDto(Clinic clinic);
}
