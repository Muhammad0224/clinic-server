package uz.boss.appclinicserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.boss.appclinicserver.annotation.CurrentUser;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.PatientReqDto;
import uz.boss.appclinicserver.dto.resp.PatientRespDto;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.service.PatientService;

import javax.validation.Valid;
import java.util.List;

import static uz.boss.appclinicserver.utils.Constants.BASE_PATH;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 18:13
 */
@RestController
@RequestMapping(BASE_PATH + "/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/get")
    public ApiResponse<List<PatientRespDto>> get(){
        return patientService.get();
    }

    @GetMapping("/get-clinic")
    public ApiResponse<List<PatientRespDto>> get(@CurrentUser User user){
        return patientService.get(user);
    }

    @PostMapping("/create")
    public ApiResponse<PatientRespDto> create(@CurrentUser User user, @RequestBody @Valid PatientReqDto dto){
        return patientService.create(user,dto);
    }
}
