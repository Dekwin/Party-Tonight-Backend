package com.partymaker.mvc.model.whole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "ticket", schema = "partymaker2", catalog = "")
public class TicketEntity implements Serializable{
    @Id
    @Column(name = "id_ticket", nullable = false)
    private int idTicket;
    @Column(name = "price", nullable = true, length = 45)
    private String price;
    @Column(name = "available", nullable = true, length = 45)
    private String available;
    @Column(name = "booked", nullable = true, length = 45)
    private String booked;
    @Column(name = "created_date", nullable = true, length = 45)
    private String createdDate;
    @Id
    @Column(name = "id_event", nullable = false)
    private int idEvent;


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


    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }


    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketEntity that = (TicketEntity) o;

        if (idTicket != that.idTicket) return false;
        if (idEvent != that.idEvent) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (available != null ? !available.equals(that.available) : that.available != null) return false;
        if (booked != null ? !booked.equals(that.booked) : that.booked != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTicket;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (available != null ? available.hashCode() : 0);
        result = 31 * result + (booked != null ? booked.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + idEvent;
        return result;
    }
}
