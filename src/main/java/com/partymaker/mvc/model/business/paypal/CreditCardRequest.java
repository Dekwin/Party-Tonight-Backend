package com.partymaker.mvc.model.business.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by anton on 06.02.17.
 */
public class CreditCardRequest {

    @JsonProperty("intent")
    private String intent;

    @JsonProperty("payer")
    private Payer payer;

    @JsonProperty("transactions")
    private List<Transaction> transactions;

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreditCardRequest{");
        sb.append("intent='").append(intent).append('\'');
        sb.append(", payer=").append(payer);
        sb.append(", transactions=").append(transactions);
        sb.append('}');
        return sb.toString();
    }
}
