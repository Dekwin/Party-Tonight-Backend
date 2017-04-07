package com.partymaker.mvc.dao.user;


import com.partymaker.mvc.model.enums.Roles;
import com.partymaker.mvc.model.whole.UserEntity;

import java.io.Serializable;
import java.util.List;

public interface UserDao<T, PK extends Serializable> {

    T findById(PK id);

    List<T> findAll();

    void deleteUser(T user);

    void save(T user);

    UserEntity findByEmail(String value);

    T findByName(String name);

    List<UserEntity> findAll(int offset, int limit, Roles role);
}
