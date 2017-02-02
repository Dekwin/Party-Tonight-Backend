package com.partymaker.mvc.service.table;

import com.partymaker.mvc.dao.event.table.TableDAO;
import com.partymaker.mvc.model.whole.TableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by anton on 06/11/16.
 */
@Transactional
@Service("TableService")
public class TableServiceImpl implements TableService {

    @Autowired
    private TableDAO tableDAO;

    @Override
    public void save(TableEntity tableEntity) {
        tableDAO.save(tableEntity);
    }

    @Override
    public List<TableEntity> findAllTablesByEventAndUser(int id_user, String party_name) {
        return tableDAO.findAllByEventAndUser(id_user, party_name);
    }

    @Override
    public List<TableEntity> findAllTablesByEventId(int id_event) {
        return tableDAO.findAllByEventId(id_event);
    }
}
