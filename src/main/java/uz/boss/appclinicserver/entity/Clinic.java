package uz.boss.appclinicserver.entity;

import lombok.*;
import uz.boss.appclinicserver.entity.abs.Main;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 15:28
 */
@Entity(name = "clinics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clinic extends Main {
    @Column
    private String name;

    @Column
    private String avatar;

    @Column
    private UUID avatarId;

    @Column
    @Builder.Default
    private boolean active = true;
}
