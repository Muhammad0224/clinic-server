package uz.boss.appclinicserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.RoleReqDto;
import uz.boss.appclinicserver.dto.resp.RoleRespDto;
import uz.boss.appclinicserver.entity.Role;
import uz.boss.appclinicserver.mapper.RoleMapper;
import uz.boss.appclinicserver.repository.RoleRepo;
import uz.boss.appclinicserver.service.RoleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 17:22
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;
    private final RoleMapper roleMapper;

    @Override
    public ApiResponse<List<RoleRespDto>> get() {
        return ApiResponse.successResponse(roleRepo.findAll(Sort.by(Sort.Order.desc("createdAt")))
                .stream().map(roleMapper::toRoleDto).collect(Collectors.toList()));
    }

    @Override
    public ApiResponse<RoleRespDto> create(RoleReqDto dto) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setPermissions(dto.getPermissions());
        role.setType(dto.getType());

        roleRepo.save(role);
        return ApiResponse.successResponse(roleMapper.toRoleDto(role));
    }

    @Override
    public ApiResponse<?> edit(UUID id, RoleReqDto dto) {
        Optional<Role> optionalRole = roleRepo.findById(id);
        if (optionalRole.isEmpty()) {
            return ApiResponse.notFound("Role");
        }
        Role role = optionalRole.get();
        role.setName(dto.getName());
        role.setType(dto.getType());
        role.setPermissions(dto.getPermissions());
        roleRepo.save(role);
        return ApiResponse.successResponse(roleMapper.toRoleDto(role));
    }

    @Override
    public ApiResponse<?> delete(UUID id) {
        Optional<Role> optionalRole = roleRepo.findById(id);
        if (optionalRole.isPresent()) {
            roleRepo.deleteById(id);
            return ApiResponse.successResponse("Deleted");
        }
        return ApiResponse.error("Role topilmadi", HttpStatus.NOT_FOUND);
    }
}
