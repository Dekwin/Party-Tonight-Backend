package com.partymaker.mvc.dao.user;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.user.role.UserRole;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by anton on 10/10/16.
 */
@Repository("userRoleDao")
public class UserRoleDaoImpl extends AbstractDao<Integer,UserRole> implements UserRoleDao<UserRole, Integer> {

    @SuppressWarnings("unchecked")
    @Override
    public List<UserRole> findAllRoles() {
        Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("role"));
        return (List<UserRole>) crit.list();
    }

    @Override
    public UserRole findById(Integer id) {
        return getByKey(id);
    }

    @Override
    public UserRole findByField(String nameField, Object value) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq(nameField, value));
        return (UserRole) criteria.uniqueResult();
    }
}
