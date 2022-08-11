package uz.boss.appclinicserver.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Author: Muhammad
 * Date: 08.08.2022
 * Time: 16:30
 */
@Getter
@Setter
public class PatientSearchReqDto {
    @NotBlank
    private String cardNumber;

}
