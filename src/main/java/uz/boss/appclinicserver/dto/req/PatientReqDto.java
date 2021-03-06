package uz.boss.appclinicserver.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 18:24
 */
@Getter
@Setter
public class PatientReqDto {
    @NotBlank
    private String fullName;

    @NotBlank
    private String pnfl;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

    private String dateOfBirth;

    private UUID passportId;
}
