package com.partymaker.mvc.model.whole;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "table", schema = "partymaker2", catalog = "")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private int id_transaction;

    @Column(name = "billing_email")
    private String billingEmail;

    @Column(name = "subtotal")
    private double subtotal;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "seller_email")
    private String sellerEmail;

    @JsonProperty("id_event")
    @Column(name = "id_event")
    private int id_event;


    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TableEntity{");
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
}