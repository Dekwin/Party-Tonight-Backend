package com.partymaker.mvc.model.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.partymaker.mvc.model.business.paypal.CreditCard;
import com.partymaker.mvc.model.business.paypal.Transaction;

import java.util.List;

/**
 * Created by anton on 07.02.17.
 */
public class PMPayPalRequest {

    @JsonProperty("credit_card")
    private CreditCard creditCard;

    @JsonProperty("transactions")
    private List<Transaction> transactions;

    public PMPayPalRequest() {
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PMPayPalRequest{");
        sb.append("creditCard=").append(creditCard);
        sb.append(", transactions=").append(transactions);
        sb.append('}');
        return sb.toString();
    }
}