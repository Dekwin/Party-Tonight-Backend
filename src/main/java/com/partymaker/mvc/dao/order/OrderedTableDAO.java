package com.partymaker.mvc.dao.order;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.business.order.OrderedTable;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderedTableDAO")
public class OrderedTableDAO extends AbstractDao<Integer, OrderedTable> {

    public void save(OrderedTable table) {
        persist(table);
    }

    @SuppressWarnings("unchecked")
    public OrderedTable getTableByIdEventAndNumber(int id_event, int number) {
        Query query = getSession().createSQLQuery("SELECT * from \n" +
                "`ordered_table` JOIN `order` ON `ordered_table`.`id_order` \n" +
                "WHERE (`order`.`event_id`=:id_event AND \n" +
                "`ordered_table`.`number`=:number)")
                .addEntity(OrderedTable.class)
                .setParameter("id_event", id_event)
                .setParameter("number", number);
        return (OrderedTable) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<OrderedTable> getOrderedTables(int id_event) {
        Query query = getSession().createSQLQuery("SELECT * from \n" +
                "`ordered_table` JOIN `order` ON `ordered_table`.`id_order` \n" +
                "WHERE (`order`.`event_id`=:id_event)")
                .addEntity(OrderedTable.class)
                .setParameter("id_event", id_event);
        return (List<OrderedTable>) query.list();
    }
}