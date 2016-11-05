package com.partymaker.mvc.model.whole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "user_has_event", schema = "partymaker2", catalog = "")
public class UserHasEventEntity implements Serializable{
    @Id
    @Column(name = "id_user", nullable = false)
    private int idUser;

    @Id
    @Column(name = "id_event", nullable = false)
    private int idEvent;


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }


    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHasEventEntity that = (UserHasEventEntity) o;

        if (idUser != that.idUser) return false;
        if (idEvent != that.idEvent) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser;
        result = 31 * result + idEvent;
        return result;
    }
}
