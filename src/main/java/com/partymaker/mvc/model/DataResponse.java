package com.partymaker.mvc.model;

import java.util.List;

/**
 * Created by igorkasyanenko on 11.04.17.
 */
public class DataResponse<T> {
    List<T> items;
    Long count;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public DataResponse(List<T> items, Long count) {
        this.items = items;
        this.count = count;
    }

    public DataResponse() {
    }
}
