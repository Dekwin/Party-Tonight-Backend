package com.partymaker.mvc.dao.event;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.event;
import org.hibernate.Criteria;
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

    @Override
    public void save(event eventEntity) {
        persist(eventEntity);
    }

    @Override
    public void delete(event eventEntity) {
        delete(eventEntity);
    }
}