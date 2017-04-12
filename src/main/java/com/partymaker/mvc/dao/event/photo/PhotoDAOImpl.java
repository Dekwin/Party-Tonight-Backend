package com.partymaker.mvc.dao.event.photo;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.PhotoEntity;
import com.partymaker.mvc.model.whole.TableEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anton on 07/11/16.
 */
@Repository("PhotoDAO")
public class PhotoDAOImpl extends AbstractDao<Integer, PhotoEntity> implements PhotoDAO {
    @Override
    public void save(PhotoEntity photoEntity) {
        persist(photoEntity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PhotoEntity> findAllByEventAndUser(int id_user, String party_name) {
        Query query = getSession().createSQLQuery("SELECT * FROM partymaker2.photo LEFT JOIN partymaker2.event ON partymaker2.photo.id_event = partymaker2.event.id_event and partymaker2.event.party_name =:partyName LEFT JOIN partymaker2.user_has_event ON partymaker2.event.id_event = partymaker2.user_has_event.id_event LEFT JOIN partymaker2.user ON partymaker2.event.id_event = partymaker2.user_has_event.id_user WHERE partymaker2.user.id_user = :idUser")
                .addEntity(PhotoEntity.class)
                .setParameter("partyName", party_name)
                .setParameter("idUser", id_user);
        return (List<PhotoEntity>) query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PhotoEntity> findAllByEventId(int id_event) {
        Query query = null;
        try {
            query = getSession().createSQLQuery("SELECT * FROM partymaker2.photo  WHERE partymaker2.photo.id_event = :id_event")
                    .addEntity(PhotoEntity.class)
                    .setParameter("id_event", id_event);
            return (List<PhotoEntity>) query.list();
        } catch (Exception e) {
            //ignore
        }
        return (List<PhotoEntity>) query;
    }
}
