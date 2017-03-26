package com.partymaker.mvc.dao.order;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.business.order.OrderedTicket;
import org.springframework.stereotype.Repository;

@Repository("orderedTicketDAO")
public class OrderedTicketDAO extends AbstractDao<Integer, OrderedTicket> {

    public void save(OrderedTicket ticket) {
        persist(ticket);
    }
}