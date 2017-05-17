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
    private int id_order;

    @Column(name="subtotal")
    private double subtotal;

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

}
