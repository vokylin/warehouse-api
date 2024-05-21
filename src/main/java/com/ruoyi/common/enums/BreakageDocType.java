package com.ruoyi.common.enums;

public enum BreakageDocType {
    FINISHED_PRODUCT("1", "产成品"), RAW_MATERIAL("2", "原物料"), OTHER("3", "其他");

    private final String code;

    private final String info;

    BreakageDocType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static BreakageDocType getEnumByCode(String code) {
        for (BreakageDocType e : BreakageDocType.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static BreakageDocType getEnumByInfo(String info) {
        for (BreakageDocType e : BreakageDocType.values()) {
            if (e.getInfo().equals(info)) {
                return e;
            }
        }
        return null;
    }
}
