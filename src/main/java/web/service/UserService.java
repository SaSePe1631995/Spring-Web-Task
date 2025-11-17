package web.service;

import web.dto.UserCreateDto;
import web.model.User;
import java.util.List;

public interface UserService {

    void createUser(UserCreateDto userCreateDto);

    List<User> getAllUsers();

    void updateUser(Long id, UserCreateDto userCreateDto);

    User getUserById(Long id);

    void deleteUserById(Long id);
}
