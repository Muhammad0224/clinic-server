package uz.boss.appclinicserver.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.LoginDto;
import uz.boss.appclinicserver.dto.TokenDto;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.repository.UserRepo;
import uz.boss.appclinicserver.security.JwtProvider;
import uz.boss.appclinicserver.service.AuthService;


/**
 * @author Murtazayev Muhammad
 * @since 25.12.2021
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public ApiResponse<TokenDto> login(LoginDto dto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            User user = (User) authenticate.getPrincipal();
            String accessToken = jwtProvider.generateToken(user.getUsername(), true);
            String refreshToken = jwtProvider.generateToken(user.getUsername(), false);
            long expireTime = jwtProvider.getExpireTime(accessToken);
            return ApiResponse.successResponse(new TokenDto(accessToken, refreshToken, expireTime));
        } catch (Exception e) {
            return ApiResponse.error("Username or password incorrect", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ApiResponse<TokenDto> refresh(TokenDto dto) {
        try {
            jwtProvider.validateToken(dto.getAccessToken());
            return ApiResponse.successResponse(dto);
        } catch (ExpiredJwtException exception) {
            Claims claims = exception.getClaims();
            String subject = claims.getSubject();

            try {
                jwtProvider.validateToken(dto.getRefreshToken());
                String username = jwtProvider.getUsernameFromToken(dto.getRefreshToken());
                if (!username.equals(subject)) {
                    return ApiResponse.error("Token invalid", HttpStatus.FORBIDDEN);
                }
                String accessToken = jwtProvider.generateToken(username, true);
                String refreshToken = jwtProvider.generateToken(username, false);
                long expireTime = jwtProvider.getExpireTime(accessToken);
                User user = userRepo.findByUsernameAndEnabledTrue(username).get();
                return ApiResponse.successResponse(new TokenDto(accessToken, refreshToken,expireTime));
            } catch (Exception e) {
                return ApiResponse.error("Username or password incorrect", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return ApiResponse.error("Username or password incorrect", HttpStatus.FORBIDDEN);
        }
    }
}
