package uz.boss.appclinicserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.UserReqDto;
import uz.boss.appclinicserver.dto.resp.UserRespDto;
import uz.boss.appclinicserver.entity.Clinic;
import uz.boss.appclinicserver.entity.Role;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.enums.Permission;
import uz.boss.appclinicserver.enums.RoleType;
import uz.boss.appclinicserver.mapper.UserMapper;
import uz.boss.appclinicserver.repository.ClinicRepo;
import uz.boss.appclinicserver.repository.RoleRepo;
import uz.boss.appclinicserver.repository.UserRepo;
import uz.boss.appclinicserver.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: Muhammad
 * Date: 19.06.2022
 * Time: 21:19
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final ClinicRepo clinicRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<List<UserRespDto>> get() {
        return ApiResponse.successResponse(
                userRepo.findAll(Sort.by(Sort.Order.desc("createdAt"))).stream().map(userMapper::toUserDto).collect(Collectors.toList())
        );
    }

    @Override
    public ApiResponse<UserRespDto> get(User user) {
        return ApiResponse.successResponse(userMapper.toUserDto(user));
    }

    @Override
    public ApiResponse<List<UserRespDto>> get(RoleType roleType) {
        List<User> users = userRepo.findAllByRole_Type(roleType);
        return ApiResponse.successResponse(users.stream().map(userMapper::toUserDto).collect(Collectors.toList()));
    }

    @Override
    public ApiResponse<Set<Permission>> getPermissions(User user) {
        return ApiResponse.successResponse(user.getRole().getPermissions());
    }

    @Override
    public ApiResponse<UserRespDto> create(UserReqDto dto) {
        if (userRepo.existsByUsername(dto.getUsername()))
            return ApiResponse.error("Username has already existed", HttpStatus.CONFLICT);
        Optional<Role> optionalRole = roleRepo.findById(dto.getRoleId());
        if (optionalRole.isEmpty()) {
            return ApiResponse.notFound("Role");
        }
        Optional<Clinic> optionalClinic = clinicRepo.findById(dto.getClinicId());
        if (optionalClinic.isEmpty()) {
            return ApiResponse.notFound("Clinic");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(optionalRole.get())
                .clinicId(optionalClinic.get().getId())
                .build();
        userRepo.save(user);
        return ApiResponse.successResponse(userMapper.toUserDto(user));
    }
}
