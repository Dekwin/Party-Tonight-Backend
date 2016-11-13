package com.partymaker.mvc.model.business;

/**
 * Created by anton on 13/11/16.
 */
public class DoorRevenue {

    private String revenue;

    public DoorRevenue() {
    }

    public DoorRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoorRevenue that = (DoorRevenue) o;

        return revenue != null ? revenue.equals(that.revenue) : that.revenue == null;

    }

    @Override
    public int hashCode() {
        return revenue != null ? revenue.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DoorRevenue{");
        sb.append("revenue='").append(revenue).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
