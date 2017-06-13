package com.partymaker.mvc.service.ticket;

import com.partymaker.mvc.dao.event.ticket.TicketDAO;
import com.partymaker.mvc.model.whole.TicketEntity;
import com.partymaker.mvc.service.user.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by anton on 06/11/16.
 */
@Transactional
@Service("TicketService")
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private TicketDAO ticketDAO;

    @Override
    public void save(TicketEntity ticketEntity) {
        ticketDAO.save(ticketEntity);
    }

    @Override
    public List<TicketEntity> findAllTicketsByEventAndUser(int id_user, String party_name) {
        try {
            return ticketDAO.findAllByEventAndUser(id_user, party_name);
        } catch (Exception e) {
            logger.info("Error get all tickets by user id", e);
            return null;
        }
    }

    @Override
    public List<TicketEntity> findAllTicketsByEventId(int id_event) {
        return ticketDAO.findAllByEventId(id_event);
    }

    @Override
    public TicketEntity findTicket(int id_event) {
        return ticketDAO.getTicketByEventId(id_event);
    }

}
