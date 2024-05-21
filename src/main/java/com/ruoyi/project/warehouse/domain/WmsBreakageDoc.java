package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 报损单对象 wms_breakage_doc
 *
 * @author Rem
 * @date 2023-05-20
 */
@Data
public class WmsBreakageDoc {
    private static final long serialVersionUID = 1L;

    /**
     * 报损单号
     */
    private String id;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private String companyId;

    /**
     * 申请部门ID
     */
    @Excel(name = "申请部门ID")
    private Long officeId;

    /**
     * 申请部门名称
     */
    @Excel(name = "申请部门名称")
    private String officeName;

    /**
     * 申请人
     */
    @Excel(name = "申请人")
    private String applicant;

    private Long applicantId;

    /**
     * 报损类型
     */
    @Excel(name = "报损类型")
    private String type;

    private String receiveSendType;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    /**
     * 报损原因
     */
    @Excel(name = "报损原因")
    private String reason;

    /**
     * 报损描述
     */
    private String description;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;


    /**
     * 更新者
     */
    private Long updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date occTime;

}
