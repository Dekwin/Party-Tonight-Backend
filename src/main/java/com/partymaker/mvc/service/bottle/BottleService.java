package com.partymaker.mvc.service.bottle;

import com.partymaker.mvc.model.whole.BottleEntity;

import java.util.List;

/**
 * Created by anton on 06/11/16.
 */
public interface BottleService {

    void save(BottleEntity bottleEntity);

    List<BottleEntity> findAllBottlesByEventAndUser(int id_user, String party_name);

    List<BottleEntity> findAllBottlesByEventID(int id_event);
}
