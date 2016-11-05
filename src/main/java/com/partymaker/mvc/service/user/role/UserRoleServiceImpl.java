package com.partymaker.mvc.service.user.role;

import com.partymaker.mvc.dao.user.UserRoleDao;
import com.partymaker.mvc.model.whole.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by anton on 11/10/16.
 */
@Service("userRoleService")
@Transactional( )
public class UserRoleServiceImpl implements UserRoleService<RoleEntity> {

    @Autowired
    @Qualifier("userRoleDao")
    private UserRoleDao dao;

    @Override
    public RoleEntity findUserRoleByName(String roleName) {
        return (RoleEntity) dao.findByField("role", roleName);
    }

    @Override
    public RoleEntity findUserRoleById(Integer id) {
        return (RoleEntity) dao.findById(id);
    }
}
