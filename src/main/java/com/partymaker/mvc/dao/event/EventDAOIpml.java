package com.partymaker.mvc.dao.event;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.event;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anton on 04/11/16.
 */
@Repository("eventDAO")
public class EventDAOIpml extends AbstractDao<Integer, event> implements EventDAO<event> {

    @Override
    public event getByID(int id) {
        return getByKey(id);
    }

    @Override
    public event getByCode(String time) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("time", time));
        return (event) criteria.uniqueResult();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<event> getAll() {
        return createEntityCriteria().list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<event> getAllByUserId(int id_user) {
        Query query = getSession().createSQLQuery("SELECT * from event JOIN user_has_event ON event.id_event = user_has_event.id_event JOIN user on user_has_event.id_user = user.id_user WHERE user.id_user=:user_id")
                .addEntity(event.class)
                .setParameter("user_id", id_user);
        return (List<event>) query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<event> getAllCode(String code) {
        Query query = getSession().createSQLQuery("SELECT * from event where event.zip_code =:code")
                .addEntity(event.class)
                .setParameter("code", code);
        return (List<event>) query.list();
    }

    @Override
    public void save(event eventEntity) {
        persist(eventEntity);
    }

    @Override
    public void delete(event eventEntity) {
        delete(eventEntity);
    }
}
