package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 出库类型
 * @author: gaomian
 * @Date: 2023-04-19
 */
@Getter
@AllArgsConstructor
public enum WmsDeliveryTypeEnum {

    SALE("0201", "产品销售出库"),
    MANUFACTURE("0202", "生产领料出库"),
    DEVELOP("0203", "研发领料出库"),
    KEEP_SAMPLE("0204", "原辅料检验留样出库"),
    CONSUMABLES("020001", "耗材出库"),
    SALE_PACKAGE("020002", "销售包材出库"),
    BELONG_TO_CUSTOMER("020003", "客户来料出库"),
    QUALITY_INSPECTION_PICKING("020004", "质检领料"),
    OTHER("0299", "其他出库"),
    TRANSFER_OUT_OF_THE_WAREHOUSE("020005", "调拨出库");

    private String code;

    private String desc;
}
