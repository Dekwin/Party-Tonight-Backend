package com.partymaker.mvc.dao.user;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.RoleEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anton on 10/10/16.
 */
@Repository("userRoleDao")
public class UserRoleDaoImpl extends AbstractDao<Integer,RoleEntity> implements UserRoleDao<RoleEntity, Integer> {

    @SuppressWarnings("unchecked")
    @Override
    public List<RoleEntity> findAllRoles() {
        Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("role"));
        return (List<RoleEntity>) crit.list();
    }

    @Override
    public RoleEntity findById(Integer id) {
        return getByKey(id);
    }

    @Override
    public RoleEntity findByField(String nameField, Object value) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq(nameField, value));
        return (RoleEntity) criteria.uniqueResult();
    }


}
