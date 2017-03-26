package com.partymaker.mvc.dao.user;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anton on 10/10/16.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer,UserEntity> implements UserDao<UserEntity, Integer> {

    @Override
    public UserEntity findById(Integer id) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id_user", id));
        return (UserEntity) criteria.uniqueResult();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<UserEntity> findAll() {
        Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("user_name"));
        return (List<UserEntity>) crit.list();
    }

    @Override
    public void deleteUser(UserEntity user) {
        delete(user);
    }

    @Override
    public void save(UserEntity user) {
        persist(user);
    }


    @Override
    public UserEntity findByEmail(String value) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("email", value));
        return (UserEntity) criteria.uniqueResult();
    }

    @Override
    public UserEntity findByName(String name) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("userName", name));

        return (UserEntity) criteria.uniqueResult();
    }
}
