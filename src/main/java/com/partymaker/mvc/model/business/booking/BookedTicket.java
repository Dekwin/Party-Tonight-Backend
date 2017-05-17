package com.partymaker.mvc.model.business.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.partymaker.mvc.model.business.order.OrderedTicket;

public class BookedTicket {

    @JsonProperty("type")
    String type;

    @JsonProperty("price")
    double price;

    public BookedTicket(OrderedTicket ticket) {
        this.type = ticket.getType();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
