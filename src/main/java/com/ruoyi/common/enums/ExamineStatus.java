package com.ruoyi.common.enums;

public enum ExamineStatus {
    WAIT_EXAMINE(0, "待检验"),
    EXAMINING(1, "质检中"),
    QUALIFIED(2, "合格"),
    UNQUALIFIED(3, "不合格");

    private Integer code;

    private String info;

    ExamineStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static ExamineStatus getExamineStatus(Integer code) {
        for (ExamineStatus examineStatus : ExamineStatus.values()) {
            if (examineStatus.getCode().equals(code)) {
                return examineStatus;
            }
        }
        return null;
    }

    public static String getExamineStatusInfo(Integer code) {
        for (ExamineStatus examineStatus : ExamineStatus.values()) {
            if (examineStatus.getCode().equals(code)) {
                return examineStatus.getInfo();
            }
        }
        return null;
    }


}
