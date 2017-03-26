package com.partymaker.mvc.dao.user;

import com.partymaker.mvc.model.business.order.Transaction;

public interface TransactionDAO {

    void save(Transaction transaction);

    Transaction getById(int id);
}