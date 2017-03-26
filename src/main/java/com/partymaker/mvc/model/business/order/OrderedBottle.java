package com.partymaker.mvc.model.business.order;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "ordered_bottle", schema = "partymaker2")
public class OrderedBottle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bottle")
    private int id_bottle;

    @Column(name = "type", nullable = true, length = 45)
    private String type;

    @Column(name = "amount")
    private int amount;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_order")
    private OrderEntity order;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId_bottle() {
        return id_bottle;
    }

    public void setId_bottle(int id_bottle) {
        this.id_bottle = id_bottle;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

}
