package com.partymaker.mvc.dao.order;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.business.order.OrderedTable;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("orderedTableDAO")
public class OrderedTableDAO extends AbstractDao<Integer, OrderedTable> {

    public void save(OrderedTable table) {
        persist(table);
    }

    @SuppressWarnings("unchecked")
    public OrderedTable getTableByIdEventAndNumber(int id_event, int number) {
        Query query = getSession().createSQLQuery("SELECT * from \n" +
                "`ordered_table` JOIN `order` ON `ordered_table`.`id_order` \n" +
                "JOIN `transaction` ON `order`.`id_transaction` \n" +
                "WHERE (`transaction`.`id_event`=:id_event AND \n" +
                "`ordered_table`.`number`=:number)")
                .addEntity(OrderedTable.class)
                .setParameter("id_event", id_event)
                .setParameter("number", number);
        return (OrderedTable) query.uniqueResult();
    }
}