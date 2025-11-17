package web.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.dto.UserCreateDto;
import web.model.User;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserDao userDao, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public void updateUser(Long id, UserCreateDto userCreateDto) {
        userDao.updateUserById(id,userCreateDto.getFirstName(),
                userCreateDto.getLastName(),
                userCreateDto.getAge());
    }

    @Transactional()
    @Override
    public void createUser(UserCreateDto userCreateDto) {
        userDao.createUser(modelMapper.map(userCreateDto, User.class));
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        userDao.deleteUserById(id);
    }
}
