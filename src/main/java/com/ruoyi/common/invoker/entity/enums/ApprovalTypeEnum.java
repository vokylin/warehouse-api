package com.ruoyi.common.invoker.entity.enums;

public enum ApprovalTypeEnum {

    START("start", "发起审批"),

    FINISH("finish", "审批结束"),

    CANCEL("cancel", "审批取消"),

    TERMINATE("terminate", "审批终止");

    private String code;
    private String name;

    ApprovalTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }


    public static ApprovalTypeEnum getEnumByCode(String code) {
        for (ApprovalTypeEnum e : ApprovalTypeEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static ApprovalTypeEnum getEnumByName(String name) {
        for (ApprovalTypeEnum e : ApprovalTypeEnum.values()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

}
