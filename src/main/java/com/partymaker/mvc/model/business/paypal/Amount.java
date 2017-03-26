package com.partymaker.mvc.model.business.paypal;


import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by anton on 06.02.17.
 */
public class Amount {

    @JsonProperty("total")
    private String total;

    @JsonProperty("currency")
    private String currency;

    public Amount() {
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Amount{");
        sb.append("total='").append(total).append('\'');
        sb.append(", currency='").append(currency).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
