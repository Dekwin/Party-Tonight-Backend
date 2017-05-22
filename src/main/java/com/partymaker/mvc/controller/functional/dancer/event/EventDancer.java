package com.partymaker.mvc.controller.functional.dancer.event;

import com.partymaker.mvc.model.business.booking.BookedBottle;
import com.partymaker.mvc.model.business.booking.BookedTable;
import com.partymaker.mvc.model.business.booking.BookedTicket;
import com.partymaker.mvc.model.business.booking.Booking;
import com.partymaker.mvc.model.business.order.*;
import com.partymaker.mvc.model.whole.ReviewEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.service.book.BookService;
import com.partymaker.mvc.service.bottle.BottleService;
import com.partymaker.mvc.service.event.EventService;
import com.partymaker.mvc.service.order.TransactionService;
import com.partymaker.mvc.service.photo.PhotoService;
import com.partymaker.mvc.service.review.ReviewService;
import com.partymaker.mvc.service.table.TableService;
import com.partymaker.mvc.service.ticket.TicketService;
import com.partymaker.mvc.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by anton on 17/11/16.
 */
@RestController
@RequestMapping(value = {"/dancer/event"})
public class EventDancer {

    private static final Logger logger = Logger.getLogger(EventDancer.class);
    public static double OWNER_FEE = 0.05;
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;
    @Autowired
    BottleService bottleService;
    @Autowired
    TicketService ticketService;
    @Autowired
    TableService tableService;
    @Autowired
    PhotoService photoService;

    @Autowired
    BookService bookService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    ReviewService reviewService;

    @PostMapping(value = {"/review_post"})
    public Callable<ResponseEntity<?>> search(@RequestBody ReviewEntity reviewEntity) {
        return () -> {
            reviewService.save(reviewEntity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        };
    }

    /**
     * retrieve events by zip_code
     */
    @GetMapping(value = {"/get"})
    public Callable<ResponseEntity<?>> search(@RequestHeader("zip_code") String zip_code) {
        return () -> {
            logger.info("Getting event by zip code = " + zip_code);
            try {
                if (zip_code == null || zip_code.isEmpty())
                    throw new RuntimeException("Invalid zip code");

                List<event> events = eventService.findAllByCode(zip_code);

                return new ResponseEntity<List>(events, HttpStatus.OK);
            } catch (Exception e) {
                logger.info("Error getting events due to ", e);
                return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        };
    }

    @PostMapping(value = {"/confirm_invoices"})
    public Callable<ResponseEntity<?>> book(@RequestBody Transaction transaction) {
        return () -> {
            transactionService.save(transaction);

            List<Booking> bookings = new ArrayList<>(transaction.getOrders().size());
            for (OrderEntity order : transaction.getOrders()) {
                Booking booking = new Booking();

                booking.setId_event(order.getEventId());

                booking.setTable(new BookedTable(order.getTable()));
                booking.setTicket(new BookedTicket(order.getTicket()));

                for (OrderedBottle bottle : order.getBottles()) {
                    booking.getBottles().add(new BookedBottle(bottle));
                }

                bookings.add(booking);
            }

            bookService.book(bookings);

            return new ResponseEntity<>(HttpStatus.OK);
        };
    }

    @GetMapping(value = {"/{event_id}/tables"})
    public Callable<ResponseEntity<?>> getFreeTables(@PathVariable("event_id") int id_event) {
        return () -> new ResponseEntity<List>(bookService.getTables(id_event), HttpStatus.OK);
    }

    @PostMapping(value = {"/validate_booking"})
    public Callable<ResponseEntity<?>> validateOrder(@RequestBody Booking[] order) {
        return () -> {
            if (order == null) {
                return new ResponseEntity<Object>("Cannot be null or empty.", HttpStatus.BAD_REQUEST);
            }

            try {
                List<Booking> newOrder = bookService.validateBookings(order);

                return new ResponseEntity<>(newOrder, HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error during validation order: ", e);
                return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @PostMapping(value = {"/invoices"})
    public Callable<ResponseEntity<?>> getInvoices(@RequestBody Booking[] bookings) {
        return () -> {
            if (bookings == null) {
                return new ResponseEntity<Object>("Cannot be null or empty.", HttpStatus.BAD_REQUEST);
            }

            try {
                double fee = 0;

                Transaction response = new Transaction();
                response.setCustomerEmail(userService.getCurrentUser().getEmail());
                response.setServiceBillingEmail(userService.getServiceTaxAccount().getBillingEmail());
                response.setServiceEmail(userService.getServiceTaxAccount().getEmail());

                for (Booking booking : bookings) {
                    event mEvent = eventService.findById(booking.getId_event());

                    UserEntity partyCreator;

                    if (mEvent != null) {
                        int eventId = mEvent.getId_event();
                        int userId = userService.getUserIdByEventId(eventId);

                        partyCreator = (UserEntity) userService.findUserById(userId);

                        if (partyCreator != null) {
                            OrderEntity order = new OrderEntity();

                            order.setEventId(eventId);
                            order.setSellerBillingEmail(partyCreator.getBillingEmail());
                            order.setSellerEmail(partyCreator.getEmail());

                            order.setTable(new OrderedTable(booking.getTable()));
                            order.setTicket(new OrderedTicket(booking.getTicket()));

                            for (BookedBottle bottle : booking.getBottles()) {
                                order.addBottle(new OrderedBottle(bottle));
                            }

                            // now we divide sum onto fee and real payment

                            double subtotal = Booking.getSubtotal(booking);

                            order.setSubtotal((1 - OWNER_FEE) * subtotal);
                            fee += OWNER_FEE * subtotal;

                            response.addOrder(order);
                        }
                    }

                }

                response.setServiceTax(fee);

                return new ResponseEntity<Transaction>(response, HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error during getting order (with commission) for book: ", e);
                return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };


    }
}
