package com.ruoyi.project.common.entity;

import lombok.Data;

@Data
public class InventoryAuditDto {
    /**
     * 盘点任务ID
     */
    private String inventoryId;

    /**
     * 审核状态
     */
    private Integer status;

    /**
     * 审核人ID
     */
    private String reviewerId;

    /**
     * 审核人
     */
    private String reviewedBy;

}
