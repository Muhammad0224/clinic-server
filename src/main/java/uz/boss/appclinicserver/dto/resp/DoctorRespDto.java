package uz.boss.appclinicserver.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.boss.appclinicserver.entity.Attachment;

import java.util.Set;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 04.07.2022
 * Time: 17:32
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRespDto {
    private UUID id;
    private String fullName;
    private String username;
    private String pnfl;
    private Set<Attachment> files;
    private boolean active;
}
