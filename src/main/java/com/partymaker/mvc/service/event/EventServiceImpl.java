package com.partymaker.mvc.service.event;


import com.partymaker.mvc.dao.event.EventDAO;
import com.partymaker.mvc.model.business.DoorRevenue;
import com.partymaker.mvc.model.business.StatementTotal;
import com.partymaker.mvc.model.whole.*;
import com.partymaker.mvc.service.bottle.BottleService;
import com.partymaker.mvc.service.photo.PhotoService;
import com.partymaker.mvc.service.table.TableService;
import com.partymaker.mvc.service.ticket.TicketService;
import com.partymaker.mvc.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by anton on 04/11/16.
 */
@Transactional
@Service("eventService")
public class EventServiceImpl implements EventService {
    private static final Logger logger = Logger.getLogger(EventServiceImpl.class);

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private static Date date;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    EventService eventService;

    @Autowired
    PhotoService photoService;

    @Autowired
    UserService userService;

    @Autowired
    TicketService ticketService;

    @Autowired
    BottleService bottleService;

    @Autowired
    TableService tableService;

    @Override
    public event findById(int id) {
        return (event) eventDAO.getByID(id);
    }

    @Override
    public event findByHash(String timeHash) {
        return (event) eventDAO.getByCode(timeHash);
    }

    @Override
    public List<event> findAll() {
        return eventDAO.getAll();
    }

    @Override
    public List<event> findAllByUserId(int id_user) {
        List<event> events = eventDAO.getAllByUserId(id_user);
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
        return events;
    }


    @Override
    public List<event> findAllByCode(String code) {
        List<event> events = eventDAO.getAllCode(code);

        events.forEach(v -> {
            v.setBottles(bottleService.findAllBottlesByEventID(v.getId_event()));
            v.setTables(tableService.findAllTablesByEventId(v.getId_event()));
            v.setTickets(ticketService.findAllTicketsByEventId(v.getId_event()));
            v.setPhotos(photoService.findAllPhotosByEventId(v.getId_event()));
        });
        return events;
    }

    @Override
    public void delete(event eventEntity) {
        eventDAO.delete(eventEntity);
    }

    @Override
    public void save(event eventEntity, String user_email) {
        // the hardest hardcode
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
        eventDAO.save(eventEntity);

        userService.addEvent(user_email, eventEntity);

        // get event by unique value which stored in time dbField from db to add tickets, ...
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
    }

    @Override
    public boolean isExist(String timeHash) {
        return Objects.nonNull(eventDAO.getByCode(timeHash));
    }

    public void validation(event event) {
        if (event == null
                ||
                (event.getBottles() == null || event.getBottles().isEmpty())
                ||
                (event.getTables() == null || event.getTables().isEmpty())
                ||
                (event.getTickets() == null || event.getTickets().isEmpty())) {
            logger.info("Bad event");
            throw new RuntimeException("Bad data to create event");
        } else if ((event.getClub_name() == null || event.getClub_name().isEmpty())
                ||
                event.getClub_capacity() == null
                ||
                (event.getZip_code() == null || event.getZip_code().isEmpty())
                ||
                (event.getLocation() == null || event.getLocation().isEmpty())) {
            logger.info("Bad event");
            throw new RuntimeException("Bad data to create event");
        }
    }

    @Override
    public StatementTotal getTotal(String partyName, UserEntity user) {

        StatementTotal statementTotal = new StatementTotal();
        statementTotal.setBottleSales("0");
        statementTotal.setTicketsSales("0");
        statementTotal.setTableSales("0");
        statementTotal.setRefunds("0");

        ticketService.findAllTicketsByEventAndUser(user.getId_user(), partyName).forEach(v -> {
            if (Objects.isNull(v.getBooked()) || v.getBooked().equals("")) {
                logger.info("Bad object = " + v);
                v.setBooked("0");
            }
            /**
             * calculate the unbooked tickets
             * */
            statementTotal.setRefunds(String.valueOf(
                    Double.parseDouble(statementTotal.getRefunds())
                            -
                            ((Double.parseDouble(v.getAvailable())
                                    - Double.parseDouble(v.getBooked()))
                                    * Double.parseDouble(v.getPrice())
                            )
            ));
            /**
             * calculate the booked tickets
             * */
            statementTotal.setTicketsSales(String.valueOf(
                    Double.parseDouble(statementTotal.getTicketsSales())
                            +
                            (Double.parseDouble(v.getBooked())
                                    * Double.parseDouble(v.getPrice())
                            )
            ));
        });

        tableService.findAllTablesByEventAndUser(user.getId_user(), partyName).forEach(v -> {
            if (Objects.isNull(v.getBooked()) || v.getBooked().equals("")) {
                logger.info("Bad object = " + v);
                v.setBooked("0");
            }
            /**
             * calculate the unbooked tables
             * */
            statementTotal.setRefunds(String.valueOf(
                    Double.parseDouble(statementTotal.getRefunds())
                            -
                            ((Double.parseDouble(v.getAvailable())
                                    - Double.parseDouble(v.getBooked()))
                                    * Double.parseDouble(v.getPrice())
                            )
            ));
            /**
             * calculate the booked tables
             * */
            statementTotal.setTableSales(String.valueOf(
                    Double.parseDouble(statementTotal.getTableSales())
                            + Double.parseDouble(v.getBooked())
                            * Double.parseDouble(v.getPrice())));
        });

        bottleService.findAllBottlesByEventAndUser(user.getId_user(), partyName).forEach(v -> {
            if (Objects.isNull(v.getBooked()) || v.getBooked().equals("")) {
                logger.info("Bad object = " + v);
                v.setBooked("0");
            }
            /**
             * calculate the unbooked bottles
             * */
            statementTotal.setRefunds(String.valueOf(
                    Double.parseDouble(statementTotal.getRefunds())
                            -
                            ((Double.parseDouble(v.getAvailable())
                                    - Double.parseDouble(v.getBooked()))
                                    * Double.parseDouble(v.getPrice())
                            )
            ));
            /**
             * calculate the booked bottles
             * */
            statementTotal.setBottleSales(String.valueOf(
                    Double.parseDouble(statementTotal.getBottleSales())
                            +
                            (Double.parseDouble(v.getBooked())
                                    * Double.parseDouble(v.getPrice())
                            )
            ));
        });
        /**
         * add withdrawn summary of all above components
         * */
        statementTotal.setWithdrawn(String.valueOf(
                Double.parseDouble(statementTotal.getBottleSales())
                        + Double.parseDouble(statementTotal.getTableSales())
                        + Double.parseDouble(statementTotal.getTicketsSales())
        ));
        return statementTotal;
    }

    @Override
    public DoorRevenue getRevenue(String partyName, UserEntity user) {
        DoorRevenue doorRevenue = new DoorRevenue();
        doorRevenue.setRevenue("0");

        ticketService.findAllTicketsByEventAndUser(user.getId_user(), partyName).forEach(v -> {
            logger.info("Ticket = " + v);
            if (Objects.isNull(v.getBooked()) || v.getBooked().equals("")) {
                logger.info("Bad object = " + v);
                v.setBooked("0");
            }
            doorRevenue.setRevenue(String.valueOf(
                    Double.parseDouble(doorRevenue.getRevenue())
                            +
                            (Double.parseDouble(v.getAvailable())
                                    * Double.parseDouble(v.getPrice())
                            )
            ));
        });

        return doorRevenue;
    }
}
