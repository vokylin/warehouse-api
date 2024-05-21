package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 作业通知对象 wms_work_notice
 *
 * @author Rem
 * @date 2023-04-07
 */
@Data
public class WmsWorkNotice {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 入库通知明细ID
     */
    @Excel(name = "入库通知明细ID")
    private String noticeItemDetailId;

    /**
     * 入库通知ID
     */
    @Excel(name = "入库通知ID")
    private String noticeId;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private String companyId;

    /**
     * 类型(上架、拣货)
     */
    @Excel(name = "类型(上架、拣货)")
    private Integer type;

    /**
     * 收发类型
     */
    @Excel(name = "收发类型")
    private String receiveSendType;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    /**
     * 作业开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "作业开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workStartTime;

    /**
     * 作业完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "作业完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workEndTime;

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

    private Integer[] statusArr;
}
