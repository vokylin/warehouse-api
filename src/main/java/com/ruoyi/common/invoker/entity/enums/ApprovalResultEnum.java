package com.ruoyi.common.invoker.entity.enums;


/**
 * 审批结果枚举
 *
 * @author REM
 * @date 2023/05/17
 */
public enum ApprovalResultEnum {

    AGREE("agree", "同意"),
    REFUSE("refuse", "拒绝");

    private final String code;
    private final String name;

    ApprovalResultEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
