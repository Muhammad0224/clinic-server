package uz.boss.appclinicserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class TokenDto {

    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;

    private long expireTime;
}
