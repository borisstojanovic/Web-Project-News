package rs.raf.Web_Project.services;

import rs.raf.Web_Project.entities.Category;
import rs.raf.Web_Project.repositories.category.ICategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {

    public CategoryService() {

    }

    @Inject
    private ICategoryRepository categoryRepository;

    public Category add(Category category) {
        return this.categoryRepository.add(category);
    }

    public boolean remove(Integer id) { return this.categoryRepository.remove(id); }

    public Category update(Category category){ return this.categoryRepository.update(category); }

    public List<Category> all(){ return this.categoryRepository.all(); }

    public List<Category> allPaginated(int start, int size){ return this.categoryRepository.allPaginated(start, size); }

    public int count(){ return this.categoryRepository.count(); }
}
