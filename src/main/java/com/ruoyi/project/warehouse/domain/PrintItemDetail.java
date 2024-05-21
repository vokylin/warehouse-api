package com.ruoyi.project.warehouse.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PrintItemDetail {

    /**
     * 物料编码
     */
    private String itemCode;

    /**
     * 物料名称
     */
    private String itemName;

    /**
     * 批次
     */
    private String batchNo;

    /**
     * 单位
     */
    private String unit;

    /**
     * 规格
     */
    private String model;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 付款条件
     */
    private String paymentCondition;

    /**
     * 采购单号
     */
    private String purchaseOrderNumber;

    /**
     * 类别
     */
    private String category;

    /**
     * 用途
     */
    private String useDesc;

    private String recordDetailId;

}
