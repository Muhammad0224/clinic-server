package uz.boss.appclinicserver.dto.req;

import lombok.Getter;
import lombok.Setter;
import uz.boss.appclinicserver.entity.Attachment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 04.07.2022
 * Time: 17:36
 */
@Getter
@Setter
public class DoctorReqDto {
    @NotBlank
    private String fullName;

    @NotBlank
    private String pnfl;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Set<UUID> files;
}
