package carivex.homepages.domain.notice;

import carivex.homepages.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "notice")
public class Notice extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NoticeCategory category;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 200)
    private String titleEn;

    @Lob
    @Column(nullable = false)
    private String content;

    @Lob
    private String contentEn;

    @Column(length = 255)
    private String fileOriginalName;

    @Column(length = 255)
    private String fileStoredName;

    public Notice(NoticeCategory category, String title, String content,
                  String titleEn, String contentEn,
                  String fileOriginalName, String fileStoredName) {
        this.category = category;
        this.title = title;
        this.titleEn = titleEn;
        this.content = content;
        this.contentEn = contentEn;
        this.fileOriginalName = fileOriginalName;
        this.fileStoredName = fileStoredName;
    }

    public void update(NoticeCategory category, String title, String content,
                       String titleEn, String contentEn,
                       String fileOriginalName, String fileStoredName) {
        this.category = category;
        this.title = title;
        this.titleEn = titleEn;
        this.content = content;
        this.contentEn = contentEn;
        this.fileOriginalName = fileOriginalName;
        this.fileStoredName = fileStoredName;
    }
}
