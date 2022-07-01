package uz.boss.appclinicserver.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 17:14
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleRespDto {
    private UUID id;
    private String name;

}
