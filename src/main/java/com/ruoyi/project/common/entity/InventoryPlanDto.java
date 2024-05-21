package com.ruoyi.project.common.entity;

import com.ruoyi.project.warehouse.domain.WmsInventoryDetail;
import com.ruoyi.project.warehouse.domain.WmsInventoryPlan;
import lombok.Data;

@Data
public class InventoryPlanDto {

    /**
     * 盘点计划
     */
    private WmsInventoryPlan inventoryPlan;

    /**
     * 盘点计划明细
     */
    private WmsInventoryDetail inventoryPlanDetail;

    public InventoryPlanDto(WmsInventoryPlan inventoryPlan, WmsInventoryDetail inventoryPlanDetail) {
        this.inventoryPlan = inventoryPlan;
        this.inventoryPlanDetail = inventoryPlanDetail;
    }
}
