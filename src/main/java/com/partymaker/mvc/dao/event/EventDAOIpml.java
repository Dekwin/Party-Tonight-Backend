package com.partymaker.mvc.dao.event;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.EventEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anton on 04/11/16.
 */
@Repository("eventDAO")
public class EventDAOIpml extends AbstractDao<Integer, EventEntity> implements EventDAO<EventEntity> {

    @Override
    public EventEntity getByID(int id) {
        return getByKey(id);
    }

    @Override
    public EventEntity getByCode(String code) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("zip_code", code));
        return (EventEntity) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EventEntity> getAll() {
        return createEntityCriteria().list();
    }

    @Override
    public void save(EventEntity eventEntity) {
        persist(eventEntity);
    }

    @Override
    public void delete(EventEntity eventEntity) {
        delete(eventEntity);
    }
}
