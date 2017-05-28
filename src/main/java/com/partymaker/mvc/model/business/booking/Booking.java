package com.partymaker.mvc.model.business.booking;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Booking {

    @JsonProperty("id_event")
    private int id_event;

    @JsonProperty("bottles")
    private List<BookedBottle> bottles = new ArrayList<>();

    @JsonProperty("table")
    private BookedTable table;

    @JsonProperty("ticket")
    private BookedTicket ticket;

    public static double getSubtotal(Booking booking) {
        double subtotal = 0;

        if (booking.getTable() != null) {
            subtotal += booking.getTable().getPrice();
        }

        for (BookedBottle b : booking.getBottles()) {
            subtotal += b.getAmount() * b.getPrice();
        }

        if (booking.getTicket() != null) {
            subtotal += booking.getTicket().getPrice();
        }

        return subtotal;
    }

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

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }
}
