package com.partymaker.mvc.model.business.order;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order", schema = "partymaker2")
public class OrderEntity implements Serializable {

    @Id
    @Column(name = "id_order")
    @GeneratedValue
    private int id_order;

    @Column(name = "customer", unique = true)
    private String customer;

    @Column(name = "date_created")
    private String date_created;

    @OneToMany
    @Column(name = "bottles")
    private List<OrderedBottle> bottles = new ArrayList<>();

    @OneToOne
    @Column(name = "table")
    private OrderedTable table;

    @OneToOne
    @Column(name = "ticket")
    private OrderedTicket ticket;

    @ManyToOne
    @Column(name = "id_transaction")
    private int id_transaction;

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(int id_transaction) {
        this.id_transaction = id_transaction;
    }

    public List<OrderedBottle> getBottles() {
        return bottles;
    }

    public void setBottles(List<OrderedBottle> bottles) {
        this.bottles = bottles;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date_created;
    }

    public void setDate(String date) {
        this.date_created = date;
    }

    public OrderedTable getTable() {
        return table;
    }

    public void setTable(OrderedTable table) {
        this.table = table;
    }

    public OrderedTicket getTicket() {
        return ticket;
    }

    public void setTicket(OrderedTicket ticket) {
        this.ticket = ticket;
    }
}
