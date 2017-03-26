package com.partymaker.mvc.model.business.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.partymaker.mvc.model.business.booking.BookedTable;

import javax.persistence.*;

@Entity
@Table(name = "ordered_table", schema = "partymaker2")
public class OrderedTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_table")
    private int id_table;

    @Column(name = "type", nullable = true, length = 45)
    private String type;

    @Column(name = "number")
    private int number;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_order")
    private OrderEntity order;

    public OrderedTable() {
    }

    public OrderedTable(OrderEntity order, BookedTable table) {
        this.order = order;
        this.number = table.getNumber();
    }

    public int getId_table() {
        return id_table;
    }

    public void setId_table(int id_table) {
        this.id_table = id_table;
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

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

}
