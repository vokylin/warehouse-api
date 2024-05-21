package com.ruoyi.common.enums;

public enum RefundWorkNoticeStatus {

    /**
     * 0 待确认
     * 1 退货中
     * 2 退货完成
     */

    WAIT_REFUND(0, "待退货"),

    REFUNDING(1, "退货中"),

    REFUNDED(2, "退货完成");

    private final int code;
    private final String info;

    RefundWorkNoticeStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static RefundWorkNoticeStatus getReceiptStatus(int code) {
        for (RefundWorkNoticeStatus RefundWorkStatus : RefundWorkNoticeStatus.values()) {
            if (RefundWorkStatus.getCode() == code) {
                return RefundWorkStatus;
            }
        }
        return null;
    }

    public static String getReceiptStatusInfo(int code) {
        for (RefundWorkNoticeStatus RefundWorkStatus : RefundWorkNoticeStatus.values()) {
            if (RefundWorkStatus.getCode() == code) {
                return RefundWorkStatus.getInfo();
            }
        }
        return null;
    }

}
