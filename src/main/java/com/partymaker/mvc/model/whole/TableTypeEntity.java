package com.partymaker.mvc.model.whole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "table_type", schema = "partymaker2", catalog = "")
public class TableTypeEntity implements Serializable {
    private int idTableType;
    private String type;

    @Id
    @Column(name = "id_table_type", nullable = false)
    public int getIdTableType() {
        return idTableType;
    }

    public void setIdTableType(int idTableType) {
        this.idTableType = idTableType;
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

        TableTypeEntity that = (TableTypeEntity) o;

        if (idTableType != that.idTableType) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTableType;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
