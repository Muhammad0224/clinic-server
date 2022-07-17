package uz.boss.appclinicserver.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 30.06.2022
 * Time: 15:26
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClinicRespDto {
    private UUID id;
    private String name;
    private String avatar;
    private boolean active;
    private UUID avatarId;
}
