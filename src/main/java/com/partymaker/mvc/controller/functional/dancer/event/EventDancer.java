package com.partymaker.mvc.controller.functional.dancer.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.partymaker.mvc.model.business.Book;
import com.partymaker.mvc.model.whole.Transaction;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.service.book.BookService;
import com.partymaker.mvc.service.book.TransactionService;
import com.partymaker.mvc.service.bottle.BottleService;
import com.partymaker.mvc.service.event.EventService;
import com.partymaker.mvc.service.photo.PhotoService;
import com.partymaker.mvc.service.table.TableService;
import com.partymaker.mvc.service.ticket.TicketService;
import com.partymaker.mvc.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
                        int eventId = mEvent.getId_event();
                        int userId = userService.getUserIdByEventId(eventId);

                        partyCreator = (UserEntity) userService.findUserBuId(userId);
                    }

                    if (partyCreator != null) {
                        Transaction current = new Transaction();

                        current.setId_event(eventService.findByName(bookItem.getPartyName()).getId_event());
                        current.setSellerEmail(partyCreator.getEmail());
                        current.setBillingEmail(partyCreator.getBillingEmail());
                        current.setSubtotal(bookItem.getTotalSum(bookService.getTicket(bookItem)));
                        current.setCustomerEmail(userService.getCurrentUser().getEmail());

                        logger.info(bookItem.toString());
                        logger.info(current.toString());

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

    @PostMapping(value = {"/confirm_payments"})
    public Callable<ResponseEntity<?>> book(@RequestParam("bookings[]") String bookingsJson, @RequestParam("transactions[]") String transactionsJson) {
        return () -> {
            ObjectMapper mapper = new ObjectMapper();

            String bookingsString = URLDecoder.decode(bookingsJson,"UTF-8");
            String transactionString = URLDecoder.decode(transactionsJson, "UTF-8");

            List<Book> bookings = mapper.readValue(bookingsString, mapper.getTypeFactory().constructCollectionType(List.class, Book.class));
            List<Transaction> transactions = mapper.readValue(transactionString, mapper.getTypeFactory().constructCollectionType(List.class, Transaction.class));

            // fixme need to store or handle in any way "booking" param

            for (Book b : bookings) {
                bookService.book(b);

                logger.info("booked " + b.toString());
            }

            for (Transaction t : transactions) {
                t.setCompleted(1);
                transactionService.save(t);

                logger.info("transaction " + t.toString());
            }

            return new ResponseEntity<Object>(HttpStatus.OK);
        };
    }
}
