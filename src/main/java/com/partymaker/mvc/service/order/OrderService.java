package com.partymaker.mvc.service.order;

import com.partymaker.mvc.dao.order.OrderDAO;
import com.partymaker.mvc.model.business.order.OrderEntity;
import com.partymaker.mvc.model.business.order.OrderWrapped;
import com.partymaker.mvc.model.business.order.OrderedBottle;
import com.partymaker.mvc.model.business.order.OrderedTable;
import com.partymaker.mvc.model.whole.TicketEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.service.bottle.BottleService;
import com.partymaker.mvc.service.event.EventService;
import com.partymaker.mvc.service.table.TableService;
import com.partymaker.mvc.service.ticket.TicketService;
import com.partymaker.mvc.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    TransactionService transactionService;

    @Autowired
    UserService<UserEntity> userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private BottleService bottleService;

    @Autowired
    private TableService tableService;

    @Autowired
    private EventService eventService;

    @Autowired
    private OrderDAO dao;

    public void save(OrderEntity order) {
        dao.save(order);

        order.getBottles().forEach(v -> orderedBottleService.save(v));
        orderedTableService.save(order.getTable());
        orderedTicketService.save(order.getTicket());
    }

    public List<OrderEntity> findAllForEvent(event event) {
        return dao.findAllForEvent(event);
    }


    public List<OrderWrapped> getAllOrdersWrapped(event event) {
        List<OrderWrapped> ordersWrapped = new ArrayList<>();


        for (OrderEntity order : findAllForEvent(event)) {
            UserEntity customer = userService.findUserByEmail(order.getTransactionId().getCustomerEmail());

            double ticketsSubtotal = calculateSubtotalForTickets(order);
            double tablesSubtotal = calculateSubtotalForTables(order);
            double bottlesSubtotal = calculateSubtotalForBottles(order);

            double subtotal = ticketsSubtotal + tablesSubtotal + bottlesSubtotal;

            ordersWrapped.add(new OrderWrapped(
                    customer.getId_user(),
                    customer.getUserName(),
                    ticketsSubtotal,
                    tablesSubtotal,
                    bottlesSubtotal,
                    subtotal
            ));
        }

        return ordersWrapped;
    }

    public List<OrderWrapped> getAllOrdersWrapped(int event_id) {
        return getAllOrdersWrapped(eventService.findById(event_id));
    }


    // we have only one type of tickets now
    // so it's necessary to update this method
    // if types of ticket are added
    private double calculateSubtotalForTickets(OrderEntity order) {
        if (order.getTicket() != null) {
            TicketEntity ticketOfEvent = ticketService.findTicket(order.getEventId());

            // we can order only one ticket
            // idk why. but only one
            return Double.parseDouble(ticketOfEvent.getPrice())
                    * 1;
        } else return 0;
    }

    private double calculateSubtotalForBottles(OrderEntity order) {
        double result = 0;

        for (OrderedBottle bottle : order.getBottles()) {
            result += Double.parseDouble(bottleService.findBottleByEventIdAndType(
                    order.getEventId(), bottle.getTitle()).getPrice()) *
                    bottle.getAmount();
        }

        return result;
    }

    // we can order only one table
    private double calculateSubtotalForTables(OrderEntity order) {
        if (order.getTable() != null) {
            OrderedTable table = order.getTable();

            return Double.parseDouble(
                    tableService.findTableByEventIdAndType(order.getEventId(), table.getType())
                            .getPrice());
        } else return 0;
    }
}
