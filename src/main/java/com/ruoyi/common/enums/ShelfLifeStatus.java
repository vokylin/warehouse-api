package com.ruoyi.common.enums;

public enum ShelfLifeStatus {

//    保质期状态 （0-正常 1-临期 2-过期）

    NORMAL(0, "正常"),
    EARLY(1, "临期"),
    EXPIRED(2, "过期");

    private final Integer code;
    private final String info;

    ShelfLifeStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static ShelfLifeStatus getEnumByCode(Integer code) {
        for (ShelfLifeStatus value : ShelfLifeStatus.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

}
