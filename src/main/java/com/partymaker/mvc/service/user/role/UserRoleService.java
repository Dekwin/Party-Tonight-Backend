package com.partymaker.mvc.service.user.role;


/**
 * Created by anton on 11/10/16.
 */
public interface UserRoleService<T> {

    T findUserRoleByName(String roleName);

    T findUserRoleById(Integer id);
}
