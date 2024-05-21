package com.ruoyi.common.enums;

public enum NoticeType {

    RECEIVE_NOTICE(0, "收货通知单"),

    DELIVERY_NOTICE(1, "发货通知单");

    private final Integer code;

    private final String info;

    NoticeType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static NoticeType getNoticeTypeByCode(Integer code) {
        for (NoticeType noticeType : NoticeType.values()) {
            if (noticeType.getCode().equals(code)) {
                return noticeType;
            }
        }
        return null;
    }

}
