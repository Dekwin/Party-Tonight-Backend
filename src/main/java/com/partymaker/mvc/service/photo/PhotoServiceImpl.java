package com.partymaker.mvc.service.photo;

import com.partymaker.mvc.dao.event.photo.PhotoDAO;
import com.partymaker.mvc.model.whole.PhotoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by anton on 07/11/16.
 */
@Transactional
@Service("PhotoService")
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoDAO photoDAO;

    @Override
    public void save(PhotoEntity photoEntity) {
        photoDAO.save(photoEntity);
    }

    @Override
    public List<PhotoEntity> findAllPhotosByEventAndUser(int id_user, String party_name) {
        return photoDAO.findAllByEventAndUser(id_user, party_name);
    }

    @Override
    public List<PhotoEntity> findAllPhotosByEventId(int id_event) {
        return photoDAO.findAllByEventId(id_event);
    }
}
