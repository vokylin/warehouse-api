package com.ruoyi.common.enums;

public enum ReceiptStatus {

    WAIT_ACTIVE(0, "待收货"),
    RECEIVING(1, "收货中"),
    RECEIVED(2, "已收货"),
    CANCEL(3, "已取消");

    private Integer code;

    private String info;

    ReceiptStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static ReceiptStatus getReceiptStatus(Integer code) {
        for (ReceiptStatus receiptStatus : ReceiptStatus.values()) {
            if (receiptStatus.getCode().equals(code)) {
                return receiptStatus;
            }
        }
        return null;
    }

    public static String getReceiptStatusInfo(Integer code) {
        for (ReceiptStatus receiptStatus : ReceiptStatus.values()) {
            if (receiptStatus.getCode().equals(code)) {
                return receiptStatus.getInfo();
            }
        }
        return null;
    }


}
