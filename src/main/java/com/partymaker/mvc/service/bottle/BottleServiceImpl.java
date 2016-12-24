package com.partymaker.mvc.service.bottle;

import com.partymaker.mvc.dao.event.bottle.BottleDAO;
import com.partymaker.mvc.model.whole.BottleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by anton on 06/11/16.
 */
@Transactional
@Service
public class BottleServiceImpl implements BottleService {

    @Autowired
    private BottleDAO bottleDAO;

    @Override
    public void save(BottleEntity bottleEntity) {
        bottleDAO.save(bottleEntity);
    }

    @Override
    public List<BottleEntity> findAllBottlesByEventAndUser(int id_user, String party_name) {
        return bottleDAO.findAllByEventAndUser(id_user, party_name);
    }

    @Override
    public List<BottleEntity> findAllBottlesByEventID(int id_event) {
        return bottleDAO.findAllByEventId(id_event);
    }
}
