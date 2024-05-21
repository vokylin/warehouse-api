package com.ruoyi.project.warehouse.mapper;


import com.ruoyi.project.warehouse.domain.OriginItemStorage;

import java.util.List;

public interface OriginItemStorageMapper {
    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    OriginItemStorage selectByPrimaryKey(String id);

    List<OriginItemStorage> selectAll();
}
