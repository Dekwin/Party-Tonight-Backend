package com.partymaker.mvc.dao.user;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.user.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anton on 10/10/16.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Long,User> implements UserDao<User, Long> {

    @Override
    public User findById(Long id) {
        return findById(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll() {
        Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("nickName"));
        return (List<User>) crit.list();
    }

    @Override
    public void deleteUser(User user) {
        delete(user);
    }

    @Override
    public void save(User user) {
        persist(user);
    }


    @Override
    public User findByField(String nameField, String value) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("email", value));
        /*System.out.println("value= " + value);
        System.out.println("thi is-..............................."+criteria.uniqueResult());*/
        return (User) criteria.uniqueResult();
    }
}
