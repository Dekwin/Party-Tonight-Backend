package com.partymaker.mvc.service.event;

import com.partymaker.mvc.model.DataResponse;
import com.partymaker.mvc.model.business.DoorRevenue;
import com.partymaker.mvc.model.business.StatementTotal;
import com.partymaker.mvc.model.business.StatementWrapper;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.model.whole.event;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Created by anton on 04/11/16.
 */
public interface EventService {

    event findById(int id);

    event findByHash(String timeHash);

    List<event> findAll();

    List<event> findAll(int offset,int limit);

    DataResponse findAll(int offset, int limit, Order order);

    List<event> findAllByUserId(int id_user);

    List<event> findAllByCode(String code);

    void delete(event eventEntity);

    void save(event eventEntity, String user_email);

    boolean isExist(String party_name);

    event findByName(String name);

    void validation(event event);

    List<StatementWrapper> getAllTotals(int offset, int limit);

    StatementTotal getTotal(String partyName);

    StatementTotal getTotal(String partyName,UserEntity user);

    DoorRevenue getRevenue(String partyName,UserEntity user);
}
