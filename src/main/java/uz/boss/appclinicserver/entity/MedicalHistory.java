package uz.boss.appclinicserver.entity;

import lombok.*;
import uz.boss.appclinicserver.entity.abs.Main;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 14:20
 */
@Entity(name = "medical_histories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistory extends Main {
    @Column
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "patient_id", insertable = false, updatable = false)
    private Patient patient;

    @Column(name = "patient_id")
    private UUID patientId;

    @Column
    private String disease;

    @Column(columnDefinition = "text")
    private String complaint;

    @Column(columnDefinition = "text")
    private String diagnosis;

    @Column(columnDefinition = "text")
    private String treatment;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false,name = "clinic_id")
    private Clinic clinic;

    @Column(name = "clinic_id")
    private UUID clinicId;

    @ManyToMany
    private Set<Attachment> files;
}
