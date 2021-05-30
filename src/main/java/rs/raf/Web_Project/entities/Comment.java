package rs.raf.Web_Project.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Comment {
    private Integer id;

    private Integer newsId;

    @NotNull(message = "Author field is required")
    @NotBlank(message = "Author field is required")
    private String author;

    @NotNull(message = "Text field is required")
    @NotBlank(message = "Text field is required")
    private String text;

    @NotNull(message = "Created_at field is required")
    private Date createdAt;

    public Comment() {
    }

    public Comment(Integer newsId, String author, String text, Date createdAt) {
        this.newsId = newsId;
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Comment(Integer id, Integer newsId, String author, String text, Date createdAt) {
        this.id = id;
        this.newsId = newsId;
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
