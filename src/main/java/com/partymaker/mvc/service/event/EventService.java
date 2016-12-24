package com.partymaker.mvc.service.event;

import com.partymaker.mvc.model.whole.event;

import java.util.List;

/**
 * Created by anton on 04/11/16.
 */
public interface EventService {

    event findById(int id);

    event findByHash(String timeHash);

    List<event> findAll();

    List<event> findAllByUserId(int id_user);

    List<event> findAllByCode(String code);

    void delete(event eventEntity);

    void save(event eventEntity);

    boolean isExist(String party_name);
}
