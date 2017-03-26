package com.partymaker.mvc.service.order;

import com.partymaker.mvc.dao.order.TransactionDAO;
import com.partymaker.mvc.model.business.order.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDAO dao;

    @Override
    public void save(Transaction transaction) {
        dao.save(transaction);
    }

    @Override
    public boolean isCompleted(int id) {
        return false;
    }

    @Override
    public Transaction getById(int id) {
        return null;
    }

    @Override
    public void setCompleted(int id, int completeness) {

    }
}
