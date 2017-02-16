package com.partymaker.mvc.dao.business.confirmation;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.business.confirmation.EmailCredentials;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by anton on 14.02.17.
 */
@Repository
public class EmailCredentialsDAO extends AbstractDao<Integer, EmailCredentials> {

    public EmailCredentials getGredentials() {
        Query q = getSession().createSQLQuery("SELECT * from EmailCredentials");
        return (EmailCredentials) q.list().get(0);
    }
}
