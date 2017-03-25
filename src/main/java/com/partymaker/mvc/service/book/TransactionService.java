package com.partymaker.mvc.service.book;

import com.partymaker.mvc.model.whole.Transaction;

public interface TransactionService {

    void save(Transaction transaction);

    boolean isCompleted(int id);

    Transaction getById(int id);

    void setCompleted(int id, int completeness);
}
