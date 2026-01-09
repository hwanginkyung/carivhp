package carivex.homepages.web;


import carivex.homepages.domain.inquiry.InquiryType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InquiryForm {

    @NotBlank
    private String name;

    @NotBlank
    private String contact;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private InquiryType type;

    @NotBlank
    private String content;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public InquiryType getType() { return type; }
    public void setType(InquiryType type) { this.type = type; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
