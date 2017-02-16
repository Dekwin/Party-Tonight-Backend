package com.partymaker.mvc.model.whole;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty("id_event")
    @Column(name = "id_event")
    private int id_event;


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

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
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
