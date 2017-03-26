package com.partymaker.mvc.service.order;

import com.partymaker.mvc.model.business.booking.Booking;
import com.partymaker.mvc.model.business.order.Transaction;

import java.util.List;

public interface TransactionService {

    void save(Transaction transaction);

    void save(List<Transaction> transactions, List<Booking> bookings);
}
