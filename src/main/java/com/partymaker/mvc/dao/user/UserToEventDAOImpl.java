package com.partymaker.mvc.dao.user;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.UserToEvent;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userToEventDao")
public class UserToEventDAOImpl extends AbstractDao<Integer, UserToEvent> implements UserToEventDAO {

    @Override
    public int getAllEventIdByUserId(int id) {
        return 0;
    }

    @Override
    public int getUserIdByEventId(int id) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id_event", id));

        return ((UserToEvent) criteria.uniqueResult()).getUserId();
    }
}
