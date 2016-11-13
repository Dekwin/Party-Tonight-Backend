package com.partymaker.mvc.dao.event.table;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.BottleEntity;
import com.partymaker.mvc.model.whole.TableEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anton on 06/11/16.
 */
@Repository("TableDAO")
public class TableDAOImpl extends AbstractDao<Integer, TableEntity> implements TableDAO {

    public void save(TableEntity eventEntity) {
        persist(eventEntity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TableEntity> findAllByEventAndUser(int id_user, String party_name) {
        Query query = getSession().createSQLQuery("SELECT * FROM `table` LEFT JOIN event ON `table`.id_event = event.id_event and event.party_name =:partyName LEFT JOIN user_has_event ON event.id_event = user_has_event.id_event LEFT JOIN user ON event.id_event = user_has_event.id_user WHERE user_has_event.id_user = :idUser")
                .addEntity(TableEntity.class)
                .setParameter("partyName", party_name)
                .setParameter("idUser", id_user);
        return (List<TableEntity>) query.list();
    }
}
