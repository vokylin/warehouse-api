package com.ruoyi.common.enums;

public enum ReceiveItemDetailWorkStatus {
    WAIT_WORK(0, "待作业"),

    QUALITY_INSPECTION(1, "质检中"),

    ON_THE_SHELF(2, "上架中");


    private Integer code;

    private String info;

    ReceiveItemDetailWorkStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static ReceiveItemDetailWorkStatus getReceiveItemDetailWorkStatus(Integer code) {
        for (ReceiveItemDetailWorkStatus ReceiveItemDetailWorkStatus : ReceiveItemDetailWorkStatus.values()) {
            if (ReceiveItemDetailWorkStatus.getCode().equals(code)) {
                return ReceiveItemDetailWorkStatus;
            }
        }
        return null;
    }

    public static String getReceiveItemDetailWorkStatusInfo(Integer code) {
        for (ReceiveItemDetailWorkStatus ReceiveItemDetailWorkStatus : ReceiveItemDetailWorkStatus.values()) {
            if (ReceiveItemDetailWorkStatus.getCode().equals(code)) {
                return ReceiveItemDetailWorkStatus.getInfo();
            }
        }
        return null;
    }

}
