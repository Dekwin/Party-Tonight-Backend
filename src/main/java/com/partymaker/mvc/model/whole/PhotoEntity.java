package com.partymaker.mvc.model.whole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "photo", schema = "partymaker2")
public class PhotoEntity implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id_photo", nullable = false)
    private int idPhoto;

    @Column(name = "photo")
    private String photo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_event")
    private event eventEntity;


    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public event getEventEntity() {
        return eventEntity;
    }

    public void setEventEntity(event eventEntity) {
        this.eventEntity = eventEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhotoEntity that = (PhotoEntity) o;

        if (idPhoto != that.idPhoto) return false;
        if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPhoto;
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        return result;
    }
}
