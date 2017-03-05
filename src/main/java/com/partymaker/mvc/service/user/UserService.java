package com.partymaker.mvc.service.user;

import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.model.whole.event;

import java.util.List;

/**
 * Created by anton on 09/10/16.
 */
public interface UserService<T> {

    T findUserBuId(int id);

    List<T> findAllUsers();

    void deleteUser(int id);

    void createUser(T user);

    void updateUser(T user);

    void addEvent(String userEmail, event event);

    T findUserByEmail(String value);

    boolean isExistByEmail(String email);

    boolean isExistByName(String string);

    void isExistUserRequiredFields(UserEntity user);

    void validationUser(T user);

    void userLock(int id_user);

    void userUnLock(int id_user);
}
