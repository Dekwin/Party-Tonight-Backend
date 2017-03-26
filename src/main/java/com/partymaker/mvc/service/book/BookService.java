package com.partymaker.mvc.service.book;

import com.partymaker.mvc.dao.event.EventDAO;
import com.partymaker.mvc.dao.event.bottle.BottleDAO;
import com.partymaker.mvc.dao.event.table.TableDAO;
import com.partymaker.mvc.dao.event.ticket.TicketDAO;
import com.partymaker.mvc.model.business.Book;
import com.partymaker.mvc.model.business.booking.BookedBottle;
import com.partymaker.mvc.model.business.booking.Booking;
import com.partymaker.mvc.model.business.order.OrderedTable;
import com.partymaker.mvc.model.business.order.OrderedTicket;
import com.partymaker.mvc.model.whole.BottleEntity;
import com.partymaker.mvc.model.whole.TableEntity;
import com.partymaker.mvc.model.whole.TicketEntity;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.service.order.OrderedBottleService;
import com.partymaker.mvc.service.order.OrderedTableService;
import com.partymaker.mvc.service.order.OrderedTicketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class BookService {

    private static final Logger logger = Logger.getLogger(BookService.class);


    @Autowired
    private EventDAO<event> eventDAO;

    @Autowired
    private BottleDAO bottleDAO;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private TableDAO tableDAO;

    @Autowired
    private OrderedBottleService orderedBottleService;

    @Autowired
    private OrderedTableService orderedTableService;

    @Autowired
    private OrderedTicketService orderedTicketService;




    public String book(Book book) {
        logger.info("Received book = " + book);
        event e = eventDAO.getEventByName(book.getPartyName());

        TicketEntity t = ticketDAO.getTicketByEventId(e.getId_event());

        validateTickets(t, book);

        List<BottleEntity> bottleEntity = bottleDAO.getBottleByEventId(e.getId_event());

        bottleEntity.forEach(b -> {
            book.getBottles().forEach(v -> {
                if (b.getType().equals(v.getType())) {
                    int aa = Integer.parseInt(b.getAvailable());
                    int bb = Integer.parseInt(b.getBooked());

                    if (aa == bb) {
                        logger.info("No more bottles left.");
                        throw new RuntimeException("No more bottles of " + v.getName() + "left.");
                    }

                    if (aa - bb < Integer.parseInt(v.getBooked())) {
                        logger.info("Only " + (aa - bb) + " bottles left.");
                        throw new RuntimeException("Only " + (aa - bb) + " of " + v.getName() + " left.");
                    }

                    b.setBooked(String.valueOf(bb + Integer.parseInt(v.getBooked())));
                }
            });
        });

        if (book.getTables() != null && !book.getTables().isEmpty()) {
            TableEntity ta = tableDAO.getTableByEventId(e.getId_event(), book.getTables().get(0).getType());
            ta.setBooked(String.valueOf(Integer.parseInt(ta.getBooked()) + book.getTables().size()));
        }

        t.setBooked(String.valueOf(Integer.parseInt(t.getBooked()) + book.getTickets()));

        return "Success";
    }

    private void validateTables(TableEntity ta, Book book) {
        int a = Integer.parseInt(ta.getAvailable());
        int b = Integer.parseInt(ta.getBooked());

        if (a == b) {
            logger.info("No more tables left.");
            throw new RuntimeException("No more tables left.");
        }

        if (a - b < book.getBottles().size()) {
            logger.info("Only " + (a - b) + " tables left.");
            throw new RuntimeException("Only " + (a - b) + " tables left.");
        }
    }

    public void validateTickets(TicketEntity t, Book book) {
        int a = Integer.parseInt(t.getAvailable());
        int b = Integer.parseInt(t.getBooked());

        if (a == b) {
            logger.info("No more tickets left.");
            throw new RuntimeException("No more tickets left.");
        }
        if (a - b < book.getTickets()) {
            logger.info("Only " + (a - b) + " tickets left.");
            throw new RuntimeException("Only " + (a - b) + " tickets left.");
        }
    }

    public List<Booking> validateBookings(Booking[] bookings) {
        List<Booking> order = Arrays.asList(bookings);

        for (Booking booking : order) {
            List<BottleEntity> bottles = bottleDAO.getBottleByEventId(booking.getId_event());

            for (BookedBottle bookedBottle : booking.getBottles()) {
                BottleEntity storedEntity = bottleDAO.getBottleByEventIdAndType(booking.getId_event(), bookedBottle.getTitle());

                int stored = Integer.parseInt(storedEntity.getAvailable());
                int ordered = Integer.parseInt(storedEntity.getBooked());

                if (bookedBottle.getAmount() > stored - ordered) {
                    bookedBottle.setAmount(stored - ordered);
                }
            }

            if (booking.getTable() != null) {
                OrderedTable orderedTable = orderedTableService.getTable(booking.getId_event(),
                        booking.getTable().getType(),
                        booking.getTable().getNumber());

                if (orderedTable != null) {
                    booking.setTable(null);
                }
            }

            if (booking.getTicket() != null) {

                // todo: tickets
                List<OrderedTicket> orderedTickets = orderedTicketService.getTickets(booking.getId_event(), booking.getTicket().getType());

                if (orderedTickets != null) {
                    int available = Integer.parseInt(ticketDAO.getTicketByEventId(booking.getId_event()).getAvailable());
                    int ordered = Integer.parseInt(orderedTickets.get(0).getType());

                    if (available - ordered < 0) {

                    }

                }
            }

        }
        return order;
    }
}
