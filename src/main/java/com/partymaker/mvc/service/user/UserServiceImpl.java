package com.partymaker.mvc.service.user;

import com.partymaker.mvc.dao.event.EventDAO;
import com.partymaker.mvc.dao.user.UserDao;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.service.billing.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by anton on 09/10/16.
 */
@Service("userService")
@Transactional()
public class UserServiceImpl implements UserService<UserEntity> {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static Date date;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    BillingService billingService;

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
        date = new Date();
        user.setCreatedDate(dateFormat.format(date));

        logger.info("saving billing");
        billingService.saveBilling(user.getBilling());
        logger.info("saved billing");

        logger.info("Getting billing");
        user.setBilling(billingService.findByCard(user.getBilling().getCard_number()));

        userDao.save(user);
    }

    /* since we use the Transaction,
     don't need to update, just set fetched user with new parameters */
    @Override
    public void updateUser(UserEntity user) {
        UserEntity user1 = (UserEntity) userDao.findById(user.getId_user());

    }

    @Override
    public void addEvent(String userEmail, event event) {
        UserEntity entity = (UserEntity) userDao.findByField(userEmail, userEmail);

        event eventEntity = (event) eventDAO.getByCode(event.getTime());

        if (Objects.nonNull(entity) && Objects.nonNull(eventEntity)) {
            entity.getEvents().add(eventEntity);
        }
    }

    @Override
    public UserEntity findUserByEmail(String value) {
        return (UserEntity) userDao.findByField("email", value);
    }

    @Override
    public boolean isExistByEmail(String email) {
        return Objects.nonNull(userDao.findByField(email, email));
    }

    @Override
    public boolean isExistByName(String string) {
        return Objects.nonNull(userDao.findByName(string));
    }

    @Override
    public void isExistUserRequiredFields(UserEntity user) {

        validationUser(user);

        if (isExistByEmail(user.getEmail())) {
            throw new RuntimeException("User with such email is already exist!");
        }
        if (isExistByName(user.getUserName())) {
            throw new RuntimeException("Bad user name!");
        }
        if (billingService.isExist(user.getBilling())) {
            throw new RuntimeException("User with current billing info is already exist!");
        }
    }

    @Override
    public void validationUser(UserEntity user) {
        if (user == null)
            throw new RuntimeException("User cannot be null");
        if (user.getEmail() == null || user.getEmail().isEmpty())
            throw new RuntimeException("User email is required!");
        if (user.getPassword() == null || user.getPassword().isEmpty())
            throw new RuntimeException("User password is required!");
        if (user.getUserName() == null || user.getUserName().isEmpty())
            throw new RuntimeException("User name is required!");
        if (user.getBilling() == null)
            throw new RuntimeException("User billing info is required!");
    }

    @Override
    public void userLock(long id_user) {
        logger.info("Locking user with id = " + id_user);
        findUserBuId(id_user).setEnable(false);
    }

    @Override
    public void userUnLock(long id_user) {
        logger.info("Locking user with id = " + id_user);
        findUserBuId(id_user).setEnable(true);
    }
}
