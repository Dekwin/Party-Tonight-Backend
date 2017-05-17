package com.partymaker.mvc.model.business.order;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaction", schema = "partymaker2", catalog = "")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "pay_key")
    private String payKey;

    @Column(name = "service_tax")
    private double serviceTax;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "service_email")
    private String serviceEmail;

    @Column(name = "service_billing_email")
    private String serviceBillingEmail;

    @OneToMany(mappedBy = "transaction", cascade = {CascadeType.ALL})
    private List<OrderEntity> orders = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public double getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(double serviceTax) {
        this.serviceTax = serviceTax;
    }

    public String getServiceBillingEmail() {
        return serviceBillingEmail;
    }

    public void setServiceBillingEmail(String serviceBillingEmail) {
        this.serviceBillingEmail = serviceBillingEmail;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> order) {
        this.orders = order;
    }

    public void addOrder(OrderEntity order) {
        this.orders.add(order);
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getServiceEmail() {
        return serviceEmail;
    }

    public void setServiceEmail(String serviceEmail) {
        this.serviceEmail = serviceEmail;
    }
}