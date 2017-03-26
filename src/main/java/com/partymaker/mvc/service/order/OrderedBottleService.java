package com.partymaker.mvc.service.order;


import com.partymaker.mvc.dao.order.OrderedBottleDAO;
import com.partymaker.mvc.model.business.order.OrderedBottle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderedBottleService {

    @Autowired
    OrderedBottleDAO dao;

    public void save(OrderedBottle bottle) {
        dao.save(bottle);
    }

    public OrderedBottle getBottleByEventIdAndTitle(int id_event, String title) {
        List<OrderedBottle> results = dao.getBottleByEventIdAndTitle(id_event, title);

        if (results != null && results.size() > 0) {
            return results.get(0);
        } else return null;
    }
}
