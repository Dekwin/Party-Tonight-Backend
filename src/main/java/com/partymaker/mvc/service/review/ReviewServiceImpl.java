package com.partymaker.mvc.service.review;

import com.partymaker.mvc.dao.event.review.ReviewDAO;
import com.partymaker.mvc.model.whole.ReviewEntity;
import com.partymaker.mvc.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Autowired
    private ReviewDAO dao;

    @Autowired
    private UserService userService;

    @Override
    public void save(ReviewEntity entity) {
        entity.setId_user(userService.getCurrentUser().getId_user());
        entity.setDate_created(getDateCreated());

        dao.save(entity);
    }

    private String getDateCreated() {
        Date date = new Date();
        return String.valueOf(dateFormat.format(date));
    }
}
