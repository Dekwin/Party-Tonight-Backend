package com.partymaker.mvc.model.whole;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "table", schema = "partymaker2", catalog = "")
public class TableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_table")
    private int id_table;
    @Column(name = "price", nullable = true, length = 45)
    private String price;

    @Column(name = "type", nullable = true, length = 45)
    private String type;

    @Column(name = "available", nullable = true, length = 45)
    private String available;

    @Column(name = "booked", nullable = true, length = 45)
    private String booked;

    @JsonProperty("id_event")
    @Column(name = "id_event")
    private int id_event;


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

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableEntity that = (TableEntity) o;

        if (id_table != that.id_table) return false;
        if (id_event != that.id_event) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (available != null ? !available.equals(that.available) : that.available != null) return false;
        return booked != null ? booked.equals(that.booked) : that.booked == null;
    }

    @Override
    public int hashCode() {
        int result = id_table;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (available != null ? available.hashCode() : 0);
        result = 31 * result + (booked != null ? booked.hashCode() : 0);
        result = 31 * result + id_event;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TableEntity{");
        sb.append("id_table=").append(id_table);
        sb.append(", price='").append(price).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", available='").append(available).append('\'');
        sb.append(", booked='").append(booked).append('\'');
        sb.append(", id_event=").append(id_event);
        sb.append('}');
        return sb.toString();
    }
}
