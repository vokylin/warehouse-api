package com.ruoyi.common.invoker.entity.result;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;


/**
 * 审批任务回调
 *
 * @author REM
 * @date 2023/05/17
 */
@Data
public class CallbackApprovalTaskEndResult {
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
     * finish：表示审批任务结束。
     * cancel：说明当前节点有多个审批人并且是或签，其中一个人执行了审批，其他审批人会推送cancel类型事件。
     */
    private String type;

    /**
     * 当前任务审批人的userId
     */
    private String staffId;

    /**
     * agree：同意
     * refuse：拒绝
     */
    private String result;

    /**
     * remark表示操作时写的评论内容
     */
    private String remark;

    /**
     * 审批模板的唯一码
     */
    private String processCode;

}
