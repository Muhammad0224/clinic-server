package uz.boss.appclinicserver.entity;

import lombok.*;
import uz.boss.appclinicserver.entity.abs.Main;
import uz.boss.appclinicserver.enums.Permission;
import uz.boss.appclinicserver.enums.RoleType;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Muhammad
 * Date: 28.06.2022
 * Time: 19:31
 */
@Entity(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends Main {
    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private RoleType type;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<Permission> permissions;
}
