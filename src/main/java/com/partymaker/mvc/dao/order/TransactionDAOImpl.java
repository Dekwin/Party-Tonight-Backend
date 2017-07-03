package com.partymaker.mvc.dao.order;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.business.order.*;
import com.partymaker.mvc.model.whole.event;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("transactionDAO")
public class TransactionDAOImpl extends AbstractDao<Integer, Transaction> implements TransactionDAO {

    @Override
    public void save(Transaction transaction) {
        Transaction temp = new Transaction();

        temp.setPayKey(transaction.getPayKey());
        temp.setCustomerEmail(transaction.getCustomerEmail());
        temp.setServiceBillingEmail(transaction.getServiceBillingEmail());
        temp.setServiceTax(transaction.getServiceTax());
        temp.setServiceEmail(transaction.getServiceEmail());

        for (OrderEntity order : transaction.getOrders()) {

            OrderEntity tempOrder = new OrderEntity();

            for (OrderedBottle bottle : order.getBottles()) {
                OrderedBottle tempBottle = new OrderedBottle();

                tempBottle.setAmount(bottle.getAmount());
                tempBottle.setTitle(bottle.getTitle());
                tempBottle.setOrder(tempOrder);

                tempOrder.addBottle(tempBottle);
            }

            if (order.getTable().getType() != null) {
                OrderedTable tempTable = new OrderedTable();
                tempTable.setNumber(order.getTable().getNumber());
                tempTable.setType(order.getTable().getType());
                tempTable.setOrder(tempOrder);

                tempOrder.setTable(tempTable);
            }

            if (order.getTicket().getType() != null) {
                OrderedTicket tempTicket = new OrderedTicket();
                tempTicket.setType(order.getTicket().getType());
                tempTicket.setOrder(tempOrder);

                tempOrder.setTicket(tempTicket);
            }

            tempOrder.setId_event(order.getId_event());
            tempOrder.setSellerEmail(order.getSellerEmail());
            tempOrder.setSellerBillingEmail(order.getSellerBillingEmail());
            tempOrder.setSubtotal(order.getSubtotal());
            tempOrder.setTransactionId(temp);

            temp.addOrder(tempOrder);
        }

        persist(temp);
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
