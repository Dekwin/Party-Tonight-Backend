package com.partymaker.mvc.dao.order;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.business.order.OrderedBottle;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderedBottleDAO")
public class OrderedBottleDAO extends AbstractDao<Integer, OrderedBottle> {

    public void save(OrderedBottle bottle) {
        persist(bottle);
    }

    @SuppressWarnings("unchecked")
    public List<OrderedBottle> getBottleByEventIdAndTitle(int id_event, String title) {
        Query query = getSession().createSQLQuery("SELECT * from \n" +
                "`ordered_bottle` JOIN `order` ON `ordered_bottle`.`id_order` \n" +
                "WHERE (`order`.`id_event`=:id_event AND `ordered_bottle`.`title`=:title)")
                .addEntity(OrderedBottle.class)
                .setParameter("id_event", id_event)
                .setParameter("title", title);
        return (List<OrderedBottle>) query.list();
    }
}