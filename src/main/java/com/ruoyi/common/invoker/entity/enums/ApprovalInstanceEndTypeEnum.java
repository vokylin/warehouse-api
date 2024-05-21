package com.ruoyi.common.invoker.entity.enums;

/**
 * 审批任务结束枚举类型
 *
 * @author REM
 * @date 2023/05/17
 */
public enum ApprovalInstanceEndTypeEnum {

    START("start", "审批任务开始"),

    FINISH("finish", "完成"),

    // 说明当前节点有多个审批人并且是或签，其中一个人执行了审批，其他审批人会推送cancel类型事件。
    CANCEL("cancel", "取消"),

    REDIRECT("redirect", "转交"),

    // 发起人撤销审批单
    TERMINATE("terminate", "审批终止");

    private final String code;
    private final String name;

    ApprovalInstanceEndTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ApprovalInstanceEndTypeEnum getByCode(String code) {
        for (ApprovalInstanceEndTypeEnum approvalTaskEndTypeEnum : ApprovalInstanceEndTypeEnum.values()) {
            if (approvalTaskEndTypeEnum.getCode().equals(code)) {
                return approvalTaskEndTypeEnum;
            }
        }
        return null;
    }

}
