package com.partymaker.mvc.model.business;

import com.partymaker.mvc.model.whole.BottleEntity;
import com.partymaker.mvc.model.whole.TableEntity;
import com.partymaker.mvc.model.whole.TicketEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by anton on 19/11/16.
 */

public class Book {

    @JsonProperty("partyName")
    private String partyName;

    @JsonProperty("tickets")
    private int tickets;

    @JsonProperty("tables")
    private List<TableEntity> tables;

    @JsonProperty("bottles")
    private List<BottleEntity> bottles;

    public List<TableEntity> getTables() {
        return tables;
    }

    public void setTables(List<TableEntity> tables) {
        this.tables = tables;
    }

    public List<BottleEntity> getBottles() {
        return bottles;
    }

    public void setBottles(List<BottleEntity> bottles) {
        this.bottles = bottles;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public double getTotalSum(TicketEntity ticketEntity) {
        double sum = 0;

        for (TableEntity t : tables) {
            sum += Integer.parseInt(t.getBooked()) * Double.parseDouble(t.getPrice());
        }

        for (BottleEntity b : bottles) {
            sum += Integer.parseInt(b.getBooked()) * Double.parseDouble(b.getPrice());
        }

        sum += Double.parseDouble(ticketEntity.getPrice()) * tickets;

        return sum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("partyName='").append(partyName).append('\'');
        sb.append(", tickets=").append(tickets);
        sb.append(", tables=").append(tables);
        sb.append(", bottles=").append(bottles);
        sb.append('}');
        return sb.toString();
    }
}
