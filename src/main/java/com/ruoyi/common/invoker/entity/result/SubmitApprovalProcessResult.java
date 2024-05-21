package com.ruoyi.common.invoker.entity.result;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;


/**
 * 提交审批流程结果
 *
 * @author REM
 * @date 2023/05/17
 */
@Data
public class SubmitApprovalProcessResult {

    /**
     * 错误代码
     */
    @JSONField(name = "code")
    private String errCode;

    /**
     * 流程实例ID
     */
    @JSONField(name = "instanceId")
    private String processInstanceId;

    /**
     * 错误消息
     */
    @JSONField(name = "message")
    private String errMsg;

    /**
     * 请求ID
     */
    @JSONField(name = "requestid")
    private String requestId;
}
