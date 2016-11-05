package com.partymaker.mvc.service.event;

import com.partymaker.mvc.model.whole.EventEntity;

import java.util.List;

/**
 * Created by anton on 04/11/16.
 */
public interface EventService {

    EventEntity findById(int id);

    List<EventEntity> findAll();

    void delete(EventEntity eventEntity);

    void save(EventEntity eventEntity);
}
