package com.partymaker.mvc.model.whole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "ticket", schema = "partymaker2", catalog = "")
public class TicketEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private int idTicket;
    @Column(name = "price", nullable = true, length = 45)
    private String price;
    @Column(name = "available", nullable = true, length = 45)
    private String available;
    @Column(name = "booked", nullable = true, length = 45)
    private String booked;
    @Column(name = "created_date", nullable = true, length = 45)
    private String created_date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_event")
    private event eventEntity;


    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getBooked() {
        return booked;
    }

    public void setBooked(String booked) {
        this.booked = booked;
    }


    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public event getEventEntity() {
        return eventEntity;
    }

    public void setEventEntity(event eventEntity) {
        this.eventEntity = eventEntity;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TicketEntity{");
        sb.append("idTicket=").append(idTicket);
        sb.append(", price='").append(price).append('\'');
        sb.append(", available='").append(available).append('\'');
        sb.append(", booked='").append(booked).append('\'');
        sb.append(", created_date='").append(created_date).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
