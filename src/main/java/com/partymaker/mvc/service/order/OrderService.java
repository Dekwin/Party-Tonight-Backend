package com.partymaker.mvc.service.order;

import com.partymaker.mvc.dao.order.OrderDAO;
import com.partymaker.mvc.model.business.order.OrderEntity;
import com.partymaker.mvc.model.business.order.OrderedBottle;
import com.partymaker.mvc.model.business.order.OrderedTable;
import com.partymaker.mvc.model.business.order.OrderedTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        List<OrderedBottle> bottles = order.getBottles();
        List<OrderedTable> tables = order.getTables();
        List<OrderedTicket> tickets = order.getTickets();

        order.setDate(getDateCreated());

        dao.save(order);

        bottles.forEach(v -> v.setOrder(order));
        tables.forEach(v -> v.setOrder(order));
        tickets.forEach(v -> v.setOrder(order));

        bottles.forEach(v -> orderedBottleService.save(v));
        tables.forEach(v -> orderedTableService.save(v));
        tickets.forEach(v -> orderedTicketService.save(v));
    }

    private String getDateCreated() {
        Date date = new Date();
        return String.valueOf(dateFormat.format(date));
    }
}
