package com.partymaker.mvc.model.whole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "table", schema = "partymaker2", catalog = "")
public class TableEntity implements Serializable{
    @Id
    @Column(name = "id_table", nullable = false)
    private int idTable;
    @Column(name = "price", nullable = true, length = 45)
    private String price;
    @Column(name = "available", nullable = true, length = 45)
    private String available;
    @Column(name = "booked", nullable = true, length = 45)
    private String booked;
    @Id
    @Column(name = "id_event", nullable = false)
    private int idEvent;
    @Id
    @Column(name = "id_table_type", nullable = false)
    private int idTableType;


    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
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

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getIdTableType() {
        return idTableType;
    }

    public void setIdTableType(int idTableType) {
        this.idTableType = idTableType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableEntity that = (TableEntity) o;

        if (idTable != that.idTable) return false;
        if (idEvent != that.idEvent) return false;
        if (idTableType != that.idTableType) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (available != null ? !available.equals(that.available) : that.available != null) return false;
        if (booked != null ? !booked.equals(that.booked) : that.booked != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTable;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (available != null ? available.hashCode() : 0);
        result = 31 * result + (booked != null ? booked.hashCode() : 0);
        result = 31 * result + idEvent;
        result = 31 * result + idTableType;
        return result;
    }
}
