package uz.boss.appclinicserver.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.boss.appclinicserver.entity.Attachment;
import uz.boss.appclinicserver.enums.HistoryStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 18.07.2022
 * Time: 15:59
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryRespDto {
    private UUID id;
    private LocalDateTime date;
    private String patientName;
    private UUID patientId;
    private String disease;
    private String complaint;
    private String diagnosis;
    private String treatment;
    private UUID doctorId;
    private String doctorName;
    private HistoryStatus status;
    private Set<Attachment> files;
}
