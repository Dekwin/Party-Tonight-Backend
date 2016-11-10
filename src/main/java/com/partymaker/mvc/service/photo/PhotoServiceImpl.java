package com.partymaker.mvc.service.photo;

import com.partymaker.mvc.dao.photo.PhotoDAO;
import com.partymaker.mvc.model.whole.PhotoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
