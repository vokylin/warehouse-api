package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出库通知明细对象 wms_delivery_notice_detail
 *
 * @author Rem
 * @date 2023-04-12
 */
@Data
public class WmsDeliveryNoticeDetail {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 出库通知id
     */
    @Excel(name = "出库通知id")
    private String deliveryNoticeId;

    /**
     * 通知数量
     */
    @Excel(name = "通知数量")
    private BigDecimal quantity;

    /**
     * 分配数量
     */
    @Excel(name = "分配数量")
    private BigDecimal allotQuantity;

    /**
     * 作业数量
     */
    @Excel(name = "作业数量")
    private BigDecimal workQuantity;

    /**
     * 作业完成数量
     */
    @Excel(name = "作业完成数量")
    private BigDecimal workFinishQuantity;

    /**
     * 发货数量
     */
    @Excel(name = "发货数量")
    private BigDecimal deliveryQuantity;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

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
     * 物料条码
     */
    @Excel(name = "物料条码")
    private String itemBarcode;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String itemUnit;

    /**
     * 规格
     */
    @Excel(name = "规格")
    private String itemSpec;

    /**
     * 业务单位数量
     */
    @Excel(name = "业务单位数量")
    private BigDecimal businessQuantity;

    /**
     * 业务单位
     */
    @Excel(name = "业务单位")
    private String businessUnit;

    /**
     * 所属单位类型
     */
    @Excel(name = "所属单位类型")
    private String belongs;

    /**
     * 换算率
     */
    @Excel(name = "换算率")
    private BigDecimal conversionRate;

    /**
     * 业务编号
     */
    @Excel(name = "业务编号")
    private String businessNo;
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
