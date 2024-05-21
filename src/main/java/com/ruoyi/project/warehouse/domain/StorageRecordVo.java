package com.ruoyi.project.warehouse.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StorageRecordVo {

    private String recordDetailId;

    private String warehouseId;

    /**
     * 库别（仓库编码）
     */
    private String warehouseCode;

    private String warehouseName;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 物料编码
     */
    private String itemCode;

    /**
     * 物料名称
     */
    private String itemName;

    /**
     * 物料批次
     */
    private String batchNo;

    /**
     * 单位
     */
    private String unit;

    /**
     * 规格型号
     */
    private String spec;

    /**
     * 类型（入库、出库、报损）
     */
    private Integer type;

    /**
     * 滚算前单价
     */
    private BigDecimal beforeRollingPrice;

    /**
     * 滚算前库存数
     */
    private BigDecimal beforeRollingQuantity;

    /**
     * 发生时价格
     */
    private BigDecimal occPrice;

    /**
     * 发生时数量
     */
    private BigDecimal occQuantity;

    /**
     * 发生时总价
     */
    private BigDecimal occTotalPrice;

    /**
     * 滚算后单价
     */
    private BigDecimal afterRollingPrice;

    /**
     * 业务编号
     */
    private String businessNo;

    /**
     * 通知ID
     */
    private String noticeId;

    /**
     * 发生时间（出/入库选择时间）
     */
    private Date occTime;

    /**
     * 单据类型(入库、退料、领料）
     */
    private Integer billType;

    private String remark;

}
