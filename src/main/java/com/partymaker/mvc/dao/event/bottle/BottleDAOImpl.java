package com.partymaker.mvc.dao.event.bottle;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.BottleEntity;
import com.partymaker.mvc.model.whole.event;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anton on 06/11/16.
 */
@Repository("BottleDAO")
public class BottleDAOImpl extends AbstractDao<Integer, BottleEntity> implements BottleDAO {

    public BottleEntity getByID(int id) {
        return getByKey(id);
    }

    public BottleEntity getByCode(String party_name) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("party_name", party_name));
        return (BottleEntity) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<event> getAll() {
        return createEntityCriteria().list();
    }

    @Override
    public void save(BottleEntity eventEntity) {
        persist(eventEntity);
    }

    @SuppressWarnings("unckecked")
    @Override
    public List<BottleEntity> findAllByEvent(int event_id) {
        Query query = getSession().createQuery("from bottle where id_event = :event_id");
        query.setParameter("event_id", event_id);
        return query.list();
    }

    public void delete(BottleEntity eventEntity) {
        delete(eventEntity);
    }
}
