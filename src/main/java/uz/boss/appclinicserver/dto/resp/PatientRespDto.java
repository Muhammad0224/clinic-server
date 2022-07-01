package uz.boss.appclinicserver.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 18:16
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientRespDto {
    private UUID id;
}
