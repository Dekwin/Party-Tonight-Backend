package com.partymaker.mvc.service.user.role;

import com.partymaker.mvc.model.user.role.UserRole;

/**
 * Created by anton on 11/10/16.
 */
public interface UserRoleService {

    UserRole findUserRoleByName(String roleName);

    UserRole findUserRoleById(Integer id);
}
