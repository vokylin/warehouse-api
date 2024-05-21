package com.ruoyi.common.enums;

public enum ScrapSpecialFlag {

    /**
     * (1:报废   2：特采)物料
     */
    SCRAP(1, "报废"),
    SPECIAL(2, "特采");

    private final Integer code;

    private final String info;

    ScrapSpecialFlag(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static ScrapSpecialFlag getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (ScrapSpecialFlag e : ScrapSpecialFlag.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }


}
