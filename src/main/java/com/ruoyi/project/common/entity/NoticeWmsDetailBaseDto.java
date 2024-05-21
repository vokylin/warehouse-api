package com.ruoyi.project.common.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 通知仓库物料详情BaseDto
 *
 * @author gaomian
 * @since 2023-04-18
 */
@Data
public class NoticeWmsDetailBaseDto {

    /**
     * 物料 / 产品 编码
     */
    private String productCode;
    /**
     * 物料 / 产品 名称
     */
    private String productName;
    /**
     * 规格型号
     */
    private String spec;
    /**
     * 通知数量
     */
    private BigDecimal quantity;
    /**
     * 通知单位
     */
    private String unit;

    /**
     * 换算率
     */
    private BigDecimal conversionRate;

    /**
     * 业务数量
     */
    private BigDecimal businessQuantity;
    /**
     * 业务单位
     */
    private String businessUnit;
    /**
     * 业务单位所属类型
     */
    private String belongs;
    /**
     * 组合商品的父级商品编码 （组合商品使用）
     */
    private String parentProductCode;
}
