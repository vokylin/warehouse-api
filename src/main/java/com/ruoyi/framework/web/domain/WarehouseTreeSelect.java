package com.ruoyi.framework.web.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.TreeDataEnums;
import com.ruoyi.project.system.domain.Company;
import com.ruoyi.project.system.domain.Warehouse;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author ruoyi
 */
@Data
public class WarehouseTreeSelect implements Serializable {
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
     * 类型 0 公司 1 仓库
     */
    private int type;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<WarehouseTreeSelect> children;

    public WarehouseTreeSelect() {

    }

    public WarehouseTreeSelect(Company company) {
        this.id = company.getId();
        this.label = company.getName();
        this.type = TreeDataEnums.COMPANY.getValue();
        this.children = company.getChildren().stream().map(companyInfo -> {
            WarehouseTreeSelect warehouseTreeSelect = new WarehouseTreeSelect(companyInfo);
            List<Warehouse> warehouses = companyInfo.getWarehouses();
            if (CollectionUtils.isNotEmpty(warehouses)) {
                List<WarehouseTreeSelect> warehouseTreeSelects = warehouses.stream().map(warehouse -> {
                    WarehouseTreeSelect warehouseTreeSelectInfo = new WarehouseTreeSelect();
                    warehouseTreeSelectInfo.setId(warehouse.getId());
                    warehouseTreeSelectInfo.setLabel(warehouse.getName());
                    warehouseTreeSelectInfo.setType(TreeDataEnums.WAREHOUSE.getValue());
                    return warehouseTreeSelectInfo;
                }).collect(Collectors.toList());
                warehouseTreeSelect.getChildren().addAll(warehouseTreeSelects);
            }
            return warehouseTreeSelect;
        }).collect(Collectors.toList());
        // 处理总公司下的仓库
        if (company.getParentId().equals(Constants.ROOT_NODE)) {
            List<Warehouse> warehousesList = company.getWarehouses();
            if (CollectionUtils.isNotEmpty(warehousesList)) {
                List<WarehouseTreeSelect> warehouseTreeSelects = warehousesList.stream().map(warehouse -> {
                    WarehouseTreeSelect warehouseTreeSelectInfo = new WarehouseTreeSelect();
                    warehouseTreeSelectInfo.setId(warehouse.getId());
                    warehouseTreeSelectInfo.setLabel(warehouse.getName());
                    warehouseTreeSelectInfo.setType(TreeDataEnums.WAREHOUSE.getValue());
                    return warehouseTreeSelectInfo;
                }).collect(Collectors.toList());
                this.children.addAll(warehouseTreeSelects);
            }
        }
    }
}
