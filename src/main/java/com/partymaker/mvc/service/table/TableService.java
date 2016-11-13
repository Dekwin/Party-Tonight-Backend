package com.partymaker.mvc.service.table;

import com.partymaker.mvc.model.whole.TableEntity;

import java.util.List;

/**
 * Created by anton on 06/11/16.
 */
public interface TableService {
    void save(TableEntity tableEntity);

    List<TableEntity> findAllTablesByEventAndUser(int id_user, String party_name);
}
