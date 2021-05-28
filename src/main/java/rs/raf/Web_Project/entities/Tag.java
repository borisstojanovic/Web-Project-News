package rs.raf.Web_Project.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Tag {

    private Integer id;

    @NotNull(message = "Keyword field is required")
    @NotEmpty(message = "Keyword field is required")
    private String keyword;

    private List<News> newsList;

    public Tag() {
        newsList = new ArrayList<>();
    }

    public Tag(String keyword) {
        this.newsList = new ArrayList<>();
        this.keyword = keyword;
    }

    public Tag(Integer id, String keyword) {
        this.id = id;
        this.newsList = new ArrayList<>();
        this.keyword = keyword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
