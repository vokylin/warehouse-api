package com.ruoyi.common.enums;

public enum RefundWorkStatus {

    /**
     * 0 待确认
     * 1 已退货
     */

    WAIT_CONFIRM(0, "待确认"),

    REFUNDED(1, "已退货");

    private final int code;
    private final String info;

    RefundWorkStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static RefundWorkStatus getReceiptStatus(int code) {
        for (RefundWorkStatus RefundWorkStatus : RefundWorkStatus.values()) {
            if (RefundWorkStatus.getCode() == code) {
                return RefundWorkStatus;
            }
        }
        return null;
    }

    public static String getReceiptStatusInfo(int code) {
        for (RefundWorkStatus RefundWorkStatus : RefundWorkStatus.values()) {
            if (RefundWorkStatus.getCode() == code) {
                return RefundWorkStatus.getInfo();
            }
        }
        return null;
    }

}
