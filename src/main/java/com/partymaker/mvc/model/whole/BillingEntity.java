package com.partymaker.mvc.model.whole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by anton on 01/11/16.
 */
@Entity
@Table(name = "billing")
public class BillingEntity implements Serializable {

    @Id
    @Column(name = "id_billing")
    private int idBilling;

    @Column(name = "card_number")
    private String card_number;


    public BillingEntity() {
    }

    public BillingEntity(String cardNumber) {
        this.card_number = cardNumber;
    }

    public int getIdBilling() {
        return idBilling;
    }

    public void setIdBilling(int idBilling) {
        this.idBilling = idBilling;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillingEntity that = (BillingEntity) o;

        if (idBilling != that.idBilling) return false;
        if (card_number != null ? !card_number.equals(that.card_number) : that.card_number != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idBilling;
        result = 31 * result + (card_number != null ? card_number.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BillingEntity{");
        sb.append("idBilling=").append(idBilling);
        sb.append(", card_number='").append(card_number).append('\'');
        /*sb.append(", users=").append(users);*/
        sb.append('}');
        return sb.toString();
    }
}
