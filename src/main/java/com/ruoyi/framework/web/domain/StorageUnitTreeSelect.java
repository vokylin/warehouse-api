package com.ruoyi.framework.web.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.StorageUnitTypeEnums;
import com.ruoyi.project.system.domain.StorageUnit;
import com.ruoyi.project.system.domain.Warehouse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author ruoyi
 */
@Data
public class StorageUnitTreeSelect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private String id;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 存储单元类型
     */
    private Integer storageUnitType;

    /**
     * 是否最小存储单元
     */
    private Integer isSmallestStorageUnit;

    /**
     * 禁用
     */
    private boolean disabled;


    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<StorageUnitTreeSelect> children;

    public StorageUnitTreeSelect() {

    }

    public StorageUnitTreeSelect(StorageUnit storageUnit) {
        this.id = storageUnit.getId();
        this.label = storageUnit.getName();
        this.isSmallestStorageUnit = storageUnit.getIsSmallestStorageUnit();
        this.storageUnitType = storageUnit.getStorageUnitType();
        this.children = storageUnit.getChildren().stream().map(StorageUnitTreeSelect::new).collect(Collectors.toList());
    }


    public StorageUnitTreeSelect(Warehouse warehouse) {
        this.id = warehouse.getId();
        this.label = warehouse.getName();
        this.storageUnitType = StorageUnitTypeEnums.WAREHOUSE.getCode();
        this.isSmallestStorageUnit = Constants.NO;
    }


}
