package com.partymaker.mvc.model.whole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "bottle_type", schema = "partymaker2", catalog = "")
public class BottleTypeEntity implements Serializable {
    private int idBottleType;
    private String type;

    @Id
    @Column(name = "id_bottle_type", nullable = false)
    public int getIdBottleType() {
        return idBottleType;
    }

    public void setIdBottleType(int idBottleType) {
        this.idBottleType = idBottleType;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 45)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BottleTypeEntity that = (BottleTypeEntity) o;

        if (idBottleType != that.idBottleType) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idBottleType;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
