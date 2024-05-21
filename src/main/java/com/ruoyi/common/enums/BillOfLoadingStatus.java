package com.ruoyi.common.enums;

public enum BillOfLoadingStatus {

    // 0 待发运 1 已完成
    WAITING(0, "待发运"),
    FINISHED(1, "已完成");

    private final Integer code;

    private final String info;

    BillOfLoadingStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static BillOfLoadingStatus getBillOfLoadingStatus(Integer code) {
        for (BillOfLoadingStatus billOfLoadingStatus : BillOfLoadingStatus.values()) {
            if (billOfLoadingStatus.getCode().equals(code)) {
                return billOfLoadingStatus;
            }
        }
        return null;
    }

    public static String getBillOfLoadingStatusInfo(Integer code) {
        for (BillOfLoadingStatus billOfLoadingStatus : BillOfLoadingStatus.values()) {
            if (billOfLoadingStatus.getCode().equals(code)) {
                return billOfLoadingStatus.getInfo();
            }
        }
        return null;
    }
}
