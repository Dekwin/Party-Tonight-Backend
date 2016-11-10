package com.partymaker.mvc.dao.event.ticket;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.TableEntity;
import com.partymaker.mvc.model.whole.TicketEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by anton on 06/11/16.
 */
@Repository("TicketDAO")
public class TicketDAOImpl extends AbstractDao<Integer, TicketEntity> implements TicketDAO{
    public void save(TicketEntity eventEntity) {
        persist(eventEntity);
    }
}
