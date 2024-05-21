package com.ruoyi.project.warehouse.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 进料验收单质检、仓库入库内容VO
 *
 * @author gaomian
 * @since 2023-04-30
 */
@Data
public class FeedstockExamineDeliveryInfoVO {
    /**
     * 质检类型
     */
    private String checkType;

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

    /**
     * 附件类型（1: 测试报告、 2:其他）
     */
    private String annexType;

    /**
     * 验收记录 （勾选其他后填写内容）
     */
    private String annexRecord;

    /**
     * 检验结果 (2：合格、 3：不合格)
     */
    private String resultStatus;

    /**
     * 结果说明
     */
    private String resultDesc;
    /**
     * 检验人
     */
    private String examinerName;
    /**
     * 检验主管
     */
    private String examineManager;
    /**
     * 收货状态
     */
    private String receiveStatus;

    /**
     * 接收数量
     */
    private BigDecimal receiveQuantity;

    /**
     * 退还数量
     */
    private BigDecimal refundQuantity;

    /**
     * 特采编号
     */
    private String specialNo;

    /**
     * 仓库处理情况备注
     */
    private String wmsRemark;

    /**
     * 仓管
     */
    private String warehouseKeeper;

}
