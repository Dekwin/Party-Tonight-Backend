package com.partymaker.mvc.dao.user;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anton on 10/10/16.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, UserEntity> implements UserDao<UserEntity, Integer> {

    @Override
    public UserEntity findById(Integer id) {
        UserEntity userEntity = getByKey(id);
        return userEntity;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<UserEntity> findAll() {

        return getSession().createCriteria(UserEntity.class).list();
    }

    @Override
    public void deleteUser(UserEntity user) {
        delete(user);
    }

    @Override
    public void save(UserEntity user) {
        persist(user);
    }


    public UserEntity findByEmail(String nameField, String value) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("email", value));
        /*System.out.println("value= " + value);
        System.out.println("thi is-..............................."+criteria.uniqueResult());*/
        return (UserEntity) criteria.uniqueResult();
    }

    @Override
    public UserEntity findByName(String name) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("userName", name));

        return (UserEntity) criteria.uniqueResult();
    }

    @Override
    public void lock(int id) {
        Query q = getSession().createSQLQuery("UPDATE user SET enable = FALSE WHERE id_user =:user_id")
                .setParameter("user_id", id);
        q.executeUpdate();
    }
}
