package com.partymaker.mvc.model.admin;

import javax.persistence.*;

/**
 * Created by anton on 04.03.17.
 */
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @Column(name = "id_admin")
    private int id_admin;

    @Column(name = "account_email")
    private String accountEmail;

    @Column(name = "email_user_name")
    private String emailUserName;

    @Column(name = "email_user_password")
    private String emailUserPassword;


    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getEmailUserName() {
        return emailUserName;
    }

    public void setEmailUserName(String emailUserName) {
        this.emailUserName = emailUserName;
    }

    public String getEmailUserPassword() {
        return emailUserPassword;
    }

    public void setEmailUserPassword(String emailUserPassword) {
        this.emailUserPassword = emailUserPassword;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Admin admin = (Admin) o;

        if (id_admin != admin.id_admin) return false;
        if (accountEmail != null ? !accountEmail.equals(admin.accountEmail) : admin.accountEmail != null) return false;
        if (emailUserName != null ? !emailUserName.equals(admin.emailUserName) : admin.emailUserName != null)
            return false;
        return emailUserPassword != null ? emailUserPassword.equals(admin.emailUserPassword) : admin.emailUserPassword == null;
    }

    @Override
    public int hashCode() {
        int result = id_admin;
        result = 31 * result + (accountEmail != null ? accountEmail.hashCode() : 0);
        result = 31 * result + (emailUserName != null ? emailUserName.hashCode() : 0);
        result = 31 * result + (emailUserPassword != null ? emailUserPassword.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Admin{");
        sb.append("id_admin=").append(id_admin);
        sb.append(", accountEmail='").append(accountEmail).append('\'');
        sb.append(", emailUserName='").append(emailUserName).append('\'');
        sb.append(", emailUserPassword='").append(emailUserPassword).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
