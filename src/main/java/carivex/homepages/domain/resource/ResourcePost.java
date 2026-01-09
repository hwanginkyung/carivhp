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

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(length = 255)
    private String fileOriginalName;

    @Column(length = 255)
    private String fileStoredName;

    public ResourcePost(ResourceCategory category, String title, String content,
                        String fileOriginalName, String fileStoredName) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.fileOriginalName = fileOriginalName;
        this.fileStoredName = fileStoredName;
    }

    public void update(ResourceCategory category, String title, String content,
                       String fileOriginalName, String fileStoredName) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.fileOriginalName = fileOriginalName;
        this.fileStoredName = fileStoredName;
    }
}
