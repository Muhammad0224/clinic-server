package uz.boss.appclinicserver.dto.req;

import lombok.Getter;
import lombok.Setter;
import uz.boss.appclinicserver.enums.Permission;
import uz.boss.appclinicserver.enums.RoleType;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 17:17
 */
@Getter
@Setter
public class RoleReqDto {
    @NotBlank
    private String name;

    private RoleType type = RoleType.CUSTOM;

    private Set<Permission> permissions;
}
