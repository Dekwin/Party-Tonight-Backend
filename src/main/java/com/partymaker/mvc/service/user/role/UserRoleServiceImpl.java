package com.partymaker.mvc.service.user.role;

import com.partymaker.mvc.dao.user.UserRoleDao;
import com.partymaker.mvc.model.user.role.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by anton on 11/10/16.
 */
@Service("userRoleService")
@Transactional( )
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    @Qualifier("userRoleDao")
    private UserRoleDao dao;

    @Override
    public UserRole findUserRoleByName(String roleName) {
        return (UserRole) dao.findByField("role", roleName);
    }

    @Override
    public UserRole findUserRoleById(Integer id) {
        return (UserRole) dao.findById(id);
    }
}
