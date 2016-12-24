package com.partymaker.mvc.controller.functional.maker.event;

import com.partymaker.mvc.model.business.DoorRevenue;
import com.partymaker.mvc.model.business.ImageMessage;
import com.partymaker.mvc.model.business.StatementTotal;
import com.partymaker.mvc.model.whole.*;
import com.partymaker.mvc.service.bottle.BottleService;
import com.partymaker.mvc.service.event.EventService;
import com.partymaker.mvc.service.photo.PhotoService;
import com.partymaker.mvc.service.table.TableService;
import com.partymaker.mvc.service.ticket.TicketService;
import com.partymaker.mvc.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
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

    //    private String imageStorePath = "/static/images";
    private String imageStoreFULLPAth = "http://45.55.226.134:8080/static/images/";

    @Autowired
    @Qualifier("userService")
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

    @PostMapping(value = {"/create"})
    public Callable<ResponseEntity<?>> createEvent(@RequestBody event eventEntity) {
        return () -> {
            try {
                logger.info("Creating event " + eventEntity);

                List<BottleEntity> bottles = eventEntity.getBottles();
                List<TableEntity> tables = eventEntity.getTables();
                TicketEntity ticketEntity = eventEntity.getTickets().get(0);
                List<PhotoEntity> photoEntity = eventEntity.getPhotos();

                eventEntity.setTables(null);
                eventEntity.setTickets(null);
                eventEntity.setBottles(null);
                eventEntity.setPhotos(null);

                /* generate hash */
                date = new Date();
                String hash = String.valueOf(Objects.hash(dateFormat.format(date)));
                eventEntity.setTime(hash);

                // save event
                eventService.save(eventEntity);
                userService.addEvent(getPrincipal(), eventEntity);

                // get event by unique value which stored in time dbFiled from db to add tickets, ...
                event event = eventService.findByHash(eventEntity.getTime());
                logger.info("Fetched event = " + event);

                // add ...
                bottles.forEach(v -> v.setEvent(event));
                tables.forEach(v -> v.setEventEntity(event));
                ticketEntity.setEventEntity(event);
                // add image
                photoEntity.forEach(v -> v.setEventEntity(event));

                bottles.forEach(v -> bottleService.save(v));
                ticketService.save(ticketEntity);
                tables.forEach(v -> tableService.save(v));
                photoEntity.forEach(v -> photoService.save(v));

                logger.info("Event has been added to user with email " + getPrincipal());
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<Object>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<Object>(HttpStatus.CREATED);
        };
    }

    /**
     * get all events by party name
     */
    @GetMapping(value = {"/get"})
    public Callable<ResponseEntity<?>> getAllEvents() {
        return () -> {
            try {
                logger.info("Get events");
                UserEntity user = userService.findUserByEmail(getPrincipal());
                logger.info("Get events with user " + user);
                List<event> events = eventService.findAllByUserId(user.getId_user());
                events.forEach(v -> {
                    List<BottleEntity> bottles = bottleService.findAllBottlesByEventID(v.getId_event());
                    v.setBottles(bottles);
                    List<TableEntity> tables = tableService.findAllTablesByEventId(v.getId_event());
                    v.setTables(tables);
                    List<TicketEntity> tickets = ticketService.findAllTicketsByEventId(v.getId_event());
                    v.setTickets(tickets);
                    List<PhotoEntity> photoEntities = photoService.findAllPhotosByEventId(v.getId_event());
                    v.setPhotos(photoEntities);
                });
                return new ResponseEntity<List>(events, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
            }
        };
    }

    /**
     * calculate door revenue basing on ticket booked and prise fields
     */
    @GetMapping(value = {"/revenue"})
    public Callable<ResponseEntity<?>> getDoorRevenue(@RequestHeader("partyName") String partyName) {
        return () -> {
            DoorRevenue doorRevenue;
            try {
                UserEntity userEntity = userService.findUserByEmail(getPrincipal());
                doorRevenue = new DoorRevenue();
                ticketService.findAllTicketsByEventAndUser(userEntity.getId_user(), partyName).forEach(v -> {
                    doorRevenue.setRevenue(String.valueOf(Double.parseDouble(v.getBooked()) * Double.parseDouble(v.getPrice())));
                });
            } catch (Exception e) {
                return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Object>(doorRevenue, HttpStatus.OK);
        };
    }

    @GetMapping(value = {"/bottles"})
    public Callable<ResponseEntity<?>> getEventBottle(@RequestHeader("partyName") String partyName) {
        return () -> {
            List<BottleEntity> foundBottles;
            try {
                UserEntity userEntity = userService.findUserByEmail(getPrincipal());
                foundBottles = new ArrayList<>();
                bottleService.findAllBottlesByEventAndUser(userEntity.getId_user(), partyName).forEach(foundBottles::add);
            } catch (Exception e) {
                return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Object>(foundBottles, HttpStatus.OK);
        };
    }

    @GetMapping(value = {"/tables"})
    public Callable<ResponseEntity<?>> getEventTables(@RequestHeader("partyName") String partyName) {
        return () -> {
            List<TableEntity> foundTabbles;
            try {
                UserEntity userEntity = userService.findUserByEmail(getPrincipal());
                foundTabbles = new ArrayList<>();
                tableService.findAllTablesByEventAndUser(userEntity.getId_user(), partyName).forEach(foundTabbles::add);
            } catch (Exception e) {
                return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Object>(foundTabbles, HttpStatus.OK);
        };
    }

    @GetMapping(value = {"/total"})
    public Callable<ResponseEntity<?>> getStatementTotal(@RequestHeader("partyName") String partyName) {
        return () -> {
            logger.info(" Get statement total with partymaker = " + partyName);
            StatementTotal statementTotal;
            try {
                UserEntity userEntity = userService.findUserByEmail(getPrincipal());

                statementTotal = new StatementTotal();

                ticketService.findAllTicketsByEventAndUser(userEntity.getId_user(), partyName).forEach(v -> {
                    /**
                     * calculate the unbooked tickets
                     * */
                    statementTotal.setRefunds(String.valueOf(
                            (Double.parseDouble(statementTotal.getRefunds()))
                                    - (
                                    Double.parseDouble(v.getAvailable()) - Double.parseDouble(v.getBooked()) * Double.parseDouble(v.getPrice())
                            )
                    ));
                    /**
                     * calculate the booked tickets
                     * */
                    statementTotal.setTicketsSales(String.valueOf(Double.parseDouble(v.getBooked()) * Double.parseDouble(v.getPrice())));
                });

                tableService.findAllTablesByEventAndUser(userEntity.getId_user(), partyName).forEach(v -> {
                    /**
                     * calculate the unbooked tables
                     * */
                    statementTotal.setRefunds(String.valueOf(
                            (Double.parseDouble(statementTotal.getRefunds()))
                                    - (
                                    Double.parseDouble(v.getAvailable()) - Double.parseDouble(v.getBooked()) * Double.parseDouble(v.getPrice())
                            )
                    ));
                    /**
                     * calculate the booked tables
                     * */
                    statementTotal.setTableSales(String.valueOf(Double.parseDouble(v.getBooked()) * Double.parseDouble(v.getPrice())));
                });
                bottleService.findAllBottlesByEventAndUser(userEntity.getId_user(), partyName).forEach(v -> {
                    /**
                     * calculate the unbooked bottles
                     * */
                    statementTotal.setRefunds(String.valueOf(
                            (Double.parseDouble(statementTotal.getRefunds()))
                                    - (
                                    Double.parseDouble(v.getAvailable()) - Double.parseDouble(v.getBooked()) * Double.parseDouble(v.getPrice())
                            )
                    ));
                    /**
                     * calculate the booked tables
                     * */
                    statementTotal.setBottleSales(String.valueOf(Double.parseDouble(v.getBooked()) * Double.parseDouble(v.getPrice())));
                });

                /**
                 * add withdrawn summary of all above components
                 * */
                statementTotal.setWithdrawn(String.valueOf(
                        Double.parseDouble(statementTotal.getBottleSales())
                                + Double.parseDouble(statementTotal.getTableSales())
                                + Double.parseDouble(statementTotal.getTicketsSales())
                                - Double.parseDouble(statementTotal.getRefunds())
                ));
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(" Statement total failure due to " + e);
                return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Object>(statementTotal, HttpStatus.OK);
        };
    }

    @PostMapping(value = {"/image"})
    public Callable<ResponseEntity<?>> saveigame(@RequestParam("file") MultipartFile file) {
        return () -> {
            try {
                logger.info("Saving image ");
                // save image and return its path.
                ImageMessage imageMessage = new ImageMessage();
                UUID name = UUID.randomUUID();
                imageMessage.setPath(saveImage(String.valueOf(Objects.hashCode(name)), file));
                return new ResponseEntity<ImageMessage>(imageMessage, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("Failed to save image " + e);
                if (Objects.isNull(file)) {
                    return new ResponseEntity<Object>("Image cannot be null ", HttpStatus.CONFLICT);
                }
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
        String localPath = "/opt/apache-tomcat-8.5.6/webapps/static/images/";
        File imageFile = new File(localPath + nameFile);
        try {
            image.transferTo(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageStoreFULLPAth + imageFile.getName();
    }
}
