package uz.boss.appclinicserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.LoginDto;
import uz.boss.appclinicserver.dto.TokenDto;
import uz.boss.appclinicserver.service.AuthService;

import javax.validation.Valid;

import static uz.boss.appclinicserver.utils.Constants.BASE_PATH;
import static uz.boss.appclinicserver.utils.Constants.PUBLIC;

/**
 * @author Murtazayev Muhammad
 * @since 27.12.2021
 */
@RestController
@RequestMapping(BASE_PATH + PUBLIC + "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<TokenDto> login(@RequestBody @Valid LoginDto dto) {
        return authService.login(dto);
    }

    @PostMapping("/refresh-token")
    public ApiResponse<TokenDto> refresh(@RequestBody @Valid TokenDto dto) {
        return authService.refresh(dto);
    }
}

