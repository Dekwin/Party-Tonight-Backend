package com.partymaker.mvc.service.order;

import com.partymaker.mvc.dao.event.table.TableDAO;
import com.partymaker.mvc.dao.order.OrderedTableDAO;
import com.partymaker.mvc.model.business.booking.BookedTable;
import com.partymaker.mvc.model.business.order.OrderedTable;
import com.partymaker.mvc.model.whole.TableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class OrderedTableService {

    @Autowired
    private OrderedTableDAO dao;

    @Autowired
    private TableDAO tableDAO;

    public void save(OrderedTable table) {
        dao.save(table);
    }

    /**
     * Because according to files every table in event has unique number
     * we have find it in repo via id of the event and number of it.
     *
     * @param id_event id of the event
     * @param number   number of the table
     * @return ordered table if exist. Null if not.
     */
    @Nullable
    public OrderedTable getTable(int id_event, int number) {
        return dao.getTableByIdEventAndNumber(id_event, number);
    }

    /**
     * @param id_event
     * @return
     */
    public List<OrderedTable> getOrderedTables(int id_event) {
        // getting (or not) ordered tables via dao
        return dao.getOrderedTables(id_event);
    }

    /**
     * @param id_event
     * @return
     */
    public List<BookedTable> getFreeTables(int id_event) {
        List<TableEntity> totalTables = tableDAO.findAllByEventId(id_event);
        HashMap<Integer, BookedTable> tables = new HashMap<>();

        // we generate list where all the tables are free
        int lastIndex = 1;
        for (TableEntity tableEntity : totalTables) {

            for (int i = 0; i < Integer.parseInt(tableEntity.getAvailable()); i++) {
                BookedTable table = new BookedTable();

                table.setNumber(lastIndex + i);
                table.setType(tableEntity.getType());
                table.setPrice(Integer.parseInt(tableEntity.getPrice()));

                tables.put(table.getNumber(), table);
            }

            lastIndex = tables.size();
        }

        // getting (or not) ordered tables via dao
        List<OrderedTable> orderedTables = getOrderedTables(id_event);

        if (orderedTables != null) {
            for (OrderedTable table : orderedTables) {
                tables.remove(table.getNumber());
            }
        }

        return new ArrayList<>(tables.values());
    }
}
