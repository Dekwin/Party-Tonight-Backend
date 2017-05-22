package com.partymaker.mvc.model.business.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.partymaker.mvc.model.business.order.OrderedBottle;

public class BookedBottle {

    @JsonProperty("title")
    private String title;

    @JsonProperty("amount")
    private int amount;

    @JsonProperty("price")
    private double price;

    public BookedBottle() {
    }

    public BookedBottle(OrderedBottle bottle) {
        this.amount = bottle.getAmount();
        this.title = bottle.getTitle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
