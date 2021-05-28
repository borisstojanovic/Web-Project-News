package rs.raf.Web_Project.repositories.tag;

import rs.raf.Web_Project.entities.Tag;

import java.util.List;

public interface ITagRepository {
    Tag add(Tag tag);
    Tag update(Tag tag);
    void remove(Integer id);
    List<Tag> allForNews(Integer newsId);
    List<Tag> all();
}
