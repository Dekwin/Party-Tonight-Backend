package com.partymaker.mvc.dao.user;

public interface UserToEventDAO {

    int getAllEventIdByUserId(int id);

    int getUserIdByEventId(int id);
}
