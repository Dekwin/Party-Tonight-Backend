package com.partymaker.mvc.model.whole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "photo", schema = "partymaker2", catalog = "")
@IdClass(PhotoEntityPK.class)
public class PhotoEntity implements Serializable {
    private int idPhoto;
    private String photo;
    private int idEvent;

    @Id
    @Column(name = "id_photo", nullable = false)
    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    @Basic
    @Column(name = "photo", nullable = true, length = 45)
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Id
    @Column(name = "id_event", nullable = false)
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

        PhotoEntity that = (PhotoEntity) o;

        if (idPhoto != that.idPhoto) return false;
        if (idEvent != that.idEvent) return false;
        if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPhoto;
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + idEvent;
        return result;
    }
}
