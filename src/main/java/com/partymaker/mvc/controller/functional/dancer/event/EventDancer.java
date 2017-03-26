package com.partymaker.mvc.controller.functional.dancer.event;

import com.partymaker.mvc.model.business.booking.Booking;
import com.partymaker.mvc.model.business.order.Transaction;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.service.book.BookService;
import com.partymaker.mvc.service.bottle.BottleService;
import com.partymaker.mvc.service.event.EventService;
import com.partymaker.mvc.service.order.TransactionService;
import com.partymaker.mvc.service.photo.PhotoService;
import com.partymaker.mvc.service.table.TableService;
import com.partymaker.mvc.service.ticket.TicketService;
import com.partymaker.mvc.service.user.UserService;
import com.partymaker.mvc.util.Utils;
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
    private static final double OWNER_FEE = 0.05;
    private static final String OWNER_EMAIL = "owner@owner.owner";
    private static final String OWNER_BILLING_EMAIL = "owner_billing@owner.owner";

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
    public Callable<ResponseEntity<?>> book(@RequestParam("bookings[]") String
                                                    bookingsJson, @RequestParam("transactions[]") String transactionsJson) {
        return () -> {
            List<Booking> bookings = Utils.parseEncodedCollection(bookingsJson, Booking.class);
            List<Transaction> transactions = Utils.parseEncodedCollection(transactionsJson, Transaction.class);

            transactionService.save(transactions, bookings);
            bookService.book(bookings);

            return new ResponseEntity<>(HttpStatus.OK);
        };
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

    @PostMapping(value = {"/get_invoices"})
    public Callable<ResponseEntity<?>> getInvoices(@RequestBody Booking[] bookings) {
        return () -> {
            if (bookings == null) {
                return new ResponseEntity<Object>("Cannot be null or empty.", HttpStatus.BAD_REQUEST);
            }

            try {
                List<Transaction> response = new ArrayList<>(bookings.length + 1);

                double fee = 0;

                for (Booking booking : bookings) {
                    event mEvent = eventService.findById(booking.getId_event());

                    UserEntity partyCreator;

                    if (mEvent != null) {
                        int eventId = mEvent.getId_event();
                        int userId = userService.getUserIdByEventId(eventId);

                        partyCreator = (UserEntity) userService.findUserBuId(userId);

                        if (partyCreator != null) {
                            Transaction current = new Transaction();

                            current.setId_event(booking.getId_event());
                            current.setSellerEmail(partyCreator.getEmail());
                            current.setBillingEmail(partyCreator.getBillingEmail());
                            current.setCustomerEmail(userService.getCurrentUser().getEmail());

                            // now we divide sum onto fee and real payment

                            double subtotal = Booking.getSubtotal(booking);

                            current.setSubtotal((1 - OWNER_FEE) * subtotal);
                            fee += OWNER_FEE * subtotal;

                            response.add(current);

                        }
                    }

                    Transaction transactionForOwner = new Transaction();
                    transactionForOwner.setSellerEmail(OWNER_EMAIL);
                    transactionForOwner.setBillingEmail(OWNER_BILLING_EMAIL);
                    transactionForOwner.setCustomerEmail(userService.getCurrentUser().getEmail());
                    transactionForOwner.setSubtotal(fee);

                    response.add(transactionForOwner);
                }
                return new ResponseEntity<List>(response, HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error during getting order (with commission) for book: ", e);
                return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };


    }
}
