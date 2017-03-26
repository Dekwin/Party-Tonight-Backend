package com.partymaker.mvc.model.business.order;

import com.partymaker.mvc.model.business.Book;
import com.partymaker.mvc.model.whole.BottleEntity;
import com.partymaker.mvc.model.whole.TableEntity;

import java.util.ArrayList;
import java.util.List;

public class TransactionToOrderFactory {

    /***
     * We need to remember, that customer cannot buy
     * -> more than one table
     * -> more than one ticket (?)
     *
     * @param transaction
     * @param order
     * @return
     */
    public static List<OrderEntity> getOrders(Transaction transaction, List<Book> order) {
        List<OrderEntity> orderedEntities = new ArrayList<>(order.size());

        for (Book booking : order) {
            OrderEntity entity = new OrderEntity();

            entity.setId_transaction(transaction.getId_transaction());

            // bottles
            booking.getBottles().forEach(bottle -> entity.getBottles().add(bottleToOrderedOne(entity, bottle)));

            // ticket
            entity.getTickets().add(intToOrderedTicket(entity, "", booking.getTickets()));

            // table
            for (TableEntity table : booking.getTables()) {
                if (Integer.parseInt(table.getBooked()) != 0) {
                    entity.getTables().add(intToOrderedTable(table.getType(), 1));
                }
            }

            orderedEntities.add(entity);
        }

        return orderedEntities;
    }

    private static OrderedTable intToOrderedTable(String type, int number) {
        OrderedTable orderedTable = new OrderedTable();

        orderedTable.setType(type);
        orderedTable.setNumber(number);

        return orderedTable;
    }

    private static OrderedTicket intToOrderedTicket(OrderEntity order, String type, int amount) {
        OrderedTicket ticket = new OrderedTicket();

        ticket.setOrder(order);
        ticket.setType(type);
        ticket.setAmount(amount);

        return ticket;
    }

    private static OrderedTable tableToOrderedOne(OrderEntity order, TableEntityExtended table) {
        OrderedTable resultedTable = new OrderedTable();

        resultedTable.setOrder(order);
        resultedTable.setType(table.getType());
        resultedTable.setNumber(table.getNumber());

        return resultedTable;
    }

    private static OrderedBottle bottleToOrderedOne(OrderEntity order, BottleEntity bottle) {
        OrderedBottle resultedBottle = new OrderedBottle();

        resultedBottle.setOrder(order);
        resultedBottle.setAmount(Integer.parseInt(bottle.getBooked()));
        resultedBottle.setType(bottle.getType());

        return resultedBottle;
    }
}
