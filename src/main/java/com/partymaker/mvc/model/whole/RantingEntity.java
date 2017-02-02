package com.partymaker.mvc.model.whole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "ranting", schema = "partymaker2", catalog = "")
public class RantingEntity implements Serializable {

    @Id
    @Column(name = "id_ranting", nullable = false)
    private int id_ranting;
    @Column(name = "text")
    private String text;

    @Id
    @Column(name = "id_event", nullable = false)
    private int idEvent;

    @Id
    @Column(name = "id_user", nullable = false)
    private int idUser;

    @Id
    @Column(name = "id_role", nullable = false)
    private int idRole;


    public int getId_ranting() {
        return id_ranting;
    }

    public void setId_ranting(int id_ranting) {
        this.id_ranting = id_ranting;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }


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

        if (id_ranting != that.id_ranting) return false;
        if (idEvent != that.idEvent) return false;
        if (idUser != that.idUser) return false;
        if (idRole != that.idRole) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id_ranting;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + idEvent;
        result = 31 * result + idUser;
        result = 31 * result + idRole;
        return result;
    }
}
