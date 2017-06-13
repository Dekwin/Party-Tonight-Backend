package com.partymaker.mvc.service.book;

import com.partymaker.mvc.dao.event.EventDAO;
import com.partymaker.mvc.dao.event.bottle.BottleDAO;
import com.partymaker.mvc.dao.event.table.TableDAO;
import com.partymaker.mvc.dao.event.ticket.TicketDAO;
import com.partymaker.mvc.model.business.booking.BookedBottle;
import com.partymaker.mvc.model.business.booking.BookedTable;
import com.partymaker.mvc.model.business.booking.BookedTicket;
import com.partymaker.mvc.model.business.booking.Booking;
import com.partymaker.mvc.model.business.order.OrderedTable;
import com.partymaker.mvc.model.business.order.OrderedTicket;
import com.partymaker.mvc.model.whole.BottleEntity;
import com.partymaker.mvc.model.whole.TableEntity;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.service.order.OrderedTableService;
import com.partymaker.mvc.service.order.OrderedTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class BookService {

    @Autowired
    private EventDAO<event> eventDAO;

    @Autowired
    private BottleDAO bottleDAO;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private TableDAO tableDAO;

    @Autowired
    private OrderedTableService orderedTableService;

    @Autowired
    private OrderedTicketService orderedTicketService;

    public void book(List<Booking> bookings) {
        for (Booking bookingItem : bookings) {
            event event = eventDAO.getByID(bookingItem.getId_event());

            // bottles
            // we store it old repo
            for (BookedBottle bookedBottle : bookingItem.getBottles()) {
                for (BottleEntity storedBottle : bottleDAO.getBottleByEventId(
                        bookingItem.getId_event())) {

                    if (bookedBottle.getTitle().equals(storedBottle.getType())) {
                        int bookedAlready = Integer.parseInt(storedBottle.getBooked());

                        bookedAlready += bookedBottle.getAmount();

                        storedBottle.setBooked(String.valueOf(bookedAlready));
                    }
                }
            }

            // table
            // check whether table is exist in current booking
            if (bookingItem.getTable() != null) {
                BookedTable bookedTable = bookingItem.getTable();

                // we store in old repo
                // storing in the new one -> in the "confirm invoices" method
                // we can order just one table at once
                TableEntity t = tableDAO.getTableByEventIdAndType(
                        bookingItem.getId_event(), bookedTable.getType());
                t.setBooked(String.valueOf(Integer.parseInt(t.getBooked()) + 1));

            }

            if (bookingItem.getTicket() != null) {
                // we don't have type of ticket in the old repo
                // so just getting first instance of it
                int ticketsAlreadyBooked = Integer.valueOf(event.getTickets().get(0).getBooked());

                // we can buy one ticket at once. yes, as it described in files.
                ticketDAO.getTicketByEventId(bookingItem.getId_event())
                        .setBooked(String.valueOf(ticketsAlreadyBooked + 1));
            }
        }
    }

    public List<Booking> validateBookings(Booking[] bookings) {
        List<Booking> order = Arrays.asList(bookings);

        for (Booking booking : order) {
            for (BookedBottle bookedBottle : booking.getBottles()) {

                // we may use new repo, but it must be slower
                BottleEntity bottleEntity = bottleDAO.getBottleByEventIdAndType(
                        booking.getId_event(), bookedBottle.getTitle());

                int bottlesBooked = Integer.parseInt(bottleEntity.getBooked());
                int bottlesTotal = Integer.parseInt(bottleEntity.getAvailable());

                if (bottlesTotal - bottlesBooked <= bookedBottle.getAmount()) {
                    bookedBottle.setAmount(bottlesTotal - bottlesBooked);
                }
            }

            if (booking.getTable() != null) {
                BookedTable bookedTable = booking.getTable();

                // getting one from ordered table's repo
                OrderedTable orderedTable = orderedTableService.getTable(
                        booking.getId_event(), bookedTable.getNumber());

                // if we can find one ordered table with the same number and type
                if (orderedTable != null) {
                    booking.setTable(null);
                }
            }

            if (booking.getTicket() != null) {
                BookedTicket bookedTicket = booking.getTicket();

                // we can't find any unique ticket
                // so we find everyone of one type
                List<OrderedTicket> orderedTickets = orderedTicketService.getTickets(
                        booking.getId_event(), bookedTicket.getType());

                // ticket entity for getting total amount of ticket
                // in old repo we have just only type
                int ticketsAvailable = Integer.parseInt(ticketDAO.getTicketByEventId(booking.getId_event()).getAvailable());


                // if we don't have any ordered tickets we cool
                if (orderedTickets != null) {

                    // if we already have sold all the tickets
                    // just make ticket null
                    if (orderedTickets.size() >= ticketsAvailable) {
                        booking.setTicket(null);
                    }
                }
            }
        }
        return order;
    }

    public List<BookedTable> getTables(int id_event) {
        return orderedTableService.getFreeTables(id_event);
    }
}
