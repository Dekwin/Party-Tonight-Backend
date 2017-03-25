package com.partymaker.mvc.dao.event.ticket;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.TicketEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anton on 06/11/16.
 */
@Repository("TicketDAO")
public class TicketDAOImpl extends AbstractDao<Integer, TicketEntity> implements TicketDAO {
    public void save(TicketEntity eventEntity) {
        persist(eventEntity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TicketEntity> findAllByEventAndUser(int id_user, String party_name) {
        Query query = getSession().createSQLQuery("SELECT *\n" +
                "FROM ticket\n" +
                "  LEFT JOIN event ON ticket.id_event = event.id_event AND event.party_name =:party_name\n" +
                "  LEFT JOIN user_has_event ON event.id_event = user_has_event.id_event\n" +
                "  LEFT JOIN user ON user_has_event.id_user = user.id_user\n" +
                "WHERE user.id_user =:id_user")
                .addEntity(TicketEntity.class)
                .setParameter("party_name", party_name)
                .setParameter("id_user", id_user);
        return (List<TicketEntity>) query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TicketEntity> findAllByEventId(int id_event) {
        Query query = null;
        try {
            query = getSession().createSQLQuery("SELECT * FROM partymaker2.ticket WHERE ticket.id_event = :id_event")
                    .addEntity(TicketEntity.class)
                    .setParameter("id_event", id_event);
            return (List<TicketEntity>) query.list();
        } catch (Exception e) {
            // ignore
        }
        return (List<TicketEntity>) query;
    }


    @Override
    public TicketEntity getTicketByEventId(int id) {
        Query query = getSession().createSQLQuery("SELECT * FROM ticket WHERE ticket.id_event =:id")
                .addEntity(TicketEntity.class)
                .setParameter("id", id);
        return (TicketEntity) query.list().get(0);
    }
}
