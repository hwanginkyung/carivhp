package carivex.homepages.domain.resource;


import carivex.homepages.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "resource_post")
public class ResourcePost extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ResourceCategory category;

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

    @Column(nullable = false)
    private boolean pinned = false;

    @Column(nullable = false)
    private Long views = 0L;

    public ResourcePost(ResourceCategory category, String title, String content,
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

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public void update(ResourceCategory category, String title, String content,
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
