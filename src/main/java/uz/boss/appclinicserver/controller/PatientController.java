package uz.boss.appclinicserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.boss.appclinicserver.annotation.CurrentUser;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.PatientReqDto;
import uz.boss.appclinicserver.dto.req.PatientSearchReqDto;
import uz.boss.appclinicserver.dto.resp.PatientRespDto;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.service.PatientService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static uz.boss.appclinicserver.utils.Constants.BASE_PATH;
import static uz.boss.appclinicserver.utils.Constants.PUBLIC;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 18:13
 */
@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/patients/get/all")
    public ApiResponse<List<PatientRespDto>> get(){
        return patientService.get();
    }

    @GetMapping("/patients/get")
    public ApiResponse<List<PatientRespDto>> get(@CurrentUser User user){
        return patientService.get(user);
    }

    @GetMapping(PUBLIC + "/patients/get/{id}")
    public ApiResponse<PatientRespDto> get(@PathVariable UUID id){
        return patientService.get(id);
    }

    @PostMapping("/patients/filter")
    public ApiResponse<List<PatientRespDto>> filter(@RequestBody @Valid PatientSearchReqDto dto){
        return patientService.filter(dto);
    }
    @GetMapping(PUBLIC + "/patients/card-number/{cardNumber}")
    public ApiResponse<PatientRespDto> cardNumber(@PathVariable String cardNumber){
        return patientService.cardNumber(cardNumber);
    }

    @PostMapping("/patients/create")
    public ApiResponse<PatientRespDto> create(@CurrentUser User user, @RequestBody @Valid PatientReqDto dto){
        return patientService.create(user,dto);
    }

    @PutMapping("/patients/edit/{id}")
    public ApiResponse<?> edit(@PathVariable UUID id, @RequestBody @Valid PatientReqDto dto){
        return patientService.edit(id, dto);
    }
}
