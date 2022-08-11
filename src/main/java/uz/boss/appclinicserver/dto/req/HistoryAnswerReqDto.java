package uz.boss.appclinicserver.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 01.08.2022
 * Time: 16:14
 */
@Getter
@Setter
public class HistoryAnswerReqDto {
    @NotBlank
    private String diagnosis;
    private String treatment;
    private Set<UUID> files;
}
