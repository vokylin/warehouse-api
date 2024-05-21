package com.ruoyi.common.enums;

public enum SourceType {
    SALES("sales", "销售"),
    PURCHASE("purchase", "采购"),
    MANUFACTURE("manufacture", "生产"),
    WAREHOUSE("warehouse", "库存");

    private String code;

    private String info;

    SourceType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static SourceType getSourceType(String code) {
        for (SourceType sourceType : SourceType.values()) {
            if (sourceType.getCode().equals(code)) {
                return sourceType;
            }
        }
        return null;
    }
}
