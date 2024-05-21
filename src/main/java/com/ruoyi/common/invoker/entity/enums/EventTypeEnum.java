package com.ruoyi.common.invoker.entity.enums;


/**
 * 事件类型枚举
 *
 * @author REM
 * @date 2023/05/17
 */
public enum EventTypeEnum {

    CHECK_URL("check_url", "检查回调接口"),
    BPMS_TASK_CHANGE("bpms_task_change", "审批任务开始、结束、转交事件"),
    BPMS_INSTANCE_CHANGE("bpms_instance_change", "审批实例开始、结束 事件");

    private final String code;
    private final String name;

    EventTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static EventTypeEnum getByCode(String code) {
        for (EventTypeEnum eventTypeEnum : EventTypeEnum.values()) {
            if (eventTypeEnum.getCode().equals(code)) {
                return eventTypeEnum;
            }
        }
        return null;
    }


}
