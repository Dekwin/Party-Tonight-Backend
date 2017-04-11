package com.partymaker.mvc.dao.event;


import com.partymaker.mvc.model.DataResponse;
import com.partymaker.mvc.model.whole.event;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Created by anton on 04/11/16.
 */
public interface EventDAO<T> {
    T getByID(int id);

    T getByCode(String time);

    List<T> getAll();

    List<T> getAll(int offset, int limit);

    DataResponse<event> getAll(int offset, int limit, Order order);

    List<T> getAllByUserId(int id_user);

    List<T> getAllCode(String code);

    void save(T eventEntity);

    void delete(T eventEntity);

    T getEventByName(String name);
}
