package uz.boss.appclinicserver.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 30.06.2022
 * Time: 15:44
 */
@Getter
@Setter
public class ClinicReqDto {

    @NotBlank
    private String avatar;

    @NotBlank
    private String name;

    @NotNull
    private UUID avatarId;
}
