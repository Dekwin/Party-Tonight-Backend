package com.partymaker.mvc.service.order;

import com.partymaker.mvc.model.business.order.Transaction;

public interface TransactionService {

    void save(Transaction transaction);

    boolean isCompleted(int id);

    Transaction getById(int id);

    void setCompleted(int id, int completeness);
}
