package com.partymaker.mvc.dao.user;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.BillingEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anton on 03/11/16.
 */
@Repository("BillingDAO")
public class BillingDAOImpl extends AbstractDao<Integer, BillingEntity> implements BillingDAO<BillingEntity, String> {

    @Override
    public List<BillingEntity> findAll() {
        return null;
    }

    @Override
    public BillingEntity getBillingByCard(String card) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("card_number", card));
        return (BillingEntity) criteria.uniqueResult();
    }

    @Override
    public void save(BillingEntity billing) {
        persist(billing);
    }

    @Override
    public void detele(BillingEntity billing) {
            delete(billing);
    }
}
