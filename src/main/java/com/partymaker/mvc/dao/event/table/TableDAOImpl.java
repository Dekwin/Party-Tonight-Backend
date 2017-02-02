package com.partymaker.mvc.dao.event.table;

import com.partymaker.mvc.dao.AbstractDao;
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
        Query query = getSession().createSQLQuery("SELECT *\n" +
                "FROM `table`\n" +
                "  JOIN event ON `table`.id_event = event.id_event AND event.party_name =:party_name\n" +
                "  JOIN user_has_event ON event.id_event = user_has_event.id_event\n" +
                "WHERE user_has_event.id_user =:user_id\n")
                .addEntity(TableEntity.class)
                .setParameter("party_name", party_name)
                .setParameter("user_id", id_user);
        return (List<TableEntity>) query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TableEntity> findAllByEventId(int id_event) {
        Query query = null;
        try {
            query = getSession().createSQLQuery("SELECT * FROM `table`  WHERE `table`.id_event = :id_event")
                    .addEntity(TableEntity.class)
                    .setParameter("id_event", id_event);
            return (List<TableEntity>) query.list();
        } catch (Exception e) {
            //ignore
        }
        return (List<TableEntity>) query;
    }
}
