import com.partymaker.mvc.configuration.ApplicationContext;
import com.partymaker.mvc.dao.event.EventDAO;
import com.partymaker.mvc.dao.event.bottle.BottleDAO;
import com.partymaker.mvc.dao.event.table.TableDAO;
import com.partymaker.mvc.dao.event.ticket.TicketDAO;
import com.partymaker.mvc.model.whole.event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

/**
 * Created by anton on 02.02.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContext.class})
@Transactional
@WebAppConfiguration
public class EventTest {

    @Autowired
    private EventDAO<event> eventDAO;

    @Autowired
    private BottleDAO bottleDAO;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private TableDAO tableDAO;

    @Rollback(false)
    @Test
    public void tets() {
        System.out.println(eventDAO.getEventByName("1"));
        System.out.println(bottleDAO.getBottleByEventId(eventDAO.getEventByName("1").getId_event()));
        System.out.println(ticketDAO.getTicketByEventId(eventDAO.getEventByName("1").getId_event()));
        System.out.println(tableDAO.getTableByEventId(eventDAO.getEventByName("1").getId_event(), "type"));
    }
}
