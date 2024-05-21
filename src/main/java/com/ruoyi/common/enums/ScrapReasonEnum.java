package com.ruoyi.common.enums;

public enum ScrapReasonEnum {
    // 保质到期 0 物料变质 1   残次品报废 2 客户退货 3 其他 4
    EXPIRED("0", "保质到期"), BAD("1", "物料变质"), DEFECTIVE("2", "残次品报废"), CUSTOMER_RETURNS("3", "客户退货"), OTHER("4", "其他");

    private final String code;

    private final String info;

    ScrapReasonEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static ScrapReasonEnum getEnumByCode(String code) {
        for (ScrapReasonEnum e : ScrapReasonEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static ScrapReasonEnum getEnumByInfo(String info) {
        for (ScrapReasonEnum e : ScrapReasonEnum.values()) {
            if (e.getInfo().equals(info)) {
                return e;
            }
        }
        return null;
    }
}
