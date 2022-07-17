package uz.boss.appclinicserver.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 19.06.2022
 * Time: 21:18
 */
@Getter
@Setter
public class UserEditReqDto {
    @NotBlank
    private String username;

    @NotNull
    private UUID roleId;

    @NotNull
    private UUID clinicId;
}
