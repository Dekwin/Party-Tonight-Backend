package com.partymaker.mvc.controller.functional.maker.event;

import com.partymaker.mvc.model.business.DoorRevenue;
import com.partymaker.mvc.model.business.ImageMessage;
import com.partymaker.mvc.model.business.StatementTotal;
import com.partymaker.mvc.model.whole.BottleEntity;
import com.partymaker.mvc.model.whole.TableEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.service.bottle.BottleService;
import com.partymaker.mvc.service.event.EventService;
import com.partymaker.mvc.service.photo.PhotoService;
import com.partymaker.mvc.service.table.TableService;
import com.partymaker.mvc.service.ticket.TicketService;
import com.partymaker.mvc.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Created by anton on 05/11/16.
 */
@RestController
@RequestMapping(value = {"/maker/event"})
public class EventMaker {

    private static final Logger logger = LoggerFactory.getLogger(EventMaker.class);


    @Autowired
    UserService<UserEntity> userService;

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

    @Value("${image.local.path}")
    private String localPath;

    @Value("${image.url}")
    private String imageUrl;

    @PostMapping(value = {"/create"})
    public Callable<ResponseEntity<?>> createEvent(@RequestBody event eventEntity) {
        return () -> {
            try {
                logger.info("Creating event " + eventEntity);

                eventService.validation(eventEntity);

                eventService.save(eventEntity, getPrincipal());

                logger.info("Event has been added to user with email " + getPrincipal());
                return new ResponseEntity<Object>(HttpStatus.CREATED);
            } catch (Exception e) {
                logger.info("Error creating event due to ", e);
                return new ResponseEntity<Object>(e.getMessage(), HttpStatus.CONFLICT);
            }
        };
    }

    /**
     * get all events by user credentials
     */
    @GetMapping(value = {"/get"})
    public Callable<ResponseEntity<?>> getAllEvents() {
        return () -> {
            try {
                logger.info("Get events");

                UserEntity user = userService.findUserByEmail(getPrincipal());

                logger.info("Get events with user " + user);
                List<event> events = eventService.findAllByUserId(user.getId_user());

                return new ResponseEntity<List>(events, HttpStatus.OK);
            } catch (Exception e) {
                logger.info("Error getting all event due to ", e);
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
            }
        };
    }

    /**
     * calculate door revenue basing on ticket booked and prise fields
     */
    @GetMapping(value = {"/revenue"})
    public Callable<ResponseEntity<?>> getDoorRevenue(@RequestHeader("party_name") String partyName) {
        return () -> {
            logger.info("Get revenue by event = " + partyName + " and user " + getPrincipal());

            if (partyName == null || getPrincipal() == null || partyName.equals("")) {
                logger.info("Bad request with party name = " + partyName + " and user " + getPrincipal());
                return new ResponseEntity<Object>("Bad request ", HttpStatus.BAD_REQUEST);
            }
            try {
                UserEntity user = userService.findUserByEmail(getPrincipal());

                if (user == null) {
                    logger.info("Bad user = " + user);
                    return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
                }

                logger.info("Get event revenue by user " + user);
                DoorRevenue doorRevenue = eventService.getRevenue(partyName, user);

                if (doorRevenue == null) {
                    logger.info("Bad revenue = " + doorRevenue);
                    return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                return new ResponseEntity<Object>(doorRevenue, HttpStatus.OK);
            } catch (Exception e) {
                logger.info("Error calculating revenue due to ", e);
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @GetMapping(value = {"/bottles"})
    public Callable<ResponseEntity<?>> getEventBottle(@RequestHeader("party_name") String partyName) {
        return () -> {
            logger.info(" Get statement total with partymaker = " + partyName);
            if (partyName == null || getPrincipal() == null || partyName.equals("")) {
                logger.info("Bad request with party name = " + partyName + " and user " + getPrincipal());
                return new ResponseEntity<Object>("Bad request ", HttpStatus.BAD_REQUEST);
            }
            List<BottleEntity> foundBottles;
            try {
                UserEntity userEntity = userService.findUserByEmail(getPrincipal());

                foundBottles = new ArrayList<>();

                bottleService.findAllBottlesByEventAndUser(userEntity.getId_user(), partyName).forEach(foundBottles::add);

                return new ResponseEntity<Object>(foundBottles, HttpStatus.OK);
            } catch (Exception e) {
                logger.info("Error getting bottles due to ", e);
                return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NO_CONTENT);
            }
        };
    }

    @GetMapping(value = {"/tables"})
    public Callable<ResponseEntity<?>> getEventTables(@RequestHeader("party_name") String partyName) {
        return () -> {
            logger.info(" Get statement total with partymaker = " + partyName);
            if (partyName == null || getPrincipal() == null || partyName.equals("")) {
                logger.info("Bad request with party name = " + partyName + " and user " + getPrincipal());
                return new ResponseEntity<Object>("Bad request ", HttpStatus.BAD_REQUEST);
            }
            List<TableEntity> foundTabbles;
            try {
                UserEntity userEntity = userService.findUserByEmail(getPrincipal());

                foundTabbles = new ArrayList<>();

                tableService.findAllTablesByEventAndUser(userEntity.getId_user(), partyName).forEach(foundTabbles::add);
                return new ResponseEntity<Object>(foundTabbles, HttpStatus.OK);
            } catch (Exception e) {
                logger.info("Error getting tables due to ", e);
                return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NO_CONTENT);
            }
        };
    }

    @GetMapping(value = {"/total"})
    public Callable<ResponseEntity<?>> getStatementTotal(@RequestHeader("party_name") String partyName) {
        return () -> {
            logger.info(" Get statement total with partymaker = " + partyName);
            if (partyName == null || getPrincipal() == null || partyName.equals("")) {
                logger.info("Bad request with party name = " + partyName + " and user " + getPrincipal());
                return new ResponseEntity<Object>("Bad request ", HttpStatus.BAD_REQUEST);
            }
            try {
                UserEntity userEntity = userService.findUserByEmail(getPrincipal());
                logger.info("Get event total with user = " + userEntity);

                if (userEntity == null) {
                    logger.info("user = " + userEntity);
                    return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
                }
                StatementTotal statementTotal = eventService.getTotal(partyName, userEntity);
                logger.info("Return total = " + statementTotal);

                if (statementTotal == null) {
                    logger.info("Got nullable total " + statementTotal);
                    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<Object>(statementTotal, HttpStatus.OK);
            } catch (Exception e) {
                logger.info(" Statement total failure due to", e);
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @PostMapping(value = {"/image"})
    public Callable<ResponseEntity<?>> saveigame(@RequestParam("file") MultipartFile file) {
        return () -> {
            if (file == null || getPrincipal() == null) {
                logger.info("Bad request with file = " + file + " and user " + getPrincipal());
                return new ResponseEntity<Object>("Bad request ", HttpStatus.BAD_REQUEST);
            }
            try {
                logger.info("Saving image ");
                // save image and return its path.
                ImageMessage imageMessage = new ImageMessage();

                UUID name = UUID.randomUUID();

                imageMessage.setPath(saveImage(String.valueOf(Objects.hashCode(name)), file));

                return new ResponseEntity<ImageMessage>(imageMessage, HttpStatus.CREATED);
            } catch (Exception e) {
                logger.info("Failed to save image ", e);
                return new ResponseEntity<Object>(HttpStatus.CONFLICT);
            }
        };
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

    /**
     * save photo to local storage and return its the path
     */
    private String saveImage(String nameFile, MultipartFile image) {
        File imageFile = new File(localPath + nameFile);
        try {
            image.transferTo(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageUrl + imageFile.getName();
    }
}
