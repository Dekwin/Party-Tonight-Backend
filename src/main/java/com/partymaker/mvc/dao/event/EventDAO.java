package com.partymaker.mvc.dao.event;


import java.util.List;

/**
 * Created by anton on 04/11/16.
 */
public interface EventDAO<T> {
    T getByID(int id);

    T getByCode(String time);

    List<T> getAll();

    List<T> getAllByUserId(int id_user);

    List<T> getAllCode(String code);

    void save(T eventEntity);

    void delete(T eventEntity);
}
