package com.partymaker.mvc.model.business.order;

import com.google.gson.annotations.SerializedName;

public class OrderWrapped {

    @SerializedName("user_id")
    private int userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("tickets_bought")
    private double ticketsSubtotal;

    @SerializedName("tables_bought")
    private double tablesSubtotal;

    @SerializedName("bottles_bought")
    private double bottlesSubtotal;

    @SerializedName("total_spent")
    private double subtotal;

    public OrderWrapped(int userId, String userName,
                        double ticketsSubtotal, double tablesSubtotal,
                        double bottlesSubtotal, double subtotal) {
        this.userId = userId;
        this.userName = userName;
        this.ticketsSubtotal = ticketsSubtotal;
        this.tablesSubtotal = tablesSubtotal;
        this.bottlesSubtotal = bottlesSubtotal;
        this.subtotal = subtotal;
    }

    public OrderWrapped() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getTicketsSubtotal() {
        return ticketsSubtotal;
    }

    public void setTicketsSubtotal(double ticketsSubtotal) {
        this.ticketsSubtotal = ticketsSubtotal;
    }

    public double getTablesSubtotal() {
        return tablesSubtotal;
    }

    public void setTablesSubtotal(double tablesSubtotal) {
        this.tablesSubtotal = tablesSubtotal;
    }

    public double getBottlesSubtotal() {
        return bottlesSubtotal;
    }

    public void setBottlesSubtotal(double bottlesSubtotal) {
        this.bottlesSubtotal = bottlesSubtotal;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
