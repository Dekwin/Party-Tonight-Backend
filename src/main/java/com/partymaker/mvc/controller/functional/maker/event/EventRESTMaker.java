package com.partymaker.mvc.controller.functional.maker.event;

import com.partymaker.mvc.model.whole.EventEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.event.EventService;
import com.partymaker.mvc.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * Created by anton on 05/11/16.
 */
@RestController
@RequestMapping(value = {"/maker/event"})
public class EventRESTMaker {

    private static final Logger logger = Logger.getLogger(EventRESTMaker.class);

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static Date date;

    @Autowired
    @Qualifier("userService")
    UserService<UserEntity> userService;

    @Autowired
    EventService eventService;

    @PostMapping(value = {"/create"})
    public Callable<ResponseEntity<?>> createEvent(@RequestBody EventEntity eventEntity) {
        return () -> {
            logger.info("Creating event " + eventEntity);
            System.out.println(getPrincipal());
            /*UserEntity user = userService.findUserByEmail(getPrincipal());*/

            eventService.save(eventEntity);
            logger.info("Event has been created");
            userService.addEvent(getPrincipal(), eventEntity);
            logger.info("Event has been added to user with email " + getPrincipal());
            return new ResponseEntity<Object>(HttpStatus.CREATED);
        };
    }

    @GetMapping(value = {"/event"})
    public Callable<ResponseEntity<?>> getevent() {
        return () -> new ResponseEntity<Object>(new EventEntity(), HttpStatus.OK);
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
