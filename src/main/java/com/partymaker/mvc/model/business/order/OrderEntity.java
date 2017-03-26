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

    @OneToMany
    @Column(name = "tables")
    private List<OrderedTable> tables = new ArrayList<>();

    @OneToMany
    @Column(name = "tickets")
    private List<OrderedTicket> tickets = new ArrayList<>();

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

    public List<OrderedTable> getTables() {
        return tables;
    }

    public void setTables(List<OrderedTable> tables) {
        this.tables = tables;
    }

    public List<OrderedTicket> getTickets() {
        return tickets;
    }

    public void setTickets(List<OrderedTicket> tickets) {
        this.tickets = tickets;
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
}
