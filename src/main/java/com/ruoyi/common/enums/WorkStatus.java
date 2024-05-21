package com.ruoyi.common.enums;

public enum WorkStatus {
    NORMAL(0, "正常"),

    WORKING(1, "作业中"),
    ABNORMAL(2, "异常");

    private Integer code;

    private String info;


    WorkStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static WorkStatus getEnumByCode(Integer code) {
        for (WorkStatus workStatus : WorkStatus.values()) {
            if (workStatus.getCode().equals(code)) {
                return workStatus;
            }
        }
        return null;
    }

    public static WorkStatus getEnumByInfo(String info) {
        for (WorkStatus workStatus : WorkStatus.values()) {
            if (workStatus.getInfo().equals(info)) {
                return workStatus;
            }
        }
        return null;
    }


}
