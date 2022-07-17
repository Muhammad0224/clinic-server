package uz.boss.appclinicserver.entity;

import lombok.*;
import uz.boss.appclinicserver.entity.abs.Main;
import uz.boss.appclinicserver.enums.DirType;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "attachments")
public class Attachment extends Main {

    @Column(name = "name", nullable = false)
    private String name;

    private String originalName;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size", nullable = false)
    private Long size;

    private String fullPath;

    @Enumerated(EnumType.STRING)
    private DirType directory;

    @Builder.Default
    private boolean status = true;
}
