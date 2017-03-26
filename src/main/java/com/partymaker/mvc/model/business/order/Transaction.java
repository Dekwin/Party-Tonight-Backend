package com.partymaker.mvc.model.business.order;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaction", schema = "partymaker2", catalog = "")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_transaction")
    private int id_transaction;

    @JsonProperty("billing_email")
    @Column(name = "billing_email")
    private String billingEmail;

    @JsonProperty("subtotal")
    @Column(name = "subtotal")
    private double subtotal;

    @JsonProperty("customer_email")
    @Column(name = "customer_email")
    private String customerEmail;

    @JsonProperty("seller_email")
    @Column(name = "seller_email")
    private String sellerEmail;

    @JsonProperty("id_event")
    @Column(name = "id_event")
    private int id_event;

    @Column(name = "completed", columnDefinition = "0")
    private int completed;

    @OneToMany(mappedBy = "id_transaction", cascade = {CascadeType.ALL})
    @Column(name = "order")
    private List<OrderEntity> order = new ArrayList<>();


    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("id_transaction=").append(id_transaction);
        sb.append(", seller_email='").append(sellerEmail).append('\'');
        sb.append(", customer_email='").append(customerEmail).append('\'');
        sb.append(", billing_email='").append(billingEmail).append('\'');
        sb.append(", subtotal='").append(subtotal).append('\'');
        sb.append(", id_event=").append(id_event);
        sb.append('}');
        return sb.toString();
    }

    public int getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(int id_transaction) {
        this.id_transaction = id_transaction;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(String billing_email) {
        this.billingEmail = billing_email;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public List<OrderEntity> getOrder() {
        return order;
    }

    public void setOrder(List<OrderEntity> order) {
        this.order = order;
    }
}