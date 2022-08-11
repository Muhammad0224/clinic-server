package uz.boss.appclinicserver.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 18.07.2022
 * Time: 17:17
 */
@Getter
@Setter
public class MedicalHistoryReqDto {
    private UUID patientId;
    private String disease;
    private String complaint;
}
