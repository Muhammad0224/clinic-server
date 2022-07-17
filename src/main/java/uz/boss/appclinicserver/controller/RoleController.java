package uz.boss.appclinicserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.RoleReqDto;
import uz.boss.appclinicserver.dto.resp.RoleRespDto;
import uz.boss.appclinicserver.service.RoleService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static uz.boss.appclinicserver.utils.Constants.BASE_PATH;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 17:12
 */
@RestController
@RequestMapping(BASE_PATH + "/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/get")
    public ApiResponse<List<RoleRespDto>> get() {
        return roleService.get();
    }

    @PostMapping("/create")
    public ApiResponse<RoleRespDto> create(@RequestBody @Valid RoleReqDto dto) {
        return roleService.create(dto);
    }

    @PutMapping("/edit/{id}")
    public ApiResponse<?> edit(@PathVariable UUID id, @RequestBody @Valid RoleReqDto dto){
        return roleService.edit(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> delete(@PathVariable UUID id) {
        return roleService.delete(id);
    }
}
