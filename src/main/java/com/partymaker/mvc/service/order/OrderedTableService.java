package com.partymaker.mvc.service.order;

import com.partymaker.mvc.dao.order.OrderedTableDAO;
import com.partymaker.mvc.model.business.order.OrderedTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderedTableService {

    @Autowired
    OrderedTableDAO dao;

    public void save(OrderedTable table) {
        dao.save(table);
    }

    public OrderedTable getTable(int id_event, String type, int number) {
        List<OrderedTable> results = dao.getTableByIdEventTypeNumber(id_event, type, number);

        if (results != null && results.size() > 0) {
            return results.get(0);
        } else return null;
    }
}
