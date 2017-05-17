package com.partymaker.mvc.service.order;

import com.partymaker.mvc.dao.order.TransactionDAO;
import com.partymaker.mvc.model.business.order.Transaction;
import com.partymaker.mvc.model.whole.event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public List<Transaction> getAllTransactionsForEvent(event event) {
        return dao.getAllTransactionsForEvent(event);
    }
}
