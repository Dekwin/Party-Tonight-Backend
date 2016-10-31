package com.partymaker.mvc.service.user;

import com.partymaker.mvc.dao.user.UserDao;
import com.partymaker.mvc.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by anton on 09/10/16.
 */
@Service("userService")
@Transactional()
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUserBuId(Long id) {
        return (User) userDao.findById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }

    /* since we use the Transaction,
     don't need to update, just set fetched user with new parameters */
    @Override
    public void updateUser(User user) {
        User user1 = (User) userDao.findById(user.getIdUser());

        user1.setDateUpdate(user.getDateUpdate());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setPhone(user.getPhone());
        user1.setUserRole(user.getUserRole());
    }

    @Override
    public User findUserByEmail(String value) {
        return (User) userDao.findByField("email", value);
    }

    @Override
    public boolean isExist(String email) {

        return Objects.nonNull(findUserByEmail(email));
    }
}
