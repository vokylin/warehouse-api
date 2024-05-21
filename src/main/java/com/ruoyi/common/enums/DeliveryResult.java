package com.ruoyi.common.enums;

public enum DeliveryResult {
    REJECT(0, "拒绝发运"),
    AGREE(1, "同意发运");

    private final Integer code;
    private final String info;

    DeliveryResult(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static DeliveryResult getDeliveryResult(Integer code) {
        for (DeliveryResult deliveryResult : DeliveryResult.values()) {
            if (deliveryResult.getCode().equals(code)) {
                return deliveryResult;
            }
        }
        return null;
    }

    public static String getDeliveryResultInfo(Integer code) {
        for (DeliveryResult deliveryResult : DeliveryResult.values()) {
            if (deliveryResult.getCode().equals(code)) {
                return deliveryResult.getInfo();
            }
        }
        return null;
    }

}
