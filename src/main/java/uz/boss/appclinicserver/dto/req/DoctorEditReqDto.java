package uz.boss.appclinicserver.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 04.07.2022
 * Time: 17:36
 */
@Getter
@Setter
public class DoctorEditReqDto {
    @NotBlank
    private String fullName;

    @NotBlank
    private String pnfl;

    @NotBlank
    private String username;

    private Set<UUID> files;
}
