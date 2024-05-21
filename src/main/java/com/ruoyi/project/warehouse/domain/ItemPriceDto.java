package com.ruoyi.project.warehouse.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemPriceDto {

    /**
     * 物料编码
     */
    private String itemCode;

    /**
     * 实时单价
     */
    private BigDecimal actualPrice;

    /**
     * 库存金额
     */
    private BigDecimal storagePrice;

    /**
     * 滚算后价格
     */
    private BigDecimal afterRollingPrice;

    /**
     * 总数量
     */
    private BigDecimal totalQuantity;

    private boolean isNullAdd = false;

    private boolean isLossAll = false;

    public ItemPriceDto(String itemCode, BigDecimal actualPrice, BigDecimal afterRollingPrice, BigDecimal storagePrice, BigDecimal totalQuantity) {
        this.itemCode = itemCode;
        if (null == actualPrice) {
            this.actualPrice = BigDecimal.ZERO;
        } else {
            this.actualPrice = actualPrice;
        }
        this.afterRollingPrice = afterRollingPrice;
        this.storagePrice = storagePrice;
        this.totalQuantity = totalQuantity;
    }

    public ItemPriceDto(String itemCode) {
        this.itemCode = itemCode;
        this.actualPrice = BigDecimal.ZERO;
        this.afterRollingPrice = BigDecimal.ZERO;
        this.storagePrice = BigDecimal.ZERO;
        this.totalQuantity = BigDecimal.ZERO;
        this.isNullAdd = true;
    }
}
