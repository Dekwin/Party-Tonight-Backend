package com.partymaker.mvc.controller.functional.dancer.event;

import com.partymaker.mvc.model.business.Book;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.service.book.BookService;
import com.partymaker.mvc.service.bottle.BottleService;
import com.partymaker.mvc.service.event.EventService;
import com.partymaker.mvc.service.photo.PhotoService;
import com.partymaker.mvc.service.table.TableService;
import com.partymaker.mvc.service.ticket.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by anton on 17/11/16.
 */
@RestController
@RequestMapping(value = {"/dancer/event"})
public class EventDancer {

    private static final Logger logger = LoggerFactory.getLogger(EventDancer.class);


    @Autowired
    EventService eventService;

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

    @PostMapping(value = {"/book"})
    public Callable<ResponseEntity<?>> book(@RequestBody Book book) {
        return () -> {
            logger.info("Book  = " + book);
            if (book == null || book.getTables().isEmpty() || book.getBottles().isEmpty()) {
                return new ResponseEntity<Object>("Cannot be null or empty.", HttpStatus.BAD_REQUEST);
            }
            try {
                bookService.book(book);
                return new ResponseEntity<Object>(HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error during create book due to ", e);
                return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        };
    }
}
