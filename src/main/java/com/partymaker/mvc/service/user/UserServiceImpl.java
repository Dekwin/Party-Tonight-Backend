package com.partymaker.mvc.service.user;

import com.partymaker.mvc.dao.event.EventDAO;
import com.partymaker.mvc.dao.user.UserDao;
import com.partymaker.mvc.model.whole.EventEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by anton on 09/10/16.
 */
@Service("userService")
@Transactional()
public class UserServiceImpl implements UserService<UserEntity> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDAO eventDAO;

    @Override
    public UserEntity findUserBuId(Long id) {
        return (UserEntity) userDao.findById(id);
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userDao.findAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public void saveUser(UserEntity user) {
        userDao.save(user);
    }

    /* since we use the Transaction,
     don't need to update, just set fetched user with new parameters */
    @Override
    public void updateUser(UserEntity user) {
        UserEntity user1 = (UserEntity) userDao.findById(user.getId_user());

    }

    @Override
    public void addEvent(String userEmail, EventEntity event) {
        UserEntity entity = (UserEntity) userDao.findByField(userEmail,userEmail);
        EventEntity eventEntity = (EventEntity) eventDAO.getByCode(event.getZip_code());
        if (Objects.nonNull(entity) && Objects.nonNull(eventEntity)) {
            entity.getEvents().add(eventEntity);
        }
    }

    @Override
    public UserEntity findUserByEmail(String value) {
        return (UserEntity) userDao.findByField("email", value);
    }

    @Override
    public boolean isExist(String email) {

        return Objects.nonNull(userDao.findByField(email, email));
    }

    @Override
    public boolean isExistByName(String string) {
        return Objects.nonNull(userDao.findByName(string));
    }
}
