package com.partymaker.mvc.dao.order;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.business.order.Transaction;
import com.partymaker.mvc.model.whole.event;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("transactionDAO")
public class TransactionDAOImpl extends AbstractDao<Integer, Transaction> implements TransactionDAO {

    @Override
    public void save(Transaction transaction) {
        persist(transaction);
    }

    @Override
    public Transaction getById(int id) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", id));
        return (Transaction) criteria.list().get(0);
    }

    @Override
    public List<Transaction> getAllTransactionsForEvent(event event) {
        Criteria criteria = createEntityCriteria();

        criteria.add(Restrictions.eq("event_id", event.getId_event()));
        return (List<Transaction>) criteria.list();
    }


}
