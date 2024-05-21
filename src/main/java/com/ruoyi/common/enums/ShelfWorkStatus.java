package com.ruoyi.common.enums;

public enum ShelfWorkStatus {

    WORKING(0, "作业中"),

    FINISH(1, "作业完成");

    private Integer code;

    private String info;


    ShelfWorkStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static ShelfWorkStatus getEnumByCode(Integer code) {
        for (ShelfWorkStatus workStatus : ShelfWorkStatus.values()) {
            if (workStatus.getCode().equals(code)) {
                return workStatus;
            }
        }
        return null;
    }

    public static ShelfWorkStatus getEnumByInfo(String info) {
        for (ShelfWorkStatus workStatus : ShelfWorkStatus.values()) {
            if (workStatus.getInfo().equals(info)) {
                return workStatus;
            }
        }
        return null;
    }


}
