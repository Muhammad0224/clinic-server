package uz.boss.appclinicserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.resp.AttachmentRespDto;
import uz.boss.appclinicserver.enums.DirType;
import uz.boss.appclinicserver.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static uz.boss.appclinicserver.utils.Constants.BASE_PATH;
import static uz.boss.appclinicserver.utils.Constants.PUBLIC;

/**
 * @author Murtazayev Muhammad
 * @since 28.12.2021
 */
@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping("/attachment/upload")
    public ApiResponse<AttachmentRespDto> upload(MultipartFile file, @RequestParam DirType directory) throws IOException {
        return attachmentService.upload(file,directory);
    }

    @GetMapping("/attachment/get/{id}")
    public ApiResponse<AttachmentRespDto> get(@PathVariable UUID id){
        return attachmentService.get(id);
    }

    @SneakyThrows
    @GetMapping(PUBLIC + "/attachment/download/{id}")
    public void download(@PathVariable UUID id, HttpServletResponse response){
        attachmentService.download(id, response);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable UUID id){
        return attachmentService.delete(id);
    }
}
