package com.partymaker.mvc.service.ticket;

import com.partymaker.mvc.dao.event.ticket.TicketDAO;
import com.partymaker.mvc.model.whole.TicketEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by anton on 06/11/16.
 */
@Transactional
@Service("TicketService")
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketDAO ticketDAO;

    @Override
    public void save(TicketEntity ticketEntity) {
        ticketDAO.save(ticketEntity);
    }
}
