package com.partymaker.mvc.service.event;


import com.partymaker.mvc.dao.event.EventDAO;
import com.partymaker.mvc.model.whole.event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by anton on 04/11/16.
 */
@Transactional
@Service("eventService")
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDAO eventDAO;


    @Override
    public event findById(int id) {
        return (event) eventDAO.getByID(id);
    }

    @Override
    public event findByHash(String timeHash) {
        return (event) eventDAO.getByCode(timeHash);
    }

    @Override
    public List<event> findAll() {
        return eventDAO.getAll();
    }

    @Override
    public void delete(event eventEntity) {
        eventDAO.delete(eventEntity);
    }

    @Override
    public void save(event eventEntity) {
        eventDAO.save(eventEntity);
    }

    @Override
    public boolean isExist(String timeHash) {
        return Objects.nonNull(eventDAO.getByCode(timeHash));
    }
}
