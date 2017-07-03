package com.partymaker.mvc.model.business.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.partymaker.mvc.model.business.order.OrderedTable;

public class BookedTable {

    @JsonProperty("type")
    private String type;

    @JsonProperty("number")
    private int number;

    @JsonProperty("price")
    private double price;

    public BookedTable(String type, int number, double price) {
        this.type = type;
        this.number = number;
        this.price = price;
    }

    public BookedTable(OrderedTable orderedTable) {
        this.type = orderedTable.getType();
        this.number = orderedTable.getNumber();
        this.price = orderedTable.getPrice();
    }

    public BookedTable() {
    }

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
