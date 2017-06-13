package com.partymaker.mvc.dao.order;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.business.order.OrderEntity;
import com.partymaker.mvc.model.whole.event;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderDAO")
public class OrderDAO extends AbstractDao<Integer, OrderEntity> {

    public void save(OrderEntity order) {
        persist(order);
    }

    public List<OrderEntity> findAllForEvent(event event) {
        Criteria criteria = createEntityCriteria();

        criteria.add(Restrictions.eq("eventId", event.getId_event()));

        return (List<OrderEntity>) criteria.list();
    }
}
