package com.partymaker.mvc.model.business;

/**
 * Created by anton on 13/11/16.
 */
public class StatementTotal {

    protected String withdrawn = "0";
    protected String ticketsSales = "0";
    protected String bottleSales = "0";
    protected String tableSales = "0";
    protected String refunds = "0";

    public StatementTotal() {
    }

    public String getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(String withdrawn) {
        this.withdrawn = withdrawn;
    }

    public String getTicketsSales() {
        return ticketsSales;
    }

    public void setTicketsSales(String ticketsSales) {
        this.ticketsSales = ticketsSales;
    }

    public String getBottleSales() {
        return bottleSales;
    }

    public void setBottleSales(String bottleSales) {
        this.bottleSales = bottleSales;
    }

    public String getTableSales() {
        return tableSales;
    }

    public void setTableSales(String tableSales) {
        this.tableSales = tableSales;
    }

    public String getRefunds() {
        return refunds;
    }

    public void setRefunds(String refunds) {
        this.refunds = refunds;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StatementTotal{");
        sb.append("withdrawn='").append(withdrawn).append('\'');
        sb.append(", ticketsSales='").append(ticketsSales).append('\'');
        sb.append(", bottleSales='").append(bottleSales).append('\'');
        sb.append(", tableSales='").append(tableSales).append('\'');
        sb.append(", refunds='").append(refunds).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
