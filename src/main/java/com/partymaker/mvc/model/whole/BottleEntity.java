package com.partymaker.mvc.model.whole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "bottle", schema = "partymaker2")
public class BottleEntity implements Serializable{
    private int idBottle;
    private String name;
    private String prise;
    private String type;
    private String available;
    private String booked;
    private String createdDate;
    private int idEvent;
    private int idBottleType;

    @Id
    @Column(name = "id_bottle", nullable = false)
    public int getIdBottle() {
        return idBottle;
    }

    public void setIdBottle(int idBottle) {
        this.idBottle = idBottle;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "prise", nullable = true, length = 45)
    public String getPrise() {
        return prise;
    }

    public void setPrise(String prise) {
        this.prise = prise;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 45)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "available", nullable = true, length = 45)
    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    @Basic
    @Column(name = "booked", nullable = true, length = 45)
    public String getBooked() {
        return booked;
    }

    public void setBooked(String booked) {
        this.booked = booked;
    }

    @Basic
    @Column(name = "created_date", nullable = true, length = 45)
    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Id
    @Column(name = "id_event", nullable = false)
    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    @Id
    @Column(name = "id_bottle_type", nullable = false)
    public int getIdBottleType() {
        return idBottleType;
    }

    public void setIdBottleType(int idBottleType) {
        this.idBottleType = idBottleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BottleEntity that = (BottleEntity) o;

        if (idBottle != that.idBottle) return false;
        if (idEvent != that.idEvent) return false;
        if (idBottleType != that.idBottleType) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (prise != null ? !prise.equals(that.prise) : that.prise != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (available != null ? !available.equals(that.available) : that.available != null) return false;
        if (booked != null ? !booked.equals(that.booked) : that.booked != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idBottle;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (prise != null ? prise.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (available != null ? available.hashCode() : 0);
        result = 31 * result + (booked != null ? booked.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + idEvent;
        result = 31 * result + idBottleType;
        return result;
    }
}
