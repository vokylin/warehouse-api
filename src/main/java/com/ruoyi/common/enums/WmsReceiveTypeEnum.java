package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 入库类型
 * @author: gaomian
 * @Date: 2023-04-19
 */
@Getter
@AllArgsConstructor
public enum WmsReceiveTypeEnum {

    PURCHASE("0101", "采购入库"),
    PRODUCT("0102", "产成品入库"),
    SEMI_MANUFACTURES("0103", "半成品入库"),
    PRODUCT_REFUND("0104", "产成品退货入库"),
    BELONG_TO_CUSTOMER("0105", "客户来料入库"),
    MANUFACTURE_REFUND("0106", "生产退料入库"),
    DEVELOP_REFUND("0107", "研发退料入库"),
    SAMPLE_REFUND("010001", "留样退料入库"),
    OTHER("0199", "其他入库"),
    TRANSFER_TO_STORAGE("010002", "调拨入库");


    private String code;

    private String desc;
}
