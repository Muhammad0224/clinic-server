package uz.boss.appclinicserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import uz.boss.appclinicserver.dto.resp.MedicalHistoryRespDto;
import uz.boss.appclinicserver.entity.MedicalHistory;

/**
 * Author: Muhammad
 * Date: 18.07.2022
 * Time: 16:10
 */
@Component
@Mapper(componentModel = "spring")
public interface MedicalHistoryMapper {
    @Mapping(target = "patientName", source = "patient.fullName")
    @Mapping(target = "doctorName", source = "doctor.fullName")
    MedicalHistoryRespDto toHistoryDto(MedicalHistory medicalHistory);
}
