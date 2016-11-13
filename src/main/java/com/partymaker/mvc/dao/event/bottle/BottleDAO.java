package com.partymaker.mvc.dao.event.bottle;

import com.partymaker.mvc.model.whole.BottleEntity;
import com.partymaker.mvc.model.whole.PhotoEntity;

import java.util.List;

/**
 * Created by anton on 07/11/16.
 */
public interface BottleDAO {
    void save(BottleEntity bottleEntity);

    List<BottleEntity> findAllByEventAndUser(int id_user, String party_name);
}
