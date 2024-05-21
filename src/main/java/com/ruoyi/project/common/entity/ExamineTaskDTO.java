package com.ruoyi.project.common.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 质检任务Dto
 *
 * @author gaomian
 * @since 2022/04/26
 */
@Data
public class ExamineTaskDTO {
    /**
     * 仓库质检作业ID
     */
    private String wmsExamineId;

    /**
     * 进料验收单ID
     */
    private String checkAcceptId;

    /**
     * 质检总数
     */
    private BigDecimal allQuantity;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 公司ID
     */
    private String companyId;

}
