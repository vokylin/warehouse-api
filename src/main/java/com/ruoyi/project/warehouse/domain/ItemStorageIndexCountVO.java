package com.ruoyi.project.warehouse.domain;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemStorageIndexCountVO {

    /**
     * 物料编码
     */
    private String itemCode;

    /**
     * 物料名称
     */
    private String itemName;

    /**
     * 单位
     */
    private String unit;

    /**
     * 规格型号
     */
    private String spec;

    /**
     * 总数量
     */
    private BigDecimal quantity;

    private BigDecimal upQuality;

    private BigDecimal downQuality;

    private Integer limitedStatus;
}
