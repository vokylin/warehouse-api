package com.ruoyi.common.enums;

public enum ReciveItemStatus {

    WAITING_FOR_INSPECTION(0, "待检验"),

    INSPECTION_IN_PROGRESS(1, "质检中"),

    QUALIFIED(2, "合格"),

    UNQUALIFIED(3, "不合格");

    private Integer code;

    private String info;

    ReciveItemStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static ReciveItemStatus getReceiptStatus(Integer code) {
        for (ReciveItemStatus receiptStatus : ReciveItemStatus.values()) {
            if (receiptStatus.getCode().equals(code)) {
                return receiptStatus;
            }
        }
        return null;
    }

    public static String getReceiptStatusInfo(Integer code) {
        for (ReciveItemStatus receiptStatus : ReciveItemStatus.values()) {
            if (receiptStatus.getCode().equals(code)) {
                return receiptStatus.getInfo();
            }
        }
        return null;
    }


}
