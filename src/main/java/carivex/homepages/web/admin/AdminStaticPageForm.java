package carivex.homepages.web.admin;

import jakarta.validation.constraints.NotBlank;

public class AdminStaticPageForm {

    @NotBlank
    private String type;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
