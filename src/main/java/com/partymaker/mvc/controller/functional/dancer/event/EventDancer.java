package com.partymaker.mvc.controller.functional.dancer.event;

import com.partymaker.mvc.model.business.Book;
import com.partymaker.mvc.model.whole.Transaction;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.service.bottle.BottleService;
import com.partymaker.mvc.service.event.EventService;
import com.partymaker.mvc.service.photo.PhotoService;
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

    @PostMapping(value = {"/get_transactions"})
    public Callable<ResponseEntity<?>> getTransactions(@RequestBody Book[] bookings) {
        return () -> {
            if (bookings == null) {
                return new ResponseEntity<Object>("Cannot be null or empty.", HttpStatus.BAD_REQUEST);
            }

            try {
                List<Transaction> transactions = new ArrayList<>();

                for (Book bookItem : bookings) {
                    logger.info("searching for " + bookItem.getPartyName() + " party");

                    event mEvent = eventService.findByName(bookItem.getPartyName());

                    UserEntity partyCreator = null;

                    if (mEvent != null) {
                        for (UserEntity user : mEvent.getUsers()) {
                            if (user.getRole().getIdRole() == 1) {
                                partyCreator = user;
                            }
                        }
                    }

                    if (partyCreator == null) {
                        Transaction current = new Transaction();

                        current.setSellerEmail(partyCreator.getEmail());
                        current.setBillingEmail(partyCreator.getBillingEmail());
//                        current.setSubtotal(bookItem.getTotalSum(bookService.getTicket(bookItem)));
                        current.setSubtotal(0.0);
                        current.setCustomerEmail(userService.getCurrentUser().getEmail());

                        transactions.add(current);
                    }
                }

                return new ResponseEntity<List>(transactions, HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error during getting order (with commission) for book: ", e);
                return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @PostMapping(value = {"/book"})
    public Callable<ResponseEntity<?>> book(@RequestBody Book book) {
        return () -> {
            return new ResponseEntity<Object>(HttpStatus.OK);
        };
    }
}
