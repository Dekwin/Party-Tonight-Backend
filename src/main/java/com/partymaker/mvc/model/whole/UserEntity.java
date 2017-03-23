package com.partymaker.mvc.model.whole;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String userName;

    @Column(name = "phone_number", nullable = true, length = 45)
    private String phoneNumber;

    @Column(name = "email", nullable = true, length = 45)
    private String email;

    @Column(name = "emergency_contact", nullable = true, length = 45)
    private String emergencyContact;

    @Column(name = "password", nullable = true, length = 45)
    private String password;

    @Column(name = "enable", nullable = true)
    private boolean enable;

    @Column(name = "updated_date", nullable = true, length = 45)
    private String updatedDate;

    @Column(name = "created_date", nullable = true, length = 45)
    private String createdDate;

    @Column(name = "billing_email", nullable = true, length = 45)
    private String billingEmail;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_role")
    private RoleEntity role;

    public BillingEntity getBillingEntity() {
        return billingEntity;
    }

    public void setBillingEntity(BillingEntity billingEntity) {
        this.billingEntity = billingEntity;
    }

    @JsonProperty("billing")
    @Transient
    private BillingEntity billingEntity;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_has_event",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_event")})
    private List<event> events = new ArrayList<>();


    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    public int getId_user() {
        return id_user;

    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
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

    public boolean isEnable() {
        return enable;
    }

    public List<event> getEvents() {
        return events;
    }

    public void setEvents(List<event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserEntity{");
        sb.append("id_user=").append(id_user);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", emergencyContact='").append(emergencyContact).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", enable=").append(enable);
        sb.append(", updatedDate='").append(updatedDate).append('\'');
        sb.append(", createdDate='").append(createdDate).append('\'');
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
