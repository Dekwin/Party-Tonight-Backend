package com.partymaker.mvc.service.order;

import com.partymaker.mvc.dao.event.EventDAO;
import com.partymaker.mvc.dao.order.TransactionDAO;
import com.partymaker.mvc.dao.user.UserDao;
import com.partymaker.mvc.model.business.booking.Booking;
import com.partymaker.mvc.model.business.order.OrderEntity;
import com.partymaker.mvc.model.business.order.Transaction;
import com.partymaker.mvc.model.business.order.TransactionToOrderFactory;
import com.partymaker.mvc.model.whole.event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDAO dao;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private UserDao userDao;

    @Override
    public void save(Transaction transaction) {
        dao.save(transaction);
    }

    @Override
    public void save(List<Transaction> transactions, List<Booking> bookings) {
        for (Transaction transaction : transactions) {
            if (transaction.getId_event() != 0) {
                List<event> events = eventDAO.getAllByUserId(userDao.findByEmail(transaction.getBillingEmail()).getId_user());
                List<Booking> bookingsOfOne = new ArrayList<>();
                List<Integer> ids = new ArrayList<>();

                for (event e : events) {
                    ids.add(e.getId_event());
                }

                for (Booking b : bookings) {
                    if (ids.contains(b.getId_event())) {
                        bookingsOfOne.add(b);
                    }
                }

                List<OrderEntity> orders = TransactionToOrderFactory.getOrders(transaction, bookingsOfOne);

                transaction.setOrder(orders);
                transaction.setCompleted(1);

                save(transaction);
            }
        }
    }
}
