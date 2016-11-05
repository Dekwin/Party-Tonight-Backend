package com.partymaker.mvc.service.billing;

import com.partymaker.mvc.dao.user.BillingDAO;
import com.partymaker.mvc.model.whole.BillingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Created by anton on 03/11/16.
 */
@Service("BillingService")
@Transactional
public class BillingService {

    @Autowired
    private BillingDAO billingDAO;

    public BillingEntity findByCard(String card) {
        return (BillingEntity) billingDAO.getBillingByCard(card);
    }

    public boolean isExist(BillingEntity entity){
        return Objects.nonNull(billingDAO.getBillingByCard(entity.getCard_number()));
    }
    public List<BillingEntity> findAll() {
        return billingDAO.findAll();
    }

    public void saveBilling(BillingEntity billingEntity) {
        billingDAO.save(billingEntity);
    }

    public void deleteBilling(BillingEntity billingEntity) {
        billingDAO.detele(billingEntity);
    }
}
