package com.ruoyi.common.enums;

public enum WorkType {

    SHELF(0, "上架作业"),
    PICK(1, "拣货作业"),
    REFUND(2, "退货作业");

    private Integer code;

    private String info;


    WorkType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static WorkType getEnumByCode(Integer code) {
        for (WorkType workStatus : WorkType.values()) {
            if (workStatus.getCode().equals(code)) {
                return workStatus;
            }
        }
        return null;
    }

    public static WorkType getEnumByInfo(String info) {
        for (WorkType workStatus : WorkType.values()) {
            if (workStatus.getInfo().equals(info)) {
                return workStatus;
            }
        }
        return null;
    }


}
