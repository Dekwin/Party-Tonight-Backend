package com.partymaker.mvc.dao.order;

import com.partymaker.mvc.model.business.order.Transaction;
import com.partymaker.mvc.model.whole.event;

import java.util.List;

public interface TransactionDAO {

    void save(Transaction transaction);

    Transaction getById(int id);

    List<Transaction> getAllTransactionsForEvent(event event);
}