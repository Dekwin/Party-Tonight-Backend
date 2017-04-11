package com.partymaker.mvc.service.user;

import com.partymaker.mvc.dao.event.EventDAO;
import com.partymaker.mvc.dao.user.UserDao;
import com.partymaker.mvc.dao.user.UserToEventDAO;
import com.partymaker.mvc.model.DataResponse;
import com.partymaker.mvc.model.enums.Roles;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.model.whole.event;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private static Date date;
    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private UserToEventDAO userToEventDAO;

    @Override
    public UserEntity findUserById(int id) {
        return (UserEntity) userDao.findById(id);
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<UserEntity> findByRole(int offset, int limit, String role){

        if(Roles.ROLE_ADMIN.toString().equals(role)) {
            return userDao.findAll(offset, limit, Roles.ROLE_ADMIN);
        }
        if(Roles.ROLE_PARTY_MAKER.toString().equals(role)) {
            return userDao.findAll(offset, limit, Roles.ROLE_PARTY_MAKER);
        }
        if(Roles.ROLE_STREET_DANCER.toString().equals(role)) {
            return userDao.findAll(offset, limit, Roles.ROLE_STREET_DANCER);
        }
        return userDao.findAll(offset, limit, Roles.ROLE_STREET_DANCER);
    }


    @Override
    public DataResponse<UserEntity> findByRole(int offset, int limit, String role, Order order){

        if(Roles.ROLE_ADMIN.toString().equals(role)) {
            return userDao.findAll(offset, limit, Roles.ROLE_ADMIN, order);
        }
        if(Roles.ROLE_PARTY_MAKER.toString().equals(role)) {
            return userDao.findAll(offset, limit, Roles.ROLE_PARTY_MAKER, order);
        }
        if(Roles.ROLE_STREET_DANCER.toString().equals(role)) {
            return userDao.findAll(offset, limit, Roles.ROLE_STREET_DANCER, order);
        }
        return userDao.findAll(offset, limit, Roles.ROLE_STREET_DANCER, order);
    }

    @Override
    public UserEntity updateEmail(String currentEmail,String newEmail){
        UserEntity entity = findUserByEmail(currentEmail);
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (emailValidator.isValid(newEmail) && !entity.getEmail().equals(newEmail)) {
            entity.setEmail(newEmail);
        }else throw new BadCredentialsException("email is invalid");
      updateUser(entity);
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deleteUser(Integer id) {
        userDao.deleteUser(userDao.findById(id));
    }

    @Override
    public void saveUser(UserEntity user) {
        date = new Date();
        user.setCreatedDate(dateFormat.format(date));

        user.setEnable(true);
        if (user.getBillingEmail() != null) {
            user.setBillingEmail(user.getBillingEntity().getBilling_email());
        }

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
        UserEntity entity = (UserEntity) userDao.findByEmail(userEmail);

        event eventEntity = (event) eventDAO.getByCode(event.getTime());

        if (Objects.nonNull(entity) && Objects.nonNull(eventEntity)) {
            entity.getEvents().add(eventEntity);

            //fixme
            eventEntity.getUsers().add(entity);
        }
    }

    @Override
    public int getUserIdByEventId(int id) {
        return userToEventDAO.getUserIdByEventId(id);
    }

    @Override
    public UserEntity findUserByEmail(String value) {
        return (UserEntity) userDao.findByEmail(value);
    }

    @Override
    public boolean isExistByEmail(String email) {
        return Objects.nonNull(userDao.findByEmail(email));
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
//        if (billingService.isExist(user.getBilling())) {
//            throw new RuntimeException("User with current billing info is already exist!");
//        }
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
    }

    public UserEntity findByName(String name) {
        return (UserEntity) userDao.findByName(name);
    }

    @Override
    public UserEntity getCurrentUser() {
        return findUserByEmail(getPrincipal());
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    public String getPrincipal() {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }


    @Override
    public void disableUserById(int id_user) {
        logger.info("Locking user with id = " + id_user);
        UserEntity userEntity = (UserEntity) userDao.findById(id_user);
        userEntity.setEnable(false);
    }

    @Override
    public void enableUserById(int id_user) {
        logger.info("Unlocking user with id = " + id_user);
        UserEntity userEntity = (UserEntity) userDao.findById(id_user);
        userEntity.setEnable(true);
    }

    @Override
    public void setUserVerifiedById(int id_user) {
        logger.info("Verified user with id = " + id_user);
        UserEntity userEntity = (UserEntity) userDao.findById(id_user);
        userEntity.setVerified(true);
    }

    @Override
    public void setUserNotVerifiedById(int id_user) {
        logger.info("Not verified user with id = " + id_user);
        UserEntity userEntity = (UserEntity) userDao.findById(id_user);
        userEntity.setVerified(false);
    }
}
