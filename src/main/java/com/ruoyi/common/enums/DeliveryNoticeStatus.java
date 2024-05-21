package com.ruoyi.common.enums;

public enum DeliveryNoticeStatus {
    /**
     * 待激活	0
     * 作业中	1
     * 拣货中 2
     * 拒绝发运 3
     * 同意发运 4
     * 待发运 5
     * 已完成	6
     */

    WAIT_ACTIVE(0, "待激活"),

    WORKING(1, "作业中"),

    REFUSE(3, "拒绝发运"),

    WAIT_DELIVERY(5, "待发运"),

    COMPLETE(6, "已完成"),

    CANCEL(7, "取消");


    private final int code;
    private final String info;

    DeliveryNoticeStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static DeliveryNoticeStatus getReceiptStatus(int code) {
        for (DeliveryNoticeStatus receiveNoticeStatus : DeliveryNoticeStatus.values()) {
            if (receiveNoticeStatus.getCode() == code) {
                return receiveNoticeStatus;
            }
        }
        return null;
    }

    public static String getReceiptStatusInfo(int code) {
        for (DeliveryNoticeStatus receiveNoticeStatus : DeliveryNoticeStatus.values()) {
            if (receiveNoticeStatus.getCode() == code) {
                return receiveNoticeStatus.getInfo();
            }
        }
        return null;
    }


}
