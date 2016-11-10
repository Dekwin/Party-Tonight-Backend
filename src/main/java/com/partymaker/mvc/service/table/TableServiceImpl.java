package com.partymaker.mvc.service.table;

import com.partymaker.mvc.dao.event.table.TableDAO;
import com.partymaker.mvc.model.whole.TableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
