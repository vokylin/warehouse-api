package com.ruoyi.common.enums;

public enum PickWorkNoticeStatus {
    NORMAL(0, "待分配"),

    REGISTRATION(1, "缺货登记中"),
    WORK_COMPLETE(2, "作业完成"),

    CANCEL(3, "取消");

    private Integer code;

    private String info;


    PickWorkNoticeStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static PickWorkNoticeStatus getEnumByCode(Integer code) {
        for (PickWorkNoticeStatus workStatus : PickWorkNoticeStatus.values()) {
            if (workStatus.getCode().equals(code)) {
                return workStatus;
            }
        }
        return null;
    }

    public static PickWorkNoticeStatus getEnumByInfo(String info) {
        for (PickWorkNoticeStatus workStatus : PickWorkNoticeStatus.values()) {
            if (workStatus.getInfo().equals(info)) {
                return workStatus;
            }
        }
        return null;
    }


}
