package com.partymaker.mvc.dao.user;


import com.partymaker.mvc.model.enums.Roles;
import com.partymaker.mvc.model.whole.UserEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by anton on 10/10/16.
 */
public interface UserRoleDao<T, PK extends Serializable> {
    List<T> findAllRoles();

    T findById(PK id);

    T findByField(String nameField, Object value);


}
