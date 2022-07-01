package uz.boss.appclinicserver.service;

import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.LoginDto;
import uz.boss.appclinicserver.dto.TokenDto;

/**
 * @author Murtazayev Muhammad
 * @since 25.12.2021
 */
public interface AuthService {

    ApiResponse<TokenDto> login(LoginDto dto);

    ApiResponse<TokenDto> refresh(TokenDto dto);
}
