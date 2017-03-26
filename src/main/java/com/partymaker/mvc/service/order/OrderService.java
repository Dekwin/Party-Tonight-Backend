package com.partymaker.mvc.service.order;

import com.partymaker.mvc.dao.order.OrderDAO;
import com.partymaker.mvc.model.business.order.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class OrderService {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Autowired
    OrderedBottleService orderedBottleService;

    @Autowired
    OrderedTableService orderedTableService;

    @Autowired
    OrderedTicketService orderedTicketService;

    @Autowired
    private OrderDAO dao;

    public void save(OrderEntity order) {
        order.setDate(getDateCreated());

        dao.save(order);

        order.getBottles().forEach(v -> orderedBottleService.save(v));
        orderedTableService.save(order.getTable());
        orderedTicketService.save(order.getTicket());
    }

    private String getDateCreated() {
        Date date = new Date();
        return String.valueOf(dateFormat.format(date));
    }
}
