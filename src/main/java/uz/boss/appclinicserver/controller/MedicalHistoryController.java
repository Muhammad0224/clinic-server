package uz.boss.appclinicserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.boss.appclinicserver.annotation.CurrentUser;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.HistoryAnswerReqDto;
import uz.boss.appclinicserver.dto.req.MedicalHistoryEditReqDto;
import uz.boss.appclinicserver.dto.req.MedicalHistoryReqDto;
import uz.boss.appclinicserver.dto.resp.MedicalHistoryRespDto;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.service.MedicalHistoryService;
import uz.boss.appclinicserver.utils.Constants;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Path;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static uz.boss.appclinicserver.utils.Constants.PUBLIC;

/**
 * Author: Muhammad
 * Date: 10.07.2022
 * Time: 13:51
 */
@RestController
@RequestMapping(Constants.BASE_PATH)
@RequiredArgsConstructor
public class MedicalHistoryController {
    private final MedicalHistoryService medicalHistoryService;

    @GetMapping(PUBLIC + "/medical-history/{patientId}")
    public ApiResponse<List<MedicalHistoryRespDto>> getByPatientId(@PathVariable UUID patientId){
        return medicalHistoryService.getByPatientId(patientId);
    }

    @GetMapping(PUBLIC + "/medical-history/filter/{patientId}")
    public ApiResponse<List<MedicalHistoryRespDto>> getFilteredPatientId(@PathVariable UUID patientId,
                                                                         @RequestParam Integer month,
                                                                         @RequestParam Integer year){
        return medicalHistoryService.getFilteredPatientId(patientId, month, year);
    }

    @GetMapping("/medical-history/doctor")
    public ApiResponse<List<MedicalHistoryRespDto>> getByDoctorId(@CurrentUser User user){
        return medicalHistoryService.getByDoctorId(user);
    }

    @GetMapping("/medical-history/file/{id}")
    public ApiResponse<?> getFiles(@PathVariable UUID id) throws IOException {
        return medicalHistoryService.getFiles(id);
    }

    @PostMapping("/medical-history/create")
    public ApiResponse<?> create(@CurrentUser User user, @RequestBody @Valid MedicalHistoryReqDto dto){
        return medicalHistoryService.create(user, dto);
    }

    @PostMapping("/medical-history/attach-answer/{id}")
    public ApiResponse<?> answer(@PathVariable UUID id, @CurrentUser User user, @RequestBody @Valid HistoryAnswerReqDto dto){
        return medicalHistoryService.answer(id, user, dto);
    }

    @PutMapping("/medical-history/edit/{id}")
    public ApiResponse<?> edit(@PathVariable UUID id, @CurrentUser User user, @RequestBody @Valid MedicalHistoryEditReqDto dto){
        return medicalHistoryService.edit(id, user, dto);
    }

    @DeleteMapping("/medical-history/delete/{id}")
    public ApiResponse<?> delete(@PathVariable UUID id){
        return medicalHistoryService.delete(id);
    }
}
