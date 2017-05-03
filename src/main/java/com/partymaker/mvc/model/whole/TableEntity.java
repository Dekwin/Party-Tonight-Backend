package com.partymaker.mvc.model.whole;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@JsonIgnoreProperties("id_event")
@Table(name = "table", schema = "partymaker2", catalog = "")
public class TableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_table")
    private int id_table;
    @Column(name = "price",  length = 45)
    private String price = "0";
    @Column(name = "type", nullable = true, length = 45)
    private String type;
    @Column(name = "available", length = 45)
    private String available = "0";
    @Column(name = "booked", length = 45)
    private String booked = "0";


    @JsonIgnore
    @ManyToOne(optional = false)
    //@JsonManagedReference(value = "event-table")
    @JoinColumn(name = "id_event")
    private event eventEntity;


    public int getId_table() {
        return id_table;
    }

    public void setId_table(int id_table) {
        this.id_table = id_table;
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


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public event getEventEntity() {
        return eventEntity;
    }

    public void setEventEntity(event eventEntity) {
        this.eventEntity = eventEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableEntity that = (TableEntity) o;

        if (id_table != that.id_table) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (available != null ? !available.equals(that.available) : that.available != null) return false;
        if (booked != null ? !booked.equals(that.booked) : that.booked != null) return false;
        return true;

    }

    @Override
    public int hashCode() {
        int result = id_table;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (available != null ? available.hashCode() : 0);
        result = 31 * result + (booked != null ? booked.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TableEntity{");
        sb.append("id_table=").append(id_table);
        sb.append(", price='").append(price).append('\'');
        sb.append(", available='").append(available).append('\'');
        sb.append(", booked='").append(booked).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
