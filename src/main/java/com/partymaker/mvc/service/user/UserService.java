package com.partymaker.mvc.service.user;

import com.partymaker.mvc.model.whole.EventEntity;
import com.partymaker.mvc.model.whole.UserEntity;

import java.util.List;

/**
 * Created by anton on 09/10/16.
 */
public interface UserService<T> {

    T findUserBuId(Long id);

    List<T> findAllUsers();

    void deleteUser(Long id);

    void saveUser(T user);

    void updateUser(T user);

    void addEvent(String userEmail, EventEntity event);

    T findUserByEmail(String value);

    boolean isExist(String email);

    boolean isExistByName(String string);
}
