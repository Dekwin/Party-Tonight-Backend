package com.partymaker.mvc.dao.order;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.business.order.OrderedTable;
import org.springframework.stereotype.Repository;

@Repository("orderedTableDAO")
public class OrderedTableDAO extends AbstractDao<Integer, OrderedTable> {

    public void save(OrderedTable table) {
        persist(table);
    }
}