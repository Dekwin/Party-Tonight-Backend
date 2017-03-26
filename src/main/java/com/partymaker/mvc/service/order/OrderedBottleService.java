package com.partymaker.mvc.service.order;


import com.partymaker.mvc.dao.order.OrderedBottleDAO;
import com.partymaker.mvc.model.business.order.OrderedBottle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderedBottleService {

    OrderedBottleDAO dao;

    public void save(OrderedBottle bottle) {
        dao.save(bottle);
    }
}
