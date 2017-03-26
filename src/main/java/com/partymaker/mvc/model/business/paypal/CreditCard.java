package com.partymaker.mvc.model.business.paypal;


import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Created by anton on 06.02.17.
 */
public class CreditCard {

    @JsonProperty("number")
    private String number;

    @JsonProperty("type")
    private String type;

    @JsonProperty("expire_month")
    private String expireMonth;

    @JsonProperty("expire_year")
    private String expireYear;

    @JsonProperty("cvv2")
    private String cvv2;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpireMonth() {
        return expireMonth;
    }

    public void setExpireMonth(String expireMonth) {
        this.expireMonth = expireMonth;
    }

    public String getExpireYear() {
        return expireYear;
    }

    public void setExpireYear(String expireYear) {
        this.expireYear = expireYear;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreditCard{");
        sb.append("number='").append(number).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", expireMonth='").append(expireMonth).append('\'');
        sb.append(", expireYear='").append(expireYear).append('\'');
        sb.append(", cvv2='").append(cvv2).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
