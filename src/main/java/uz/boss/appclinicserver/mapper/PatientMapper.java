package uz.boss.appclinicserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import uz.boss.appclinicserver.dto.resp.PatientRespDto;
import uz.boss.appclinicserver.entity.Patient;

/**
 * Author: Muhammad
 * Date: 05.07.2022
 * Time: 22:50
 */
@Component
@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(target = "passportPath", source = "passport.fullPath")
    PatientRespDto toPatientDto(Patient patient);
}
