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
    @Column(name = "id")
    private int id;

    @Column(name = "pay_key")
    private String payKey;

    @Column(name = "service_tax")
    private double serviceTax;

    @Column(name = "customer_billing_email")
    private String customerBillingEmail;

    @Column(name = "service_billing_email")
    private String serviceBillingEmail;

    @OneToMany(mappedBy = "transaction", cascade = {CascadeType.ALL})
    private List<OrderEntity> order = new ArrayList<>();
}