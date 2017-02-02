package com.partymaker.mvc.service.photo;

import com.partymaker.mvc.model.whole.PhotoEntity;

import java.util.List;

/**
 * Created by anton on 07/11/16.
 */
public interface PhotoService {

    void save(PhotoEntity photoEntity);

    List<PhotoEntity> findAllPhotosByEventAndUser(int id_user, String party_name);

    List<PhotoEntity> findAllPhotosByEventId(int id_event);
}
