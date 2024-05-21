package com.ruoyi.common.enums;

/**
 * 通知状态枚举
 *
 * @author REM
 * @date 2023/04/07
 */
public enum ExamineWorkNoticeStatus {
    UNNOTIFIED(0, "未通知"),
    NOTIFIED(1, "已通知"),

    ON_THE_SHELF(2, "上架中"),

    RETURNED(3, "退货中");

    private final int code;
    private final String info;

    ExamineWorkNoticeStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static ExamineWorkNoticeStatus getNoticeStatus(int code) {
        for (ExamineWorkNoticeStatus examineWorkNoticeStatus : ExamineWorkNoticeStatus.values()) {
            if (examineWorkNoticeStatus.getCode() == code) {
                return examineWorkNoticeStatus;
            }
        }
        return null;
    }

    public static String getNoticeStatusInfo(int code) {
        for (ExamineWorkNoticeStatus examineWorkNoticeStatus : ExamineWorkNoticeStatus.values()) {
            if (examineWorkNoticeStatus.getCode() == code) {
                return examineWorkNoticeStatus.getInfo();
            }
        }
        return null;
    }

}
