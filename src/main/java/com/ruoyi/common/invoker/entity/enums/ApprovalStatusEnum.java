package com.ruoyi.common.invoker.entity.enums;

/**
 * 钉钉审批实例状态枚举
 */
public enum ApprovalStatusEnum {

    /**
     * NEW：新创建
     * RUNNING：审批中
     * TERMINATED：被终止
     * COMPLETED：完成
     * CANCELED：取消
     */
    NEW("NEW", "新创建"),

    RUNNING("RUNNING", "审批中"),

    TERMINATED("TERMINATED", "被终止"),

    COMPLETED("COMPLETED", "完成"),

    CANCELED("CANCELED", "取消");

    private final String code;

    private final String name;

    ApprovalStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ApprovalStatusEnum getByCode(String code) {
        for (ApprovalStatusEnum approvalStatusEnum : ApprovalStatusEnum.values()) {
            if (approvalStatusEnum.getCode().equals(code)) {
                return approvalStatusEnum;
            }
        }
        return null;
    }


}
