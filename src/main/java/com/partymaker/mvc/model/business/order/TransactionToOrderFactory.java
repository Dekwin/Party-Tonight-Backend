package com.partymaker.mvc.model.business.order;

import com.partymaker.mvc.model.business.booking.Booking;

import java.util.ArrayList;
import java.util.List;

public class TransactionToOrderFactory {

    public static List<OrderEntity> getOrders(Transaction transaction, List<Booking> order) {
        List<OrderEntity> orderedEntities = new ArrayList<>(order.size());

        for (Booking booking : order) {
            OrderEntity entity = new OrderEntity();

            entity.setId_transaction(transaction);
            entity.setCustomer(transaction.getCustomerEmail());

            booking.getBottles().forEach(b -> {
                entity.getBottles().add(new OrderedBottle(entity, b));
            });

            if (booking.getTicket() != null) {
                entity.setTicket(new OrderedTicket(entity, booking.getTicket()));
            }

            if (booking.getTable() != null) {
                entity.setTable(new OrderedTable(entity, booking.getTable()));
            }

            orderedEntities.add(entity);
        }

        return orderedEntities;
    }
}