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

    void deleteUser(Long id);

    void saveUser(T user);

    void updateUser(T user);

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
}
