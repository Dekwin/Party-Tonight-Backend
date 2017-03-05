package com.partymaker.mvc.dao.admin;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.admin.Admin;
import org.springframework.stereotype.Repository;

/**
 * Created by anton on 05.03.17.
 */
@Repository
public class AdminDao extends AbstractDao<Integer, Admin> {

    public Admin getAdminEntity(int id) {
        Admin a = getByKey(id);
        return a;
    }

    public void createAdmin(Admin a) {
        persist(a);
    }
}
