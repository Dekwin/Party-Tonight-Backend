package com.partymaker.mvc.dao.order;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.business.order.OrderedBottle;
import org.springframework.stereotype.Repository;

@Repository("orderedBottleDAO")
public class OrderedBottleDAO extends AbstractDao<Integer, OrderedBottle> {

    public void save(OrderedBottle bottle) {
        persist(bottle);
    }
}