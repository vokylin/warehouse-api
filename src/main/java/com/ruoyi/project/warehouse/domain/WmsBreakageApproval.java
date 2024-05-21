package com.ruoyi.project.warehouse.domain;

import lombok.Data;

/**
 * 报损单审批实例
 */
@Data
public class WmsBreakageApproval {
    private String id;

    /**
     * 报损单ID
     */
    private String breakageDocId;

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
