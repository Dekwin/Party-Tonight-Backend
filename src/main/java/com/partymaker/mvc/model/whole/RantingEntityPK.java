package com.partymaker.mvc.model.whole;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
public class RantingEntityPK implements Serializable {
    private int idRanting;
    private int idEvent;
    private int idUser;
    private int idRole;

    @Column(name = "id_ranting", nullable = false)
    @Id
    public int getIdRanting() {
        return idRanting;
    }

    public void setIdRanting(int idRanting) {
        this.idRanting = idRanting;
    }

    @Column(name = "id_event", nullable = false)
    @Id
    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    @Column(name = "id_user", nullable = false)
    @Id
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Column(name = "id_role", nullable = false)
    @Id
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

        RantingEntityPK that = (RantingEntityPK) o;

        if (idRanting != that.idRanting) return false;
        if (idEvent != that.idEvent) return false;
        if (idUser != that.idUser) return false;
        if (idRole != that.idRole) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRanting;
        result = 31 * result + idEvent;
        result = 31 * result + idUser;
        result = 31 * result + idRole;
        return result;
    }
}
