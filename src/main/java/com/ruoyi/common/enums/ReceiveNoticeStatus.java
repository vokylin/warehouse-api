package com.ruoyi.common.enums;

public enum ReceiveNoticeStatus {
    /**
     * 待激活	0
     * 收货中	1
     * 取消	8
     * 交接完成	9
     */

    WAIT_ACTIVE(0, "待激活"),

    RECEIVING(1, "收货中"),

    CANCEL(8, "取消"),

    COMPLETE(9, "交接完成");

    private final int code;
    private final String info;

    ReceiveNoticeStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static ReceiveNoticeStatus getReceiptStatus(int code) {
        for (ReceiveNoticeStatus receiveNoticeStatus : ReceiveNoticeStatus.values()) {
            if (receiveNoticeStatus.getCode() == code) {
                return receiveNoticeStatus;
            }
        }
        return null;
    }

    public static String getReceiptStatusInfo(int code) {
        for (ReceiveNoticeStatus receiveNoticeStatus : ReceiveNoticeStatus.values()) {
            if (receiveNoticeStatus.getCode() == code) {
                return receiveNoticeStatus.getInfo();
            }
        }
        return null;
    }


}
