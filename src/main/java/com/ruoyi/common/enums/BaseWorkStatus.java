package com.ruoyi.common.enums;

public enum BaseWorkStatus {
    WAIT(0, "待操作"),

    FINISH(1, "已完成");

    private final int code;
    private final String info;

    BaseWorkStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static BaseWorkStatus getBaseWorkStatus(int code) {
        for (BaseWorkStatus baseWorkStatus : BaseWorkStatus.values()) {
            if (baseWorkStatus.getCode() == code) {
                return baseWorkStatus;
            }
        }
        return null;
    }

    public static String getBaseWorkStatusInfo(int code) {
        for (BaseWorkStatus baseWorkStatus : BaseWorkStatus.values()) {
            if (baseWorkStatus.getCode() == code) {
                return baseWorkStatus.getInfo();
            }
        }
        return null;
    }
}
