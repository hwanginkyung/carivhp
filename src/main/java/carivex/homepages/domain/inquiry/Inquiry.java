package carivex.homepages.domain.inquiry;


import carivex.homepages.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "inquiry")
public class Inquiry extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 30)
    private String contact;

    @Column(nullable = false, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InquiryType type;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InquiryStatus status = InquiryStatus.NEW;

    public Inquiry(String name, String contact, String email, InquiryType type, String content) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.type = type;
        this.content = content;
        this.status = InquiryStatus.NEW;
    }

    public void updateStatus(InquiryStatus status) {
        this.status = status;
    }
}
