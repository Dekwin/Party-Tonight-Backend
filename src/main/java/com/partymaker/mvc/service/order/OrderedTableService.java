package com.partymaker.mvc.service.order;

import com.partymaker.mvc.dao.order.OrderedTableDAO;
import com.partymaker.mvc.model.business.order.OrderedTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;

@Service
@Transactional
public class OrderedTableService {

    @Autowired
    OrderedTableDAO dao;

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
}
