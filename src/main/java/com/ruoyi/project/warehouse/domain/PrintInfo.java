package com.ruoyi.project.warehouse.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@Builder
public class PrintInfo {
    /**
     * 单据类型
     */
    private Integer documentType;

    /**
     * 日期时间
     */
    private Date dateTime;

    /**
     * 单据编号
     */
    private String documentNumber;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 仓库代码
     */
    private String warehouseCode;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 供应商
     */
    private String supply;

    private String purchaseDept;

    /**
     * 经办人
     */
    private String operator;

    /**
     * 经办人主管
     */
    private String operatorManager;

    /**
     * 部门
     */
    private String deptName;

    private String warehouseOperator;

    /**
     * 仓库主管
     */
    private String warehouseOperatorManager;

    /**
     * 主管
     */
    private String supervisor;

    private String companyName;

    private List<PrintItemDetail> printItemDetailList;
}
