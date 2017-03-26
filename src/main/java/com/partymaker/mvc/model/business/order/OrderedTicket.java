package com.partymaker.mvc.model.business.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.partymaker.mvc.model.business.booking.BookedTicket;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties("id_order")
@Table(name = "ordered_ticket", schema = "partymaker2")
public class OrderedTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private int id_ticket;

    @Column(name = "type", nullable = true, length = 45)
    private String type;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_order")
    private OrderEntity order;

    public OrderedTicket() {
    }

    public OrderedTicket(OrderEntity entity, BookedTicket ticket) {
        this.order = entity;
        this.type = ticket.getType();
    }

    public int getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(int id_ticket) {
        this.id_ticket = id_ticket;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
