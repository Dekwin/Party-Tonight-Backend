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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="subtotal")
    private double subtotal;

    @Column(name = "seller_email")
    private String sellerEmail;

    @Column(name = "seller_billing_email")
    private String sellerBillingEmail;

    @Column(name = "event_id")
    private int eventId;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "order")
    private List<OrderedBottle> bottles = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = {CascadeType.ALL})
    private OrderedTable table;

    @OneToOne(mappedBy = "order", cascade = {CascadeType.ALL})
    private OrderedTicket ticket;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transactionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getSellerBillingEmail() {
        return sellerBillingEmail;
    }

    public void setSellerBillingEmail(String sellerBillingEmail) {
        this.sellerBillingEmail = sellerBillingEmail;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public List<OrderedBottle> getBottles() {
        return bottles;
    }

    public void setBottles(List<OrderedBottle> bottles) {
        this.bottles = bottles;
    }

    public void addBottle(OrderedBottle bottle) {
        if (this.getBottles() == null) {
            this.setBottles(new ArrayList<>());
        }

        this.getBottles().add(bottle);
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

    public Transaction getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Transaction transactionId) {
        this.transactionId = transactionId;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }
}
