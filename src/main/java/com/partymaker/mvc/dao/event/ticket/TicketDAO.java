package com.partymaker.mvc.dao.event.ticket;

import com.partymaker.mvc.model.whole.TicketEntity;

import java.util.List;

/**
 * Created by anton on 07/11/16.
 */
public interface TicketDAO {

    void save(TicketEntity ticketEntity);

    List<TicketEntity> findAllByEventAndUser(int id_user, String party_name);
}
