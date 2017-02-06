package com.partymaker.mvc.model.business.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by anton on 06.02.17.
 */
public class Transaction {

    @JsonProperty("amount")
    private Amount amount;

    @JsonProperty("description")
    private String description;

    public Transaction() {
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("amount=").append(amount);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
