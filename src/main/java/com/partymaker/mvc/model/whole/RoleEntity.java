package com.partymaker.mvc.model.whole;

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
@Table(name = "role", schema = "partymaker2", catalog = "")
public class RoleEntity implements Serializable {



    @Id
    @Column(name = "id_role", nullable = false)
    private int idRole;
    @Column(name = "user_role", nullable = true, length = 45)
    private String userRole;

    @JsonManagedReference(value="user-role")
    @OneToMany(mappedBy = "role",  fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<UserEntity> users = new ArrayList<>();

    public RoleEntity() {
    }

    public RoleEntity(int id, String userRole) {
        this.idRole = id;
        this.userRole = userRole;
    }


    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }


    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
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

        RoleEntity that = (RoleEntity) o;

        if (idRole != that.idRole) return false;
        if (userRole != null ? !userRole.equals(that.userRole) : that.userRole != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRole;
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RoleEntity{");
        sb.append("idRole=").append(idRole);
        sb.append(", userRole='").append(userRole).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
