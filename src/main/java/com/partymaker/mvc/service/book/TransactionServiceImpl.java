package com.partymaker.mvc.service.book;

import com.partymaker.mvc.dao.user.TransactionDAO;
import com.partymaker.mvc.model.whole.Transaction;
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
