package uz.boss.appclinicserver.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Author: Muhammad
 * Date: 30.06.2022
 * Time: 15:44
 */
@Getter
@Setter
public class ClinicReqDto {
    @NotBlank
    private String name;

    @NotBlank
    private String avatar;
}
