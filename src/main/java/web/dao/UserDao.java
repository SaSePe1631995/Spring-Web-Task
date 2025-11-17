package web.dao;

import web.model.User;
import java.util.List;

public interface UserDao {

    void createUser(User user);

    List<User> getAllUsers();

    void deleteUserById(Long id);

    void updateUserById(Long userId, String firstName, String lastName, short age);

    User getUserById(Long id);
}
