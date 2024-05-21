package com.ruoyi.common.enums;

public enum LimitedStatus {

    //    0=-正常,1=-超过上限,2=-超过下限
    NORMAL(0, "正常"),
    UP(1, "超过上限"),
    DOWN(2, "超过下限");

    private final Integer code;
    private final String info;

    LimitedStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static LimitedStatus getEnumByCode(Integer code) {
        for (LimitedStatus e : LimitedStatus.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static LimitedStatus getEnumByInfo(String info) {
        for (LimitedStatus e : LimitedStatus.values()) {
            if (e.getInfo().equals(info)) {
                return e;
            }
        }
        return null;
    }

}
