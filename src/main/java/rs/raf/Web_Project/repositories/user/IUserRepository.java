package rs.raf.Web_Project.repositories.user;

import rs.raf.Web_Project.entities.User;

import java.util.List;

public interface IUserRepository {
    User findUser(String email);

    User addUser(User user);

    boolean deactivateUser(Integer id);

    void remove(Integer id);

    List<User> all();

    User update(User user);

    List<User> allPaginated(int start, int size);

    int count();
}
