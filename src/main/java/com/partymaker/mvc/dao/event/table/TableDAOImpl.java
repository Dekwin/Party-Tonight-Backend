package com.partymaker.mvc.dao.event.table;

import com.partymaker.mvc.dao.AbstractDao;
import com.partymaker.mvc.model.whole.BottleEntity;
import com.partymaker.mvc.model.whole.TableEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by anton on 06/11/16.
 */
@Repository("TableDAO")
public class TableDAOImpl extends AbstractDao<Integer,TableEntity> implements TableDAO{

    public void save(TableEntity eventEntity) {
        persist(eventEntity);
    }
}
