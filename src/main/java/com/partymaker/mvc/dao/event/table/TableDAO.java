package com.partymaker.mvc.dao.event.table;

import com.partymaker.mvc.model.whole.TableEntity;

import java.util.List;

/**
 * Created by anton on 07/11/16.
 */
public interface TableDAO {

    void save(TableEntity tableEntity);

    List<TableEntity> findAllByEventAndUser(int id_user, String party_name);
}
