package uz.boss.appclinicserver.entity;

import lombok.*;
import uz.boss.appclinicserver.entity.abs.Main;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 12:11
 */

@Entity(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient extends Main {
    @Column
    private String fullName;

    @Column(nullable = false, unique = true)
    private String pnfl;

    @Column(columnDefinition = "text")
    private String address;

    @Column
    private String phoneNumber;

    @Column(unique = true)
    private String uniqueCode;

    @Column
    private LocalDate dateOfBirth;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "passport_id", insertable = false, updatable = false)
    private Attachment passport;

    @Column(name = "passport_id")
    private UUID passportId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false,name = "clinic_id")
    private Clinic clinic;

    @Column(name = "clinic_id")
    private UUID clinicId;
}
