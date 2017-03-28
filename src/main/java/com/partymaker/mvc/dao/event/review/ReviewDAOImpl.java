package com.partymaker.mvc.dao.event.review;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.ReviewEntity;
import org.springframework.stereotype.Repository;

@Repository("ReviewDAO")
public class ReviewDAOImpl extends AbstractDao<Integer, ReviewEntity> implements ReviewDAO {

    @Override
    public void save(ReviewEntity reviewEntity) {
        persist(reviewEntity);
    }
}
