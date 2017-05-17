package com.partymaker.mvc.service.order;

import com.partymaker.mvc.model.business.order.Transaction;
import com.partymaker.mvc.model.whole.event;

import java.util.List;

public interface TransactionService {

    void save(Transaction transaction);

    List<Transaction> getAllTransactionsForEvent(event event);
}
