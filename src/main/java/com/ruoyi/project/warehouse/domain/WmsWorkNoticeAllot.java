package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作业通知明细分配对象 wms_work_notice_allot
 *
 * @author Rem
 * @date 2023-04-07
 */
@Data
public class WmsWorkNoticeAllot {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 作业通知ID
     */
    @Excel(name = "作业通知ID")
    private String workNoticeId;

    /**
     * 作业通知明细ID
     */
    @Excel(name = "作业通知明细ID")
    private String workNoticeDetailId;

    /**
     * 出/入库通知ID
     */
    private String noticeId;

    /**
     * 物料编码
     */
    @Excel(name = "物料编码")
    private String itemCode;

    private String itemName;

    /**
     * 批次
     */
    @Excel(name = "批次")
    private String batchNo;

    /**
     * 来源货位id
     */
    @Excel(name = "来源货位id")
    private String sourceLocationId;

    private String sourceLocationName;

    /**
     * 目标货位id
     */
    @Excel(name = "目标货位id")
    private String targetLocationId;

    private String targetLocationName;

    /**
     * 目标仓库ID
     */
    @Excel(name = "目标仓库ID")
    private String warehouseId;

    private String warehouseName;

    private String sourceWarehouseId;

    private String sourceWarehouseName;

    /**
     * 分配数量
     */
    @Excel(name = "分配数量")
    private BigDecimal quantity;

    /**
     * 作业状态
     */
    @Excel(name = "作业状态")
    private Integer status;

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
}
