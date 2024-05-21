package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 出库通知对象 wms_delivery_notice
 *
 * @author Rem
 * @date 2023-04-12
 */
@Data
public class WmsDeliveryNotice {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 业务系统单号
     */
    @Excel(name = "业务系统单号")
    private String businessId;

    /**
     * 单据类型（销售发货单、生产领料出库...）
     */
    @Excel(name = "单据类型", readConverterExp = "销=售发货单、生产领料出库...")
    private String businessType;

    /**
     * 发货货位
     */
    @Excel(name = "发货货位")
    private String locationId;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private String companyId;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    /**
     * 发货开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发货开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryStartTime;

    /**
     * 发货完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发货完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryEndTime;

    /**
     * 通知人ID
     */
    @Excel(name = "通知人ID")
    private String noticeUserId;

    /**
     * 通知人名称
     */
    @Excel(name = "通知人名称")
    private String noticeUser;

    /**
     * 收货方编码
     */
    @Excel(name = "收货方编码")
    private String toCode;

    /**
     * 收货方名称
     */
    @Excel(name = "收货方名称")
    private String toName;

    /**
     * 收货方联系人
     */
    @Excel(name = "收货方联系人")
    private String toContacts;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String toContactsTel;

    /**
     * 联系地址
     */
    @Excel(name = "联系地址")
    private String toAddress;

    /**
     * 温层
     */
    @Excel(name = "温层")
    private String thermosphere;

    private String deptName;


    /**
     * 通知说明
     */
    private String noticeDesc;

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
     * 收发类别
     */
    private String receiveSendType;

    private Integer[] statusArr;

}
