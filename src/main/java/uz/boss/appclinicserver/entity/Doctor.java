package uz.boss.appclinicserver.entity;

import lombok.*;
import uz.boss.appclinicserver.entity.abs.Main;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 12:02
 */

@Entity(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor extends Main {
    @Column
    private String fullName;

    @Column(unique = true)
    private String pnfl;

    @Column
    private String speciality;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false, name = "user_id")
    private User user;

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false,name = "clinic_id")
    private Clinic clinic;

    @Column(name = "clinic_id")
    private UUID clinicId;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Attachment> files;
}
