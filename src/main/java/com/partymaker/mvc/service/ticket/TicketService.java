package com.partymaker.mvc.service.ticket;

import com.partymaker.mvc.model.whole.TicketEntity;

import java.util.List;

/**
 * Created by anton on 06/11/16.
 */
public interface TicketService {

    void save(TicketEntity ticketEntity);

    List<TicketEntity> findAllTicketsByEventAndUser(int id_user, String party_name);
}
