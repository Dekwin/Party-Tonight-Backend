package com.partymaker.mvc.model.whole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "bottle", schema = "partymaker2")
public class BottleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bottle")
    private int id_bottle;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private String price;
    @Column(name = "type")
    private String type;
    @Column(name = "available")
    private String available;
    @Column(name = "booked")
    private String booked;
    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "id_event")
    private int id_event;


    public int getId_bottle() {
        return id_bottle;
    }

    public void setId_bottle(int id_bottle) {
        this.id_bottle = id_bottle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BottleEntity{");
        sb.append("id_bottle=").append(id_bottle);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price='").append(price).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", available='").append(available).append('\'');
        sb.append(", booked='").append(booked).append('\'');
        sb.append(", createdDate='").append(createdDate).append('\'');
        sb.append(", id_event=").append(id_event);
        sb.append('}');
        return sb.toString();
    }
}
