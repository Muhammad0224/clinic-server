package uz.boss.appclinicserver.service;

import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.req.DoctorEditReqDto;
import uz.boss.appclinicserver.dto.req.DoctorReqDto;
import uz.boss.appclinicserver.dto.resp.DoctorRespDto;
import uz.boss.appclinicserver.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 04.07.2022
 * Time: 17:32
 */

public interface DoctorService {
    ApiResponse<List<DoctorRespDto>> get();

    ApiResponse<List<DoctorRespDto>> get(User user);

    ApiResponse<?> getFiles(UUID id, HttpServletResponse response);

    ApiResponse<?> create(User user, DoctorReqDto dto);

    ApiResponse<?> activate(UUID id);

    ApiResponse<?> edit(UUID id, DoctorEditReqDto dto);

    ApiResponse<?> delete(UUID id);
}
