package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 发运单对象 wms_bill_of_loading
 *
 * @author ruoyi
 * @date 2023-04-16
 */
@Data
public class WmsBillOfLoading {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 发货通知单号
     */
    @Excel(name = "发货通知单号")
    private String deliveryNoticeId;

    /**
     * 业务单号
     */
    @Excel(name = "业务单号")
    private String businessId;

    /**
     * 单据类型
     */
    @Excel(name = "单据类型")
    private String businessType;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private String companyId;

    /**
     * 计划数量
     */
    @Excel(name = "计划数量")
    private BigDecimal planQuantity;

    /**
     * 实际数量
     */
    @Excel(name = "实际数量")
    private BigDecimal quantity;

    /**
     * 客户id
     */
    @Excel(name = "客户id")
    private String customerId;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    /**
     * 发运承运商id
     */
    @Excel(name = "发运承运商id")
    private String carriersId;

    /**
     * 承运人信息
     */
    @Excel(name = "承运人信息")
    private String carriersConcat;

    /**
     * 车辆信息
     */
    @Excel(name = "车辆信息")
    private String carInfo;

    /**
     * 物流单号信息
     */
    @Excel(name = "物流单号信息")
    private String logisticsNo;

    /**
     * 运费
     */
    @Excel(name = "运费")
    private BigDecimal fare;


    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;


    /**
     * 更新者
     */
    private Long updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryDate;

    private String logisticsName;

    private String logisticsCode;

    private String customerName;

    private String customerCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
