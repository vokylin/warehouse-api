package com.ruoyi.common.enums;

public enum BreakageDocStatus {
    //状态(待提交审核、审核中、已通过、未通过)
    SUBMIT(0, "待提交审核"), AUDIT(1, "审核中"), PASS(2, "已通过"), REJECT(3, "未通过"), CANCEL(4, "审批取消");

    private final Integer code;

    private final String info;

    BreakageDocStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static BreakageDocStatus getEnumByCode(Integer code) {
        for (BreakageDocStatus e : BreakageDocStatus.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static BreakageDocStatus getEnumByInfo(String info) {
        for (BreakageDocStatus e : BreakageDocStatus.values()) {
            if (e.getInfo().equals(info)) {
                return e;
            }
        }
        return null;
    }
}
