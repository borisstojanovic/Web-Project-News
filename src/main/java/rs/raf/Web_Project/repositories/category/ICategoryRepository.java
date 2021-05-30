package rs.raf.Web_Project.repositories.category;

import rs.raf.Web_Project.entities.Category;

import java.util.List;

public interface ICategoryRepository {
    Category add(Category category);
    boolean remove(Integer id);
    int count();
    Category update(Category category);
    List<Category> all();
    List<Category> allPaginated(int start, int size);
    Category find(Integer id);
}
