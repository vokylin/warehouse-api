package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作业通知明细对象 wms_work_notice_detail
 *
 * @author Rem
 * @date 2023-04-07
 */
@Data
public class WmsWorkNoticeDetail {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 作业通知id
     */
    @Excel(name = "作业通知id")
    private String workNoticeId;

    /**
     * 入库通知明细ID
     */
    private String noticeDetailId;

    /**
     * 入库通知分配明细ID
     */
    private String noticeItemDetailId;

    /**
     * 物料编码
     */
    @Excel(name = "物料编码")
    private String itemCode;

    private String itemName;

    private String unit;

    private String spec;

    /**
     * 物料批次
     */
    @Excel(name = "物料批次")
    private String batchNo;

    /**
     * 来源货位id
     */
    @Excel(name = "来源货位id")
    private String sourceLocationId;

    private String sourceLocationName;

    private String sourceWarehouseId;

    private String sourceWarehouseName;

    /**
     * 目标货位id
     */
    private String locationId;

    /**
     * 通知数量
     */
    @Excel(name = "通知数量")
    private BigDecimal quantity;

    /**
     * 已分配数量
     */
    @Excel(name = "已分配数量")
    private BigDecimal allotQuantity;

    /**
     * 当前分配数量
     */
    private BigDecimal currentAllocatedQuantity;

    /**
     * 质检扣除数量
     */
    @Excel(name = "质检扣除数量")
    private BigDecimal examineLessNum;
    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String itemCodeOrName;
}
