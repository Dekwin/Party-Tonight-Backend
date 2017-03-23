package com.partymaker.mvc.model.whole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

public class BillingEntity implements Serializable {

    private int idBilling;

    private String billing_email;

    public BillingEntity() {
    }

    public BillingEntity(String billing_email) {
        this.billing_email = billing_email;
    }

    public int getIdBilling() {
        return idBilling;
    }

    public void setIdBilling(int idBilling) {
        this.idBilling = idBilling;
    }

    public String getBilling_email() {
        return billing_email;
    }

    public void setBillingEmail(String billingEmail) {
        this.billing_email = billingEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillingEntity that = (BillingEntity) o;

        if (idBilling != that.idBilling) return false;
        if (billing_email != null ? !billing_email.equals(that.billing_email) : that.billing_email != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idBilling;
        result = 31 * result + (billing_email != null ? billing_email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BillingEntity{");
        sb.append("idBilling=").append(idBilling);
        sb.append(", billing_email='").append(billing_email).append('\'');
        /*sb.append(", users=").append(users);*/
        sb.append('}');
        return sb.toString();
    }
}
