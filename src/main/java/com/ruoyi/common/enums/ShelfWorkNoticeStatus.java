package com.ruoyi.common.enums;

public enum ShelfWorkNoticeStatus {
    NORMAL(0, "待分配"),

    WORKING(1, "分配中"),
    WORK_COMPLETE(2, "作业完成");

    private Integer code;

    private String info;


    ShelfWorkNoticeStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static ShelfWorkNoticeStatus getEnumByCode(Integer code) {
        for (ShelfWorkNoticeStatus workStatus : ShelfWorkNoticeStatus.values()) {
            if (workStatus.getCode().equals(code)) {
                return workStatus;
            }
        }
        return null;
    }

    public static ShelfWorkNoticeStatus getEnumByInfo(String info) {
        for (ShelfWorkNoticeStatus workStatus : ShelfWorkNoticeStatus.values()) {
            if (workStatus.getInfo().equals(info)) {
                return workStatus;
            }
        }
        return null;
    }


}
