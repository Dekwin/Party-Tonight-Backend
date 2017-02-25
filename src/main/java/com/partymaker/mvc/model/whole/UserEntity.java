package com.partymaker.mvc.model.whole;

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

    @Column(name = "birthday", nullable = true, length = 45)
    private String birthday;

    @Column(name = "address", nullable = true, length = 45)
    private String address;

    @Column(name = "user_role", nullable = true, length = 45)
    private String user_role;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_role")
    private RoleEntity role;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_billing", nullable = false)
    private BillingEntity billing;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_has_event",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_event")})
    private List<event> events = new ArrayList<>();


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

    public BillingEntity getBilling() {
        return billing;
    }

    public void setBilling(BillingEntity billing) {
        this.billing = billing;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id_user != that.id_user) return false;
        if (enable != that.enable) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (emergencyContact != null ? !emergencyContact.equals(that.emergencyContact) : that.emergencyContact != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (updatedDate != null ? !updatedDate.equals(that.updatedDate) : that.updatedDate != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (billing != null ? !billing.equals(that.billing) : that.billing != null) return false;
        return events != null ? events.equals(that.events) : that.events == null;
    }

    @Override
    public int hashCode() {
        int result = id_user;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (emergencyContact != null ? emergencyContact.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (enable ? 1 : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (billing != null ? billing.hashCode() : 0);
        result = 31 * result + (events != null ? events.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserEntity{");
        sb.append("id_user=").append(id_user);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", emergencyContact='").append(emergencyContact).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", enable=").append(enable);
        sb.append(", updatedDate='").append(updatedDate).append('\'');
        sb.append(", createdDate='").append(createdDate).append('\'');
        sb.append(", birthday='").append(birthday).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", role=").append(role);
        sb.append(", billing=").append(billing);
//        sb.append(", events=").append(events);
        sb.append('}');
        return sb.toString();
    }
}
