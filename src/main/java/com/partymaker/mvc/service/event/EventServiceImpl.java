package com.partymaker.mvc.service.event;


import com.partymaker.mvc.dao.event.EventDAO;
import com.partymaker.mvc.dao.user.UserDao;
import com.partymaker.mvc.model.whole.EventEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by anton on 04/11/16.
 */
@Transactional
@Service("eventService")
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDAO eventDAO;


    @Override
    public EventEntity findById(int id) {
        return (EventEntity) eventDAO.getByID(id);
    }

    @Override
    public List<EventEntity> findAll() {
        return eventDAO.getAll();
    }

    @Override
    public void delete(EventEntity eventEntity) {
        eventDAO.delete(eventEntity);
    }

    @Override
    public void save(EventEntity eventEntity) {
        eventDAO.save(eventEntity);
    }
}
