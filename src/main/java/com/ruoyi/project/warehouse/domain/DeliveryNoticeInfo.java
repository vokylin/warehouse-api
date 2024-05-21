package com.ruoyi.project.warehouse.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeliveryNoticeInfo {

    private String id;

    /**
     * 通知单号
     */
    private String deliveryNoticeId;

    /**
     * 业务单号
     */
    private String businessId;

    /**
     * 单据类型
     */
    private String businessType;

    /**
     * 收货方名称
     */
    private String toName;

    /**
     * 收货方编码
     */
    private String toCode;

    private String noticeUserId;

    private String noticeUser;

    private String toContacts;

    private String toContactsTel;

    private String toAddress;

    private String thermosphere;

    /**
     * 总数量
     */
    private BigDecimal totalQuantity;

    /**
     * 拣货数量
     */
    private BigDecimal pickQuantity;

    /**
     * 实发数量
     */
    private BigDecimal deliveryQuantity;

}
