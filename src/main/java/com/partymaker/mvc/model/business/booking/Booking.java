package com.partymaker.mvc.model.business.booking;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Booking {

    @JsonProperty("bottles")
    private List<BookedBottle> bottles;

    @JsonProperty("table")
    private BookedTable table;

    @JsonProperty("ticket")
    private BookedTicket ticket;

    public List<BookedBottle> getBottles() {
        return bottles;
    }

    public void setBottles(List<BookedBottle> bottles) {
        this.bottles = bottles;
    }

    public BookedTable getTable() {
        return table;
    }

    public void setTable(BookedTable table) {
        this.table = table;
    }

    public BookedTicket getTicket() {
        return ticket;
    }

    public void setTicket(BookedTicket ticket) {
        this.ticket = ticket;
    }
}
