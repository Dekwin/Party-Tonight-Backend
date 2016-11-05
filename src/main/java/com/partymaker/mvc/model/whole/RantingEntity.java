package com.partymaker.mvc.model.whole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "ranting", schema = "partymaker2", catalog = "")
@IdClass(RantingEntityPK.class)
public class RantingEntity implements Serializable {
    private int idRanting;
    private String text;
    private int idEvent;
    private int idUser;
    private int idRole;

    @Id
    @Column(name = "id_ranting", nullable = false)
    public int getIdRanting() {
        return idRanting;
    }

    public void setIdRanting(int idRanting) {
        this.idRanting = idRanting;
    }

    @Basic
    @Column(name = "text", nullable = true, length = -1)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
    @Column(name = "id_user", nullable = false)
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Id
    @Column(name = "id_role", nullable = false)
    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RantingEntity that = (RantingEntity) o;

        if (idRanting != that.idRanting) return false;
        if (idEvent != that.idEvent) return false;
        if (idUser != that.idUser) return false;
        if (idRole != that.idRole) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRanting;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + idEvent;
        result = 31 * result + idUser;
        result = 31 * result + idRole;
        return result;
    }
}
