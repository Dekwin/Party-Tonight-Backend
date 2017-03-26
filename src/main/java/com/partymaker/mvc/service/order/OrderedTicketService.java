package com.partymaker.mvc.service.order;

import com.partymaker.mvc.dao.order.OrderedTicketDAO;
import com.partymaker.mvc.model.business.order.OrderedTicket;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderedTicketService {

    OrderedTicketDAO dao;

    public void save(OrderedTicket ticket) {
        dao.save(ticket);
    }
}

