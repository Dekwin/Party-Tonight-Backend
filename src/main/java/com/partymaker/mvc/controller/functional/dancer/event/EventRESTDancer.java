package com.partymaker.mvc.controller.functional.dancer.event;

import com.partymaker.mvc.model.business.Book;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.service.bottle.BottleService;
import com.partymaker.mvc.service.event.EventService;
import com.partymaker.mvc.service.photo.PhotoService;
import com.partymaker.mvc.service.table.TableService;
import com.partymaker.mvc.service.ticket.TicketService;
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
public class EventRESTDancer {

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

    /**
     * retrieve events by zip_code
     */
    @GetMapping(value = {"/get"})
    public Callable<ResponseEntity<?>> search(@RequestHeader("zip_code") String zip_code) {
        return () -> {
            try {
                List<event> events = eventService.findAllByCode(zip_code);
                events.get(0);// assert on empty
                events.forEach(v -> {
                    v.setBottles(bottleService.findAllBottlesByEventID(v.getId_event()));
                    v.setTables(tableService.findAllTablesByEventId(v.getId_event()));
                    v.setTickets(ticketService.findAllTicketsByEventId(v.getId_event()));
                    v.setPhotos(photoService.findAllPhotosByEventId(v.getId_event()));
                });
                return new ResponseEntity<List>(events, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
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
