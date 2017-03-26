package com.partymaker.mvc.service.order;

import com.partymaker.mvc.dao.order.OrderedTableDAO;
import com.partymaker.mvc.model.business.order.OrderedTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderedTableService {

    OrderedTableDAO dao;

    public void save(OrderedTable table) {
        dao.save(table);
    }

    public OrderedTable getTable(int id_event, String type, int number) {
        return dao.getTableByIdEventTypeNumber(id_event, type, number);
    }
}
