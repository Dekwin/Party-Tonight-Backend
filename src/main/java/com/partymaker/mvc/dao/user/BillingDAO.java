package com.partymaker.mvc.dao.user;

import java.util.List;

/**
 * Created by anton on 03/11/16.
 */
public interface BillingDAO<T, V> {

    List<T> findAll();

    T getBillingByCard(String card);


    void save(T billing);

    void detele(T billing);

}
