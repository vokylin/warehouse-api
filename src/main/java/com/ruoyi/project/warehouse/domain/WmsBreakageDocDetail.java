package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 报损物料明细对象 wms_breakage_doc_detail
 *
 * @author Rem
 * @date 2023-05-20
 */
@Data
public class WmsBreakageDocDetail {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 报损单号
     */
    @Excel(name = "报损单号")
    private String breakageDocId;

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
     * 单位
     */
    @Excel(name = "单位")
    private String unit;

    /**
     * 单价
     */
    @Excel(name = "单价")
    private BigDecimal price;

    private String spec;

    private BigDecimal totalQuantity;

    /**
     * 不良原因
     */
    private String badCauses;

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
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productDate;

    /**
     * 过期日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "过期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireDate;

    private String locationName;

    private String warehouseName;


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

}
