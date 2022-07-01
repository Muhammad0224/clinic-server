package uz.boss.appclinicserver.service;

import org.springframework.web.multipart.MultipartFile;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.resp.AttachmentRespDto;
import uz.boss.appclinicserver.enums.DirType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public interface AttachmentService {
    ApiResponse<AttachmentRespDto> upload(MultipartFile file, DirType directory) throws IOException;

    ApiResponse<AttachmentRespDto> get(UUID id);

    void download(UUID id, HttpServletResponse response) throws IOException;

    ApiResponse<?> delete(UUID id);
}
