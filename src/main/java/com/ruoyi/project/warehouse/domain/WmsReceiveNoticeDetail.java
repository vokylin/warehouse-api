package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 入库通知明细对象 wms_receive_notice_detail
 *
 * @author Rem
 * @date 2023-03-31
 */
@Data
public class WmsReceiveNoticeDetail {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 入库通知ID
     */
    @Excel(name = "入库通知ID")
    private String receiveNoticeId;

    /**
     * 通知数量
     */
    @Excel(name = "通知数量")
    private BigDecimal quantity;

    /**
     * 已收数量
     */
    @Excel(name = "已收数量")
    private BigDecimal receiveQuantity;

    /**
     * 分配数量
     */
    @Excel(name = "分配数量")
    private BigDecimal allotQuantity;

    /**
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productDate;

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
     * 货品规格
     */
    @Excel(name = "货品规格")
    private String spec;

    /**
     * 货品批次
     */
    @Excel(name = "货品批次")
    private String batchNo;

    /**
     * 业务数量
     */
    @Excel(name = "业务数量")
    private BigDecimal businessQuantity;

    /**
     * 业务单位
     */
    @Excel(name = "业务单位")
    private String businesUnit;

    /**
     * 所属单位类型
     */
    @Excel(name = "所属单位类型 ")
    private String belongs;

    /**
     * 换算率
     */
    @Excel(name = "换算率")
    private BigDecimal conversionRate;

    /**
     * 待检位置
     */
    @Excel(name = "待检位置")
    private String locationId;

    private String locationName;

    /**
     * 货品类型
     */
    @Excel(name = "货品类型")
    private Integer itemType;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    private String itemCodeOrName;

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
     * 到期时间
     */
    private Date expireTime;

    private String checkAcceptId;

    private BigDecimal itemPrice;

    private String warehouseId;

    private WmsReceiveItemDetail wmsReceiveItemDetail;

}
