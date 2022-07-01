package uz.boss.appclinicserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.ClinicReqDto;
import uz.boss.appclinicserver.dto.resp.ClinicRespDto;
import uz.boss.appclinicserver.service.ClinicService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static uz.boss.appclinicserver.utils.Constants.BASE_PATH;

/**
 * Author: Muhammad
 * Date: 30.06.2022
 * Time: 15:23
 */
@RestController
@RequestMapping(BASE_PATH + "/clinics")
@RequiredArgsConstructor
public class ClinicController {
    private final ClinicService clinicService;

    @GetMapping("/get")
    public ApiResponse<List<ClinicRespDto>> get(){
        return clinicService.get();
    }

    @PostMapping("/create")
    public ApiResponse<ClinicRespDto> create(@RequestBody @Valid ClinicReqDto dto){
        return clinicService.create(dto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> delete(@PathVariable UUID id){
        return clinicService.delete(id);
    }
}
