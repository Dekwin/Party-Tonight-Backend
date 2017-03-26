package com.partymaker.mvc.dao.order;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.business.order.OrderedTicket;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderedTicketDAO")
public class OrderedTicketDAO extends AbstractDao<Integer, OrderedTicket> {

    public void save(OrderedTicket ticket) {
        persist(ticket);
    }


    @SuppressWarnings("unchecked")
    public List<OrderedTicket> getTicketsByType(int id_event, String type) {
        Query query = getSession().createSQLQuery("SELECT * from \n" +
                "`ordered_ticket` JOIN `order` ON `ordered_ticket`.`id_order` \n" +
                "WHERE (`order`.`id_event`=:id_event AND `ordered_ticket`.`type`=:type)")
                .addEntity(OrderedTicket.class)
                .setParameter("id_event", id_event)
                .setParameter("type", type);
        return (List<OrderedTicket>) query.list();
    }
}