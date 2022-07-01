package uz.boss.appclinicserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.boss.appclinicserver.entity.Attachment;

import java.util.UUID;

public interface AttachmentRepo extends JpaRepository<Attachment, UUID> {
}
