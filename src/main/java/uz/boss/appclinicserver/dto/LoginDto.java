package uz.boss.appclinicserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Murtazayev Muhammad
 * @since 27.12.2021
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoginDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
