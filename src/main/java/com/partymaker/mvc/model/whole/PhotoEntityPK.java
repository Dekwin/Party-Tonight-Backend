package com.partymaker.mvc.model.whole;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
public class PhotoEntityPK implements Serializable {
    private int idPhoto;
    private int idEvent;

    @Column(name = "id_photo", nullable = false)
    @Id
    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    @Column(name = "id_event", nullable = false)
    @Id
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

        PhotoEntityPK that = (PhotoEntityPK) o;

        if (idPhoto != that.idPhoto) return false;
        if (idEvent != that.idEvent) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPhoto;
        result = 31 * result + idEvent;
        return result;
    }
}
