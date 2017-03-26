package com.partymaker.mvc.model.business.paypal;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by anton on 06.02.17.
 */
public class Payer {

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("funding_instruments")
    private List<CreditCard> fundingInstruments;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<CreditCard> getFundingInstruments() {
        return fundingInstruments;
    }

    public void setFundingInstruments(List<CreditCard> fundingInstruments) {
        this.fundingInstruments = fundingInstruments;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Payer{");
        sb.append("paymentMethod='").append(paymentMethod).append('\'');
        sb.append(", fundingInstruments=").append(fundingInstruments);
        sb.append('}');
        return sb.toString();
    }
}
