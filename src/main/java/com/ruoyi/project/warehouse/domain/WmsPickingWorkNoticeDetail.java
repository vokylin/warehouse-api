package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 拣货作业通知明细对象 wms_picking_work_notice_detail
 *
 * @author Rem
 * @date 2023-04-15
 */
@Data
public class WmsPickingWorkNoticeDetail {
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
     * 出库通知明细ID
     */
    @Excel(name = "出库通知明细ID")
    private String noticeDetailId;

    /**
     * 出库通知ID
     */
    private String deliveryNoticeId;

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
     * 来源仓库ID
     */
    @Excel(name = "来源仓库ID")
    private String sourceWarehouseId;

    private String warehouseName;

    /**
     * 来源货位id
     */
    @Excel(name = "来源货位id")
    private String sourceLocationId;

    private String locationName;

    /**
     * 通知数量
     */
    @Excel(name = "通知数量")
    private BigDecimal quantity;

    /**
     * 完成数量
     */
    @Excel(name = "完成数量")
    private BigDecimal allotQuantity;

    /**
     * 缺货数量
     */
    @Excel(name = "缺货数量")
    private BigDecimal lackQuantity;

    private BigDecimal currentLackQuantity;

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
