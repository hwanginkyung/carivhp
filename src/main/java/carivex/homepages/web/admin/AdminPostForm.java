package carivex.homepages.web.admin;

import jakarta.validation.constraints.NotBlank;

public class AdminPostForm {

    private Long id;

    @NotBlank
    private String type; // notice | resource

    @NotBlank
    private String category; // enum name

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
