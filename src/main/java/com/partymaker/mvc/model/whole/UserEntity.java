package com.partymaker.mvc.model.whole;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "user", schema = "partymaker2")
public class UserEntity implements Serializable {
    @Id
    @Column(name = "id_user", nullable = false)
    private int id_user;

    @Column(name = "user_name", nullable = true, length = 45)
    private String user_name;

    @Column(name = "phone_number", nullable = true, length = 45)
    private String phone_number;

    @Column(name = "email", nullable = true, length = 45)
    private String email;

    @Column(name = "emergency_contact", nullable = true, length = 45)
    private String emergency_contact;

    @Column(name = "password", nullable = true, length = 45)
    private String password;

    @Column(name = "enable", nullable = true)
    private boolean enable;

    @Column(name = "updated_date", nullable = true, length = 45)
    private String updatedDate;

    @Column(name = "created_date", nullable = true, length = 45)
    private String createdDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_role")
    private RoleEntity role;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_billing", nullable = false)
    private BillingEntity billing;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_has_event",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_event")})
    private List<EventEntity> events = new ArrayList<>();


    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergency_contact() {
        return emergency_contact;
    }

    public void setEmergency_contact(String emergency_contact) {
        this.emergency_contact = emergency_contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }


    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }


    public BillingEntity getBilling() {
        return billing;
    }

    public void setBilling(BillingEntity billing) {
        this.billing = billing;
    }

    public boolean isEnable() {
        return enable;
    }

    public List<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(List<EventEntity> events) {
        this.events = events;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserEntity{");
        sb.append("id_user=").append(id_user);
        sb.append(", user_name='").append(user_name).append('\'');
        sb.append(", phone_number='").append(phone_number).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", emergency_contact='").append(emergency_contact).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", enable=").append(enable);
        sb.append(", updatedDate='").append(updatedDate).append('\'');
        sb.append(", createdDate='").append(createdDate).append('\'');
        sb.append(", role=").append(role);
        sb.append(", billing=").append(billing);
        sb.append('}');
        return sb.toString();
    }
}
