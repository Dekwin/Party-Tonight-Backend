package com.partymaker.mvc.controller.functional.maker.event;

import com.partymaker.mvc.model.business.DoorRevenue;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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
                System.out.println(eventEntity);

                List<BottleEntity> bottles = new ArrayList<>();
                eventEntity.getBottles().forEach(bottles::add);


                List<TableEntity> tables = new ArrayList<>();
                eventEntity.getTables().forEach(tables::add);

                TicketEntity ticketEntity = eventEntity.getTickets().get(0);

                List<MultipartFile> images = eventEntity.getImages();

                List<PhotoEntity> photoEntity = new ArrayList<>();

                eventEntity.setTables(null);
                eventEntity.setTickets(null);
                eventEntity.setBottles(null);
                eventEntity.setPhotos(null);

                /* generate hash */
                date = new Date();
                String hash = String.valueOf(Objects.hash(dateFormat.format(date)));
                eventEntity.setTime(hash);

                if (Objects.nonNull(images)) {
                    images.forEach(v -> {
                        File imageFile = new File("/home/images/" + hash + v.getName());
                        photoEntity.add(new PhotoEntity(hash + v.getName()));
                        try {
                            v.transferTo(imageFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

            /*writeToFileNIOWay2(hash, photoEntity.getPhoto());*/
                // save event
                eventService.save(eventEntity);
                userService.addEvent(getPrincipal(), eventEntity);

                // get event fro db to add tickets, ...
                event event = eventService.findByHash(eventEntity.getTime());
                System.out.println(event);

                // add ...
                bottles.forEach(v -> v.setEvent(event));
                tables.forEach(v -> v.setEventEntity(event));
                ticketEntity.setEventEntity(event);
                // add image
//            photoEntity.setPhoto(saveImage(hash, photoEntity.getPhoto()));
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

    @GetMapping(value = {"/get"})
    public Callable<ResponseEntity<?>> getAllEvents() {
        return () -> {
            try {
                UserEntity entity = userService.findUserByEmail(getPrincipal());
                List<event> events = eventService.findAllByUserId(entity.getId_user());
                events.forEach(v -> {
                    List<BottleEntity> bottles = bottleService.findAllBottlesByEventAndUser(entity.getId_user(), v.getParty_name());
                    v.setBottles(bottles);
                    List<TableEntity> tables = tableService.findAllTablesByEventAndUser(entity.getId_user(), v.getParty_name());
                    v.setTables(tables);
                    List<TicketEntity> tickets = ticketService.findAllTicketsByEventAndUser(entity.getId_user(),v.getParty_name());
                    v.setTickets(tickets);
                    v.setPhotos(null);
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
                return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Object>(statementTotal, HttpStatus.OK);
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
     * save photo and return its the path
     */
    public String saveImage(String nameFile, byte[] image) throws IOException {

        /*System.out.println(image);
        String[] byteValues = image.substring(1, image.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i = 0, len = bytes.length; i < len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }
*/
        InputStream in = new ByteArrayInputStream(image);
        BufferedImage bImageFromConvert = ImageIO.read(in);

        File imageFile = new File("/home/anton/deploy/" + nameFile + ".jpg");
        ImageIO.write(bImageFromConvert, "jpg", imageFile);
        return imageFile.getAbsolutePath();
    }

}
