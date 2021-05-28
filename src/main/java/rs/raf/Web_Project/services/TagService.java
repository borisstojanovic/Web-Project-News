package rs.raf.Web_Project.services;

import rs.raf.Web_Project.entities.Tag;
import rs.raf.Web_Project.repositories.tag.ITagRepository;

import javax.inject.Inject;
import java.util.List;

public class TagService {

    public TagService() {

    }

    @Inject
    private ITagRepository tagRepository;

    public Tag add(Tag tag) {
        return this.tagRepository.add(tag);
    }

    public void remove(Integer id) { this.tagRepository.remove(id); }

    public Tag update(Tag tag){ return this.tagRepository.update(tag); }

    public List<Tag> all(){ return this.tagRepository.all(); }

    public List<Tag> allForNews(Integer newsId){ return this.tagRepository.allForNews(newsId); }
}
