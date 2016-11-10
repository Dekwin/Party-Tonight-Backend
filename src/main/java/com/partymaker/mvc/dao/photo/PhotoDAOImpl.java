package com.partymaker.mvc.dao.photo;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.PhotoEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by anton on 07/11/16.
 */
@Repository("PhotoDAO")
public class PhotoDAOImpl extends AbstractDao<Integer, PhotoEntity> implements PhotoDAO {
    @Override
    public void save(PhotoEntity photoEntity) {
        persist(photoEntity);
    }
}
