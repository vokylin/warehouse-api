package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 入库货物明细对象 wms_receive_item_detail
 *
 * @author Rem
 * @date 2023-03-31
 */
@Data
public class WmsReceiveItemDetail {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 入库通知货品ID
     */
    @Excel(name = "入库通知货品ID ")
    private String receiveNoticeDetailId;

    private String receiveNoticeId;

    /**
     * 货品编码
     */
    @Excel(name = "货品编码")
    private String itemCode;

    /**
     * 货品名称
     */
    @Excel(name = "货品名称")
    private String itemName;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;

    /**
     * 分配数量
     */
    @Excel(name = "分配数量")
    private BigDecimal quantity;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    /**
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productDate;

    /**
     * 过期日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "过期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireTime;

    /**
     * 批次
     */
    @Excel(name = "批次")
    private String batchNo;

    /**
     * 货品单价
     */
    @Excel(name = "货品单价")
    private BigDecimal price;


    /**
     * 作业状态
     */
    private Integer workStatus;

    /**
     * 合格数量
     */
    private BigDecimal passQuantity;

    /**
     * 不合格数
     */
    private BigDecimal failQuantity;

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

    /**
     * 退货数量
     */
    private BigDecimal refundQuantity;

    /**
     * 抽样数量
     */
    private BigDecimal sampleQuantity;

    /**
     * 留样数量
     */
    private BigDecimal keepQuantity;

    private BigDecimal sampleRefundQuantity;

    /**
     * 上架数量
     */
    private BigDecimal depositQuantity;

    /**
     * 待检位置
     */
    private String locationId;

    /**
     * 批量上架IDs
     */
    private String[] ids;

}
