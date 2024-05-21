package com.ruoyi.project.common.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 质检任务通知仓库DTO
 *
 * @author gaomian
 * @since 2022/04/29
 */
@Data
public class ExamineTaskNoticeWmsDTO {
    /**
     * 仓库质检作业ID
     */
    private String wmsExamineId;

    /**
     * 质检总数
     */
    private BigDecimal allQuantity;

    /**
     * 抽样数量
     */
    private BigDecimal sampleQuantity;

    /**
     * 留样数量
     */
    private BigDecimal keepQuantity;

    /**
     * 不合格数
     */
    private BigDecimal failQuantity;

    /**
     * 报废数量
     */
    private BigDecimal scrapQuantity;

    /**
     * 合格数量
     */
    private BigDecimal passQuantity;

    private BigDecimal sampleRefundQuantity;

    /**
     * 检验员
     */
    private String examinerName;

    /**
     * 检验主管
     */
    private String examineManager;

    /**
     * 判定结果 （2：合格、 3：不合格）
     */
    private String resultStatus;

}
