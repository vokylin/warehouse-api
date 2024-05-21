package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 报损单对象 wms_breakage_doc
 *
 * @author Rem
 * @date 2023-05-20
 */
@Data
public class BreakageDocPrintInfo {
    private static final long serialVersionUID = 1L;

    /**
     * 报损单号
     */
    private String id;

    /**
     * 报损详情ID
     */
    private String detailId;

    private String printNumber;

    /**
     * 申请人
     */
    private String applicant;


    /**
     * 报损原因
     */
    private String reason;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date occTime;

    /**
     * 物料编码
     */
    private String itemCode;

    /**
     * 物料名称
     */
    private String itemName;

    /**
     * 批次
     */
    private String batchNo;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 不良原因
     */
    private String badCauses;

    private String description;


    /**
     * 部门主管
     */
    private String deptSupervisor;

    /**
     * 品管
     */
    private String qualitySupervisor;

    /**
     * 质量部主管
     */
    private String quantityDeptSupervisor;
}
