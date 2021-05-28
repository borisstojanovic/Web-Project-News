package rs.raf.Web_Project.repositories.news;

import rs.raf.Web_Project.entities.News;

import java.util.List;

public interface INewsRepository {
    News add(News news);
    News update(News news);
    void remove(Integer id);
    News find(Integer id);
    List<News> all();
    List<News> allForCategory(Integer categoryId);
    List<News> allForTag(Integer tagId);
}
