package com.ruoyi.common.invoker.entity.result;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;


/**
 * 审批实例结束回调结果
 *
 * @author REM
 * @date 2023/05/17
 */
@Data
public class CallbackApprovalInstanceEndResult {
    /**
     * 事件类型
     */
    @JSONField(name = "EventType")
    private String eventType;

    /**
     * 审批实例id。
     */
    private String processInstanceId;

    /**
     * 发生审批任务变更的企业corpId
     */
    private String corpId;

    /**
     * 创建任务的时间。时间戳，单位毫秒。
     */
    private String createTime;

    /**
     * 结束任务的时间。时间戳，单位毫秒。
     */
    private String finishTime;

    /**
     * 实例标题
     */
    private String title;

    /**
     * 审批任务结束类型：
     * finish：审批正常结束（同意或拒绝）
     * terminate：审批终止（发起人撤销审批单）。
     */
    private String type;

    /**
     * 当前任务审批人的userId
     */
    private String staffId;

    /**
     * 审批实例url
     */
    private String url;

    /**
     * 正常结束时result为agree，拒绝时result为refuse，审批终止时没这个值
     */
    private String result;

    /**
     * 审批模板的唯一码
     */
    private String processCode;

}
