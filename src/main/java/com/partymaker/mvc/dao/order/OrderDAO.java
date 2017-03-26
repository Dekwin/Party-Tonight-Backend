package com.partymaker.mvc.dao.order;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.business.order.OrderEntity;
import org.springframework.stereotype.Repository;

@Repository("orderDAO")
public class OrderDAO extends AbstractDao<Integer, OrderEntity> {

    public void save(OrderEntity order) {
        persist(order);
    }
}
