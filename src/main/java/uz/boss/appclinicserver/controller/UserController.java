package uz.boss.appclinicserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.boss.appclinicserver.annotation.CurrentUser;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.UserReqDto;
import uz.boss.appclinicserver.dto.resp.UserRespDto;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.enums.Permission;
import uz.boss.appclinicserver.enums.RoleType;
import uz.boss.appclinicserver.service.UserService;
import uz.boss.appclinicserver.utils.Constants;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Author: Muhammad
 * Date: 19.06.2022
 * Time: 21:14
 */
@RestController
@RequestMapping(Constants.BASE_PATH + "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ApiResponse<List<UserRespDto>> get(){
        return userService.get();
    }

    @GetMapping("/get")
    public ApiResponse<UserRespDto> get(@CurrentUser User user){
        return userService.get(user);
    }

    @GetMapping("/get-by-role")
    public ApiResponse<List<UserRespDto>> get(@RequestParam RoleType roleType){
        return userService.get(roleType);
    }
    @GetMapping("/permissions")
    public ApiResponse<Set<Permission>> getPermissions(@CurrentUser User user){
        return userService.getPermissions(user);
    }

    @PostMapping("/create")
    public ApiResponse<UserRespDto> create(@RequestBody @Valid UserReqDto dto){
        return userService.create(dto);
    }

}
