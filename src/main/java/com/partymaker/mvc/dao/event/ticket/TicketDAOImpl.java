package com.partymaker.mvc.dao.event.ticket;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.TableEntity;
import com.partymaker.mvc.model.whole.TicketEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
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
        Query query = getSession().createSQLQuery("SELECT * FROM ticket LEFT JOIN event ON ticket.id_event = event.id_event and event.party_name =:partyName LEFT JOIN user_has_event ON event.id_event = user_has_event.id_event LEFT JOIN user ON event.id_event = user_has_event.id_user WHERE user.id_user = :idUser")
                .addEntity(TicketEntity.class)
                .setParameter("partyName", party_name)
                .setParameter("idUser", id_user);
        return (List<TicketEntity>) query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TicketEntity> findAllByEventId(int id_event) {
        Query query = null;
        try {
            query = getSession().createSQLQuery("SELECT * FROM partymaker2.ticket  WHERE ticket.id_event = :id_event")
                    .addEntity(TicketEntity.class)
                    .setParameter("id_event", id_event);
            return (List<TicketEntity>) query.list();
        } catch (Exception e) {
            // ignore
        }
        return (List<TicketEntity>) query;
    }
}
