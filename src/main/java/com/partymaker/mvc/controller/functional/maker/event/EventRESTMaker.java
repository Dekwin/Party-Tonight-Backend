package com.partymaker.mvc.controller.functional.maker.event;

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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
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

    @GetMapping(value = {"/bottles"})
    public Callable<ResponseEntity<?>> getEventBottle() {
        return () -> {

            return new ResponseEntity<Object>(, HttpStatus.OK);
        };
    }

    @GetMapping(value = {"/revenue"})
    public Callable<ResponseEntity<?>> getDoorRevenue() {
        return () -> {

            return new ResponseEntity<Object>(, HttpStatus.OK);
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
