package uz.boss.appclinicserver.service;

import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.HistoryAnswerReqDto;
import uz.boss.appclinicserver.dto.req.MedicalHistoryEditReqDto;
import uz.boss.appclinicserver.dto.req.MedicalHistoryReqDto;
import uz.boss.appclinicserver.dto.resp.MedicalHistoryRespDto;
import uz.boss.appclinicserver.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 10.07.2022
 * Time: 13:56
 */

public interface MedicalHistoryService {
    ApiResponse<List<MedicalHistoryRespDto>> getByPatientId(UUID userId);
    ApiResponse<List<MedicalHistoryRespDto>> getByDoctorId(User user);
    ApiResponse<List<MedicalHistoryRespDto>> getFilteredPatientId(UUID patientId, Integer month, Integer year);
    ApiResponse<?> getFiles(UUID id) throws IOException;
    ApiResponse<?> create(User user, MedicalHistoryReqDto dto);
    ApiResponse<?> answer(UUID id, User user, HistoryAnswerReqDto dto);
    ApiResponse<?> edit(UUID id, User user, MedicalHistoryEditReqDto dto);
    ApiResponse<?> delete(UUID id);
}
