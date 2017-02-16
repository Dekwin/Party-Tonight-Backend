package com.partymaker.mvc.service.book;

import com.partymaker.mvc.dao.event.EventDAO;
import com.partymaker.mvc.dao.event.bottle.BottleDAO;
import com.partymaker.mvc.dao.event.table.TableDAO;
import com.partymaker.mvc.dao.event.ticket.TicketDAO;
import com.partymaker.mvc.model.business.Book;
import com.partymaker.mvc.model.whole.BottleEntity;
import com.partymaker.mvc.model.whole.TableEntity;
import com.partymaker.mvc.model.whole.TicketEntity;
import com.partymaker.mvc.model.whole.event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by anton on 16.02.17.
 */
@Service
@Transactional
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);


    @Autowired
    private EventDAO<event> eventDAO;

    @Autowired
    private BottleDAO bottleDAO;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private TableDAO tableDAO;

    public String book(Book book) {
        logger.info("Received book = " + book);
        event e = eventDAO.getEventByName(book.getPartyName());

        TicketEntity t = ticketDAO.getTicketByEventId(e.getId_event());

        validateTickets(t, book);

        List<BottleEntity> bottleEntity = bottleDAO.getBottleByEventId(e.getId_event());

        bottleEntity.forEach(b -> {
            book.getBottles().forEach(v -> {
                if (b.getId_event() == v.getId_event()) {
                    int aa = Integer.parseInt(b.getAvailable());
                    int bb = Integer.parseInt(b.getBooked());

                    if (aa == bb) {
                        logger.info("No more bottles left.");
                        throw new RuntimeException("No more bottles of " + v.getName() + "left.");
                    }

                    if (aa - bb < Integer.parseInt(v.getBooked())) {
                        logger.info("Only " + (aa - bb) + " bottles left.");
                        throw new RuntimeException("Only " + (aa - bb) + " of " + v.getName() + " left.");
                    }

                    b.setBooked(b.getBooked() + v.getBooked());
                }
            });
        });
        TableEntity ta = tableDAO.getTableByEventId(e.getId_event(), book.getTables().get(0).getType());

        validateTables(ta, book);

        t.setBooked(t.getBooked() + book.getTickets());
        ta.setBooked(ta.getBooked() + book.getTables().size());

        return "Success";
    }

    private void validateTables(TableEntity ta, Book book) {
        int a = Integer.parseInt(ta.getAvailable());
        int b = Integer.parseInt(ta.getBooked());

        if (a == b) {
            logger.info("No more tables left.");
            throw new RuntimeException("No more tables left.");
        }

        if (a - b < book.getBottles().size()) {
            logger.info("Only " + (a - b) + " tables left.");
            throw new RuntimeException("Only " + (a - b) + " tables left.");
        }
    }

    public void validateBottles(List<BottleEntity> bottleEntity, List<BottleEntity> bottles, Book book) {
        bottles.forEach(v -> {
            int aa = Integer.parseInt(v.getAvailable());
            int bb = Integer.parseInt(v.getBooked());

            if (aa == bb) {
                logger.info("No more bottles left.");
                throw new RuntimeException("No more bottles of " + v.getName() + "left.");
            }

            if (aa - bb < book.getBottles().size()) {
                logger.info("Only " + (aa - bb) + " bottles left.");
                throw new RuntimeException("Only " + (aa - bb) + " of " + v.getName() + " left.");
            }
        });
    }

    public void validateTickets(TicketEntity t, Book book) {
        int a = Integer.parseInt(t.getAvailable());
        int b = Integer.parseInt(t.getBooked());

        if (a == b) {
            logger.info("No more tickets left.");
            throw new RuntimeException("No more tickets left.");
        }
        if (a - b < book.getTickets()) {
            logger.info("Only " + (a - b) + " tickets left.");
            throw new RuntimeException("Only " + (a - b) + " tickets left.");
        }
    }
}
