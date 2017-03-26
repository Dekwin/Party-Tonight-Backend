package com.partymaker.mvc.model.business.booking;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookedTable {

    @JsonProperty("type")
    private String type;

    @JsonProperty("number")
    private int number;

    @JsonProperty("price")
    private double price;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
