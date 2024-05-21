package com.ruoyi.common.enums;

/**
 * 关联类型
 *
 * @author Rem
 * @date 2020-03-04
 */
public enum RelateType {

    // 0 未关联， 1 上架中 2待发货 3 分配中 4 拣货中
    NONE(0, "未关联"),
    PUTAWAY(1, "上架中"),
    DELIVERY(2, "待发货"),
    ALLOCATION(3, "分配中"),
    PICKING(4, "拣货中"),
    INVENTORY(5, "盘点中"),
    LOSS_IN_PROGRESS(6, "报损中");

    private final Integer code;

    private final String info;

    RelateType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static RelateType getRelateType(Integer code) {
        for (RelateType relateType : RelateType.values()) {
            if (relateType.getCode().equals(code)) {
                return relateType;
            }
        }
        return null;
    }

    public static String getRelateTypeDesc(Integer code) {
        for (RelateType relateType : RelateType.values()) {
            if (relateType.getCode().equals(code)) {
                return relateType.getInfo();
            }
        }
        return null;
    }

}
