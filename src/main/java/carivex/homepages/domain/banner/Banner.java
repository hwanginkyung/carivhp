package carivex.homepages.domain.banner;

import carivex.homepages.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "banner")
public class Banner extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String linkUrl;

    @Column(length = 255)
    private String imageOriginalName;

    @Column(length = 255)
    private String imageStoredName;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private int sortOrder = 0;

    public Banner(String title, String linkUrl, String imageOriginalName, String imageStoredName, boolean active, int sortOrder) {
        this.title = title;
        this.linkUrl = linkUrl;
        this.imageOriginalName = imageOriginalName;
        this.imageStoredName = imageStoredName;
        this.active = active;
        this.sortOrder = sortOrder;
    }

    public void update(String title, String linkUrl, String imageOriginalName, String imageStoredName, boolean active, int sortOrder) {
        this.title = title;
        this.linkUrl = linkUrl;
        this.imageOriginalName = imageOriginalName;
        this.imageStoredName = imageStoredName;
        this.active = active;
        this.sortOrder = sortOrder;
    }
}
