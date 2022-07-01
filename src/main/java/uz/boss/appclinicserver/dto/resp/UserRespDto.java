package uz.boss.appclinicserver.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 19.06.2022
 * Time: 21:16
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRespDto {
    private UUID id;
    private String username;
    private String role;
    private boolean active;

    private UUID clinicId;
    private String clinic;
}
