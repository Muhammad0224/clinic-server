package uz.boss.appclinicserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.boss.appclinicserver.annotation.CurrentUser;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.DoctorEditReqDto;
import uz.boss.appclinicserver.dto.req.DoctorReqDto;
import uz.boss.appclinicserver.dto.resp.DoctorRespDto;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.service.DoctorService;
import uz.boss.appclinicserver.utils.Constants;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 04.07.2022
 * Time: 16:34
 */
@RestController
@RequestMapping(Constants.BASE_PATH + "/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping("/get/all")
    public ApiResponse<List<DoctorRespDto>> get(){
        return doctorService.get();
    }

    @GetMapping("/get")
    public ApiResponse<List<DoctorRespDto>> get(@CurrentUser User user){
        return doctorService.get(user);
    }

    @GetMapping("/get/files/{id}")
    public ApiResponse<?> getFiles(@PathVariable UUID id, HttpServletResponse response){
       return doctorService.getFiles(id, response);
    }


    @PostMapping("/create")
    public ApiResponse<?> create(@CurrentUser User user, @RequestBody @Valid DoctorReqDto dto){
        return doctorService.create(user, dto);
    }

    @PostMapping("/activate/{id}")
    public ApiResponse<?> activate(@PathVariable UUID id){
        return doctorService.activate(id);
    }

    @PutMapping("/edit/{id}")
    public ApiResponse<?> edit(@PathVariable UUID id, @RequestBody @Valid DoctorEditReqDto dto){
        return doctorService.edit(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> delete(@PathVariable UUID id){
        return doctorService.delete(id);
    }
}
