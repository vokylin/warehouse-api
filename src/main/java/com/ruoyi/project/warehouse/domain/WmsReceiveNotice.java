package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 入库通知对象 wms_receive_notice
 *
 * @author Rem
 * @date 2023-03-31
 */
@Data
public class WmsReceiveNotice {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    private String[] ids;

    /**
     * 业务系统单号
     */
    @Excel(name = "业务系统单号")
    private String businessId;

    /**
     * 业务单据类型
     */
    @Excel(name = "业务单据类型")
    private String businessType;

    /**
     * 单据说明
     */
    @Excel(name = "单据说明")
    private String noticeDesc;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private String companyId;

    /**
     * 通知状态
     */
    @Excel(name = "通知状态")
    private Integer status;

    /**
     * 收货开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "收货开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 收货完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "收货完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 通知人
     */
    @Excel(name = "通知人")
    private String noticeUser;

    /**
     * 通知时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "通知时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date noticeTime;

    /**
     * 供应商ID
     */
    @Excel(name = "发出方名称")
    private String fromName;

    private String fromCode;

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

    private String taskId;

    private Integer[] statusArr;

    private String receiveSendType;

    /**
     * 单据类型
     */
    private Integer billType;

    private String deptName;

    /**
     * (1:报废   2：特采)物料
     */
    private Integer scrapSpecialFlag;

    private List<WmsReceiveNoticeDetail> wmsReceiveNoticeDetailList;

}
