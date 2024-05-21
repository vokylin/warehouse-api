package com.ruoyi.project.warehouse.domain;

import lombok.Data;

/**
 * 进料验收单打印详情VO
 *
 * @author gaomian
 * @since 2023-04-30
 */
@Data
public class FeedstockFormPrintDetailVO {
    /**
     * 采购内容
     */
    FeedstockPurchaseInfoVO purchaseInfo;

    /**
     * 质检\仓库入库内容
     */
    FeedstockExamineDeliveryInfoVO examineDeliveryInfo;
}
