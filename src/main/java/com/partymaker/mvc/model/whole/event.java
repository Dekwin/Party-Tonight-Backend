package com.partymaker.mvc.model.whole;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "event", schema = "partymaker2")
public class event implements Serializable {

    @Id
    @Column(name = "id_event")
    @GeneratedValue
    private int id_event;

    @Column(name = "club_name")
    private String club_name;
    @Column(name = "date")
    private String date;

    /* no visible to frontend (has my inner unique filed) */
    @JsonIgnore
    @Column(name = "time", nullable = true, length = 45)
    private String time;
    @Column(name = "location")
    private String location;
    @Column(name = "club_capacity", nullable = true, length = 45)
    private String club_capacity;
    @Column(name = "party_name")
    private String party_name;
    @Column(name = "zip_code")
    private String zip_code;

    @JsonBackReference(value = "event-bottle")
    @OneToMany(mappedBy = "event",  fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<BottleEntity> bottles = new ArrayList<>();

    @JsonBackReference(value = "event-ticket")
    @OneToMany(mappedBy = "eventEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<TicketEntity> tickets = new ArrayList<>();

    @JsonBackReference(value = "event-table")
    @OneToMany(mappedBy = "eventEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<TableEntity> tables = new ArrayList<>();

    @JsonBackReference(value = "event-photo")
    //@JsonManagedReference
    @OneToMany(mappedBy = "eventEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<PhotoEntity> photos = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "events")
    private List<UserEntity> users = new ArrayList<>();

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
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

    public List<BottleEntity> getBottles() {
        return bottles;
    }

    public void setBottles(List<BottleEntity> bottles) {
        this.bottles = bottles;
    }

    public List<TicketEntity> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }

    public List<TableEntity> getTables() {
        return tables;
    }

    public void setTables(List<TableEntity> tables) {
        this.tables = tables;
    }

    public List<PhotoEntity> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoEntity> photos) {
        this.photos = photos;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        event event = (event) o;

        if (id_event != event.id_event) return false;
        if (club_name != null ? !club_name.equals(event.club_name) : event.club_name != null) return false;
        if (date != null ? !date.equals(event.date) : event.date != null) return false;
        if (time != null ? !time.equals(event.time) : event.time != null) return false;
        if (location != null ? !location.equals(event.location) : event.location != null) return false;
        if (club_capacity != null ? !club_capacity.equals(event.club_capacity) : event.club_capacity != null)
            return false;
        if (party_name != null ? !party_name.equals(event.party_name) : event.party_name != null) return false;
        if (zip_code != null ? !zip_code.equals(event.zip_code) : event.zip_code != null) return false;
        if (bottles != null ? !bottles.equals(event.bottles) : event.bottles != null) return false;
        if (tickets != null ? !tickets.equals(event.tickets) : event.tickets != null) return false;
        if (tables != null ? !tables.equals(event.tables) : event.tables != null) return false;
        if (photos != null ? !photos.equals(event.photos) : event.photos != null) return false;
        return users != null ? users.equals(event.users) : event.users == null;
    }

    @Override
    public int hashCode() {
        int result = id_event;
        result = 31 * result + (club_name != null ? club_name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (club_capacity != null ? club_capacity.hashCode() : 0);
        result = 31 * result + (party_name != null ? party_name.hashCode() : 0);
        result = 31 * result + (zip_code != null ? zip_code.hashCode() : 0);
        result = 31 * result + (bottles != null ? bottles.hashCode() : 0);
        result = 31 * result + (tickets != null ? tickets.hashCode() : 0);
        result = 31 * result + (tables != null ? tables.hashCode() : 0);
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("event{");
        sb.append("id_event=").append(id_event);
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
