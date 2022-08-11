package uz.boss.appclinicserver.dto.req;

import lombok.Getter;
import lombok.Setter;
import uz.boss.appclinicserver.entity.Attachment;

import java.util.Set;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 01.08.2022
 * Time: 17:08
 */
@Getter
@Setter
public class MedicalHistoryEditReqDto {
    private String disease;
    private String complaint;
    private String diagnosis;
    private String treatment;
    private Set<UUID> files;
}
