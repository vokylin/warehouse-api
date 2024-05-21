package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 盘点详情对象 wms_inventory_detail
 *
 * @author Rem
 * @date 2023-05-21
 */
@Data
public class WmsInventoryDetail {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 盘点计划ID
     */
    @Excel(name = "盘点计划ID")
    private String inventoryId;

    /**
     * 库存详情ID
     */
    private String storageDetailId;

    /**
     * 仓库ID
     */
    @Excel(name = "仓库ID")
    private String warehouseId;

    /**
     * 货位ID
     */
    @Excel(name = "货位ID")
    private String locationId;

    /**
     * 物料编码
     */
    @Excel(name = "物料编码")
    private String itemCode;

    /**
     * 物料名称
     */
    @Excel(name = "物料名称")
    private String itemName;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;

    private String spec;

    /**
     * 单价
     */
    @Excel(name = "单价")
    private BigDecimal price;

    /**
     * 批次
     */
    @Excel(name = "批次")
    private String batchNo;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /**
     * 盘点数量
     */
    @Excel(name = "盘点数量")
    private BigDecimal inventoryQuantity;

    /**
     * 差异数量
     */
    @Excel(name = "差异数量")
    private BigDecimal diffQuantity;

    /**
     * 库存状态
     */
    @Excel(name = "库存状态")
    private Integer storageStatus;

    /**
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productDate;

    /**
     * 是否登记   0：否 1：是
     */
    @Excel(name = "是否登记   0：否 1：是")
    private Integer isSave;

    /**
     * 是否增补   0：否 1：是
     */
    @Excel(name = "是否增补   0：否 1：是")
    private Integer isAdd;

    /**
     * 差异分析
     */
    @Excel(name = "差异分析")
    private String diffAnalysis;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;


    /**
     * 更新者
     */
    private Long updateBy;

    private String locationName;

    private String warehouseName;

    private String itemCodeOrName;
}
