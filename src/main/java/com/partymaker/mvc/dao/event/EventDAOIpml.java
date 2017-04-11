package com.partymaker.mvc.dao.event;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.DataResponse;
import com.partymaker.mvc.model.whole.event;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Properties;

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

    @Override
    public List<event> getAll(int offset, int limit) {
        Criteria criteria =  createEntityCriteria();
        criteria.setFirstResult(offset);
        criteria.setMaxResults(limit);
        criteria.addOrder(Order.desc("date"));
        return criteria.list();


    }


    public DataResponse<event> getAll(int offset, int limit, Order order) {
        Criteria c = createEntityCriteria();
        if(order != null)
        c.addOrder(order);
        c.setFirstResult(offset);
        c.setMaxResults(limit);
        List<event> items = c.list();

        c = createEntityCriteria();
        c.setProjection(Projections.rowCount());
        Long count = (Long) c.uniqueResult();
        return new DataResponse<>(items,count) ;
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

    @Override
    public event getEventByName(String name) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("party_name", name));
        return (event) criteria.list().get(0);
    }
}
