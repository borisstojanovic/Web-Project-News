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
    List<News> allPaginatedForCategory(Integer id, int start, int size);
    List<News> allForTag(Integer tagId);
    List<News> allPaginated(int start, int size);
    List<News> allPaginatedForTag(int id, int start, int size);
    int count();
    int countForCategory(int id);
    int countForTag(int id);
}
