package rs.raf.Web_Project.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News {

    private Integer id;

    private Integer categoryId;

    private Integer userId;

    private Integer views;

    @NotNull(message = "Text field is required")
    @NotBlank(message = "Text field is required")
    private String text;

    @NotNull(message = "Title field is required")
    @NotBlank(message = "Title field is required")
    private String title;

    @NotNull(message = "Created_at field is required")
    private Date createdAt;

    private List<Tag> tags;

    public News() {

    }

    public News(Integer categoryId, Integer userId, String text, String title, Date createdAt) {
        this.categoryId = categoryId;
        this.userId = userId;
        this.views = 0;
        this.text = text;
        this.title = title;
        this.createdAt = createdAt;
    }

    public News(Integer id, Integer categoryId, Integer userId, String text, String title, Date createdAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.userId = userId;
        this.views = 0;
        this.text = text;
        this.title = title;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
