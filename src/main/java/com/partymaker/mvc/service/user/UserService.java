package com.partymaker.mvc.service.user;

import com.partymaker.mvc.model.user.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by anton on 09/10/16.
 */
public interface UserService {

    User findUserBuId(Long id);

    List<User> findAllUsers();

    void deleteUser(Long id);

    void saveUser(User user);

    void updateUser(User user);

    User findUserByEmail(String value);

    boolean isExist(String email);
}
