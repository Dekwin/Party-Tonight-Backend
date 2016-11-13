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
import org.hibernate.annotations.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.persistence.criteria.CriteriaBuilder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
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
            logger.info("Creating event " + eventEntity);
            System.out.println(eventEntity);

            List<BottleEntity> bottles = new ArrayList<>();
            eventEntity.getBottles().forEach(bottles::add);


            List<TableEntity> tables = new ArrayList<>();
            eventEntity.getTables().forEach(v -> tables.add(v));

            TicketEntity ticketEntity = eventEntity.getTickets().get(0);

            PhotoEntity photoEntity = eventEntity.getPhotos().get(0);

            eventEntity.setTables(null);
            eventEntity.setTickets(null);
            eventEntity.setBottles(null);
            eventEntity.setPhotos(null);

            /* generate hash */
            date = new Date();
            String hash = String.valueOf(Objects.hash(dateFormat.format(date)));
            eventEntity.setTime(hash);

            /*writeToFileNIOWay2(hash, photoEntity.getPhoto());*/
            // save event
            eventService.save(eventEntity);
            userService.addEvent(getPrincipal(), eventEntity);

            // get event fro db to add tickets, ...
            event event = eventService.findByHash(eventEntity.getTime());
            System.out.println(event);

            // add tickets, ...
            bottles.forEach(v -> v.setEvent(event));
            tables.forEach(v -> v.setEventEntity(event));
            ticketEntity.setEventEntity(event);
            photoEntity.setEventEntity(event);

            System.out.println(event);

            bottles.forEach(v -> bottleService.save(v));
            ticketService.save(ticketEntity);
            tables.forEach(v -> tableService.save(v));
            photoService.save(photoEntity);

            logger.info("Event has been added to user with email " + getPrincipal());
            return new ResponseEntity<Object>(HttpStatus.CREATED);
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
                    doorRevenue.setRevenue(String.valueOf(Integer.parseInt(v.getBooked()) * Integer.parseInt(v.getPrice())));
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
                            (Integer.parseInt(statementTotal.getRefunds()))
                                    - (
                                    Integer.parseInt(v.getAvailable()) - Integer.parseInt(v.getBooked()) * Integer.parseInt(v.getPrice())
                            )
                    ));
                    /**
                     * calculate the booked tickets
                     * */
                    statementTotal.setTicketsSales(String.valueOf(Integer.parseInt(v.getBooked()) * Integer.parseInt(v.getPrice())));
                });

                tableService.findAllTablesByEventAndUser(userEntity.getId_user(), partyName).forEach(v -> {
                    /**
                     * calculate the unbooked tables
                     * */
                    statementTotal.setRefunds(String.valueOf(
                            (Integer.parseInt(statementTotal.getRefunds()))
                                    - (
                                    Integer.parseInt(v.getAvailable()) - Integer.parseInt(v.getBooked()) * Integer.parseInt(v.getPrice())
                            )
                    ));
                    /**
                     * calculate the booked tables
                     * */
                    statementTotal.setTableSales(String.valueOf(Integer.parseInt(v.getBooked()) * Integer.parseInt(v.getPrice())));
                });
                bottleService.findAllBottlesByEventAndUser(userEntity.getId_user(), partyName).forEach(v -> {
                    /**
                     * calculate the unbooked bottles
                     * */
                    statementTotal.setRefunds(String.valueOf(
                            (Integer.parseInt(statementTotal.getRefunds()))
                                    - (
                                    Integer.parseInt(v.getAvailable()) - Integer.parseInt(v.getBooked()) * Integer.parseInt(v.getPrice())
                            )
                    ));
                    /**
                     * calculate the booked tables
                     * */
                    statementTotal.setBottleSales(String.valueOf(Integer.parseInt(v.getBooked()) * Integer.parseInt(v.getPrice())));
                });

                /**
                 * add withdrawn summary of all above components
                 * */
                statementTotal.setWithdrawn(String.valueOf(
                        Integer.parseInt(statementTotal.getBottleSales())
                                + Integer.parseInt(statementTotal.getTableSales())
                                + Integer.parseInt(statementTotal.getTicketsSales())
                                - Integer.parseInt(statementTotal.getRefunds())
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


    public void writeToFileNIOWay2(String nameFile, String messageToWrite) throws IOException {
        File file = new File("/home/anton/" + nameFile + ".jpeg");

        String[] byteValues = messageToWrite.substring(0, messageToWrite.length() - 1).split("");
        byte[] bytes = new byte[byteValues.length];

        for (int i = 0, len = bytes.length; i < len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpeg");

        //ImageIO is a class containing static methods for locating ImageReaders
        //and ImageWriters, and performing simple encoding and decoding.

        ImageReader reader = (ImageReader) readers.next();
        Object source = bis;
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();

        Image image = reader.read(0, param);
        //got an image file

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        //bufferedImage is the RenderedImage to be written

        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, null, null);

        File imageFile = new File("/home/anton/deploy/3434.jpeg");
        ImageIO.write(bufferedImage, "jpeg", imageFile);

        System.out.println(imageFile.getPath());
    }

}
