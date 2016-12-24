package com.partymaker.mvc.dao.event.photo;

import com.partymaker.mvc.model.whole.PhotoEntity;

import java.util.List;

/**
 * Created by anton
 */
public interface PhotoDAO {

    void save(PhotoEntity photoEntity);

    List<PhotoEntity> findAllByEventAndUser(int id_user, String party_name);

    List<PhotoEntity> findAllByEventId(int id_event);
}
