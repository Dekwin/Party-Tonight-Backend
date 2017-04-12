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
                "FROM partymaker2.ticket\n" +
                "  LEFT JOIN partymaker2.event ON partymaker2.ticket.id_event = partymaker2.event.id_event AND partymaker2.event.party_name =:party_name\n" +
                "  LEFT JOIN partymaker2.user_has_event ON partymaker2.event.id_event = partymaker2.user_has_event.id_event\n" +
                "  LEFT JOIN partymaker2.user ON partymaker2.user_has_event.id_user = partymaker2.user.id_user\n" +
                "WHERE partymaker2.user.id_user =:id_user")
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
            query = getSession().createSQLQuery("SELECT * FROM partymaker2.ticket WHERE partymaker2.ticket.id_event = :id_event")
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
        Query query = getSession().createSQLQuery("SELECT * FROM partymaker2.ticket WHERE partymaker2.ticket.id_event =:id")
                .addEntity(TicketEntity.class)
                .setParameter("id", id);
        return (TicketEntity) query.list().get(0);
    }
}
