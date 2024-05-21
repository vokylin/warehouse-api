package com.ruoyi.project.common.entity;

import lombok.Data;

/**
 * 通知仓库BaseDto
 *
 * @author gaomian
 * @since 2023-04-18
 */
@Data
public class NoticeWmsBaseDto {

    /**
     * 业务系统ID (生产计划编号、采购发运、销售发货...)
     */
    private String businessId;
    /**
     * 单据类型
     */
    private String businessType;
    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 通知人ID
     */
    private String noticeUserId;

    /**
     * 通知人名称
     */
    private String noticeUser;
    /**
     * 通知部门 （退料单、领料单）
     */
    private String deptName;

    /**
     * 通知说明
     */
    private String noticeDesc;
}
