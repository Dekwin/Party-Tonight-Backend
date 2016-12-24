package com.partymaker.mvc.dao.event.bottle;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.BottleEntity;
import com.partymaker.mvc.model.whole.event;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    @SuppressWarnings("unchecked")
    @Override
    public List<BottleEntity> findAllByEventAndUser(int id_user, String party_name) {
        Query query = null;
        try {
            query = getSession().createSQLQuery("SELECT * FROM bottle LEFT JOIN event ON bottle.id_event = event.id_event and event.party_name =:partyName LEFT JOIN user_has_event ON event.id_event = user_has_event.id_event LEFT JOIN user ON event.id_event = user_has_event.id_user WHERE user.id_user = :idUser")
                    .addEntity(BottleEntity.class)
                    .setParameter("partyName", party_name)
                    .setParameter("idUser", id_user);
            return (List<BottleEntity>) query.list();
        } catch (Exception e) {
            // ignore
        }
        return (List<BottleEntity>) query;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BottleEntity> findAllByEventId(int id_event) {
        Query query = null;
        try {
            query = getSession().createSQLQuery("SELECT * FROM bottle WHERE bottle.id_event = :id_event")
                    .addEntity(BottleEntity.class)
                    .setParameter("id_event", id_event);
            return (List<BottleEntity>) query.list();
        } catch (Exception e) {
            // ignore
        }
        return (List<BottleEntity>) query;
    }

    public void delete(BottleEntity eventEntity) {
        delete(eventEntity);
    }
}
