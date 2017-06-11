package com.partymaker.mvc.model.business;

import com.google.gson.annotations.SerializedName;

public class StatementWrapper extends StatementTotal {

    @SerializedName("event_id")
    private int eventId = 0;

    @SerializedName("event_name")
    private String eventName = "";

    public StatementWrapper(int eventId, String eventName,
                            StatementTotal that) {
        this.eventId = eventId;
        this.eventName = eventName;

        this.withdrawn = that.getWithdrawn();
        this.ticketsSales = that.getTicketsSales();
        this.bottleSales = that.getBottleSales();
        this.tableSales = that.getTableSales();
        this.refunds = that.getRefunds();
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
