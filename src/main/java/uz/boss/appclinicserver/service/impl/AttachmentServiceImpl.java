package uz.boss.appclinicserver.service.impl;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.boss.appclinicserver.dto.ApiResponse;
import uz.boss.appclinicserver.dto.resp.AttachmentRespDto;
import uz.boss.appclinicserver.entity.Attachment;
import uz.boss.appclinicserver.enums.DirType;
import uz.boss.appclinicserver.repository.AttachmentRepo;
import uz.boss.appclinicserver.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepo attachmentRepo;
    @Value("${SSHSERVER_USERNAME}")
    private String USERNAME;
    @Value("${SSHSERVER_PASSWORD}")
    private String PASSWORD;
    @Value("${SSHSERVER_HOST}")
    private String HOST;
    @Value("${SSHSERVER_PORT}")
    private int PORT;
    @Value("${DESTINATION_BASE_DIR}")
    private String DESTINATION_BASE_DIR;
    @Value("${DESTINATION_IMAGE}")
    private String DESTINATION_IMAGE;
    @Value("${DESTINATION_FILE}")
    private String DESTINATION_FILE;
    @Value("${FILE_PATH}")
    private String FILE_PATH;
    private JSch sch;
    private Session session;


    public AttachmentServiceImpl(AttachmentRepo attachmentRepo) {
        this.attachmentRepo = attachmentRepo;
    }

    @Transactional
    public ApiResponse<AttachmentRespDto> upload(MultipartFile file, DirType directory) throws IOException {
        String[] split = file.getOriginalFilename().split("\\.");
        String uniqueName = UUID.randomUUID().toString() + "." + split[split.length - 1];
//        Path path = new File(basePath + "\\uploads\\" + uniqueName).toPath();
//        try {
//            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String pathFile = sendFileToSftpServer(
                file.getInputStream(),
                uniqueName,
                directory.equals(DirType.IMAGE) ? DESTINATION_IMAGE : DESTINATION_FILE);

        Attachment resourceFile = new Attachment();
        resourceFile.setName(uniqueName);
        resourceFile.setOriginalName(file.getOriginalFilename());
        resourceFile.setSize(file.getSize());
        resourceFile.setContentType(file.getContentType());
        resourceFile.setDirectory(directory);
//        resourceFile.setFullPath(path.toFile().getAbsolutePath());
        resourceFile.setFullPath(FILE_PATH + uniqueName);
        Attachment attachment = attachmentRepo.save(resourceFile);

        return ApiResponse.successResponse(new AttachmentRespDto(attachment.getId(), attachment.getFullPath()));
    }

    @Override
    public ApiResponse<AttachmentRespDto> get(UUID id) {
        return attachmentRepo.findById(id)
                .map(attachment -> ApiResponse.successResponse(new AttachmentRespDto(attachment.getId(), attachment.getFullPath())))
                .orElseGet(() -> ApiResponse.notFound("File"));
    }

    @Override
    public void download(UUID id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepo.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();

            response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getOriginalName() + "\"");
            response.setContentType(attachment.getContentType());

            Map<String, InputStream> file = getFileFromFtpServer(
                    List.of(attachment.getName()),
                   attachment.getDirectory().equals(DirType.IMAGE) ? DESTINATION_IMAGE : DESTINATION_FILE
            );

//            FileCopyUtils.copy(new FileInputStream(attachment.getFullPath()), response.getOutputStream());
            FileCopyUtils.copy(file.get(attachment.getName()), response.getOutputStream());
        }
    }

    @Override
    public ApiResponse<?> delete(UUID id) {
        try {
            Attachment attachment = attachmentRepo.findById(id).get();
//            File oldFile = new File(attachment.getFullPath());
//            oldFile.delete();
            boolean deleted = deleteFileFromServer(
                    attachment.getName(),
                    attachment.getDirectory().equals(DirType.IMAGE) ? DESTINATION_IMAGE : DESTINATION_FILE);
            if(deleted){
                attachmentRepo.deleteById(id);
                return ApiResponse.successResponse("O'chirildi");
            }else {
                return ApiResponse.error("Serverda xatolik", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return ApiResponse.error("Topilmadi", HttpStatus.NOT_FOUND);
        }
    }


    private Map<String, InputStream> getFileFromFtpServer(List<String> fileName, String directory) {
        String result = "";
        try {
            sch = new JSch();
            session = sch.getSession(USERNAME, HOST);
            session.setPassword(PASSWORD);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPort(PORT);
            session.connect();

            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();

            sftp.ls("/");

            sftp.cd(DESTINATION_BASE_DIR);

            try {
                sftp.cd(directory);
            } catch (Exception e) {
                return null;
            }

            Map<String, InputStream> map = new HashMap<>();

            for (int i = 0; i < fileName.size(); i++) {
                InputStream inputStream = sftp.get(fileName.get(i));
                map.put(fileName.get(i), inputStream);
//                byte[] imageBytes = sftp.get(fileName.get(i)).readAllBytes();
//                inputStream.read(imageBytes, 0, imageBytes.length);
//                inputStream.close();
//                result = Base64.encodeBase64String(imageBytes);
//                map.put(fileName.get(i), result);
            }
            sftp.disconnect();
            session.disconnect();
            return map;
        } catch (JSchException | SftpException e) {
            return null;
        }
    }

    private String sendFileToSftpServer(InputStream file, String filename, String directory) throws IOException {
        String result = "";
        if (Objects.nonNull(file) && !StringUtils.isEmpty(filename)) {
            try {
                sch = new JSch();
                session = sch.getSession(USERNAME, HOST);
                session.setPassword(PASSWORD);
                session.setConfig("StrictHostKeyChecking", "no");
                session.setPort(PORT);
                session.connect();

                ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
                sftp.connect();

                sftp.ls("/");

                sftp.cd(DESTINATION_BASE_DIR);

                try {
                    sftp.cd(directory);
                } catch (final SftpException e) {
                    // dir does not exist.
                    if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                        sftp.mkdir(DESTINATION_BASE_DIR.concat("/" + directory));
                        sftp.cd(DESTINATION_BASE_DIR.concat("/" + directory));
                    }
                }
                sftp.put(new ByteArrayInputStream(file.readAllBytes()), filename);

                sftp.disconnect();
                session.disconnect();
                result = DESTINATION_BASE_DIR + "/" + filename;
            } catch (JSchException | SftpException e) {
                return null;
            }
            return result;
        }
        return "";
    }

    private boolean deleteFileFromServer(String fileName, String directory) {
        try {
            sch = new JSch();
            session = sch.getSession(USERNAME, HOST);
            session.setPassword(PASSWORD);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPort(PORT);
            session.connect();

            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();

            sftp.ls("/");

            sftp.cd(DESTINATION_BASE_DIR);

            try {
                sftp.cd(directory);
            } catch (Exception e) {
                return false;
            }

            sftp.rm(fileName);

            sftp.disconnect();
            session.disconnect();
            return true;
        } catch (JSchException | SftpException e) {
            return false;
        }
    }
}
