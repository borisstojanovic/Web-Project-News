package rs.raf.Web_Project.services;

import rs.raf.Web_Project.entities.News;
import rs.raf.Web_Project.repositories.news.INewsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsService {

    public NewsService() {

    }

    @Inject
    private INewsRepository newsRepository;

    public News add(News news) {
        return this.newsRepository.add(news);
    }

    public News update(News news) { return this.newsRepository.update(news); }

    public void remove(Integer id){ this.newsRepository.remove(id); }

    public List<News> allForTag(Integer id) {
        return this.newsRepository.allForTag(id);
    }

    public List<News> all() {
        return this.newsRepository.all();
    }

    public List<News> allForCategory(Integer id) {
        return this.newsRepository.allForCategory(id);
    }

    public News find(Integer id) {
        return this.newsRepository.find(id);
    }

    public List<News> allPaginatedForTag(Integer id, int start, int size) { return this.newsRepository.allPaginatedForTag(id, start, size); }

    public List<News> allPaginated(int start, int size) { return this.newsRepository.allPaginated(start, size); }

    public List<News> allPaginatedForCategory(Integer id, int start, int size) { return this.newsRepository.allPaginatedForCategory(id, start, size); }

    public int count() { return this.newsRepository.count(); }

    public int countForTag(int id){ return this.newsRepository.countForTag(id); }

    public int countForCategory(int id){ return this.newsRepository.countForCategory(id); }

    public List<News> mostViewed(int start, int size) { return this.newsRepository.mostViewed(start, size); }

    public List<News> allNewest(int start, int size) { return this.newsRepository.allNewest(start, size); }

    public boolean incrementViews(int id) { return this.newsRepository.incrementViews(id); }
}
