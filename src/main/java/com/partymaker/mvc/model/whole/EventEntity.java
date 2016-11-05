package com.partymaker.mvc.model.whole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "event", schema = "partymaker2")
public class EventEntity implements Serializable {

    @Id
    @Column(name = "id_event", nullable = false)
    private int idEvent;
    @Column(name = "club_name", nullable = true, length = 45)
    private String club_name;
    @Column(name = "date", nullable = true, length = 45)
    private String date;
    @Column(name = "time", nullable = true, length = 45)
    private String time;
    @Column(name = "location", nullable = true, length = 45)
    private String location;
    @Column(name = "club_capacity", nullable = true, length = 45)
    private String club_capacity;
    @Column(name = "party_name", nullable = true, length = 45)
    private String party_name;
    @Column(name = "zip_code", nullable = true, length = 45)
    private String zip_code;

    /*@ManyToMany(mappedBy = "events")
    private List<UserEntity> users = new ArrayList<>();*/

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }


    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getClub_capacity() {
        return club_capacity;
    }

    public void setClub_capacity(String club_capacity) {
        this.club_capacity = club_capacity;
    }


    public String getParty_name() {
        return party_name;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
    }


    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

   /* public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventEntity that = (EventEntity) o;

        if (idEvent != that.idEvent) return false;
        if (club_name != null ? !club_name.equals(that.club_name) : that.club_name != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (club_capacity != null ? !club_capacity.equals(that.club_capacity) : that.club_capacity != null)
            return false;
        if (party_name != null ? !party_name.equals(that.party_name) : that.party_name != null) return false;
        if (zip_code != null ? !zip_code.equals(that.zip_code) : that.zip_code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEvent;
        result = 31 * result + (club_name != null ? club_name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (club_capacity != null ? club_capacity.hashCode() : 0);
        result = 31 * result + (party_name != null ? party_name.hashCode() : 0);
        result = 31 * result + (zip_code != null ? zip_code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EventEntity{");
        sb.append("idEvent=").append(idEvent);
        sb.append(", club_name='").append(club_name).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", time='").append(time).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", club_capacity='").append(club_capacity).append('\'');
        sb.append(", party_name='").append(party_name).append('\'');
        sb.append(", zip_code='").append(zip_code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
