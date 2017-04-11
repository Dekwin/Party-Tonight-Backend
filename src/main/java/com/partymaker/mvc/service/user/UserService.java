package com.partymaker.mvc.service.user;

import com.partymaker.mvc.model.DataResponse;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.model.whole.event;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Created by anton on 09/10/16.
 */
public interface UserService<T> {

    T findUserById(int id);

    List<T> findAllUsers();

    List<UserEntity> findByRole(int offset, int limit, String role);

    DataResponse<UserEntity> findByRole(int offset, int limit, String role, Order order);

    void deleteUser(Integer id);

    void saveUser(T user);

    void updateUser(T user);

    UserEntity updateEmail(String currentEmail,String newEmail);

    void addEvent(String userEmail, event event);

    int getUserIdByEventId(int id);

    T findUserByEmail(String value);

    boolean isExistByEmail(String email);

    boolean isExistByName(String string);

    void isExistUserRequiredFields(UserEntity user);

    void validationUser(T user);

    String getPrincipal();

    UserEntity findByName(String name);

    UserEntity getCurrentUser();

    void disableUserById(int id_user);

    void enableUserById(int id_user);

    void setUserVerifiedById(int id_user);

    void setUserNotVerifiedById(int id_user);
}
