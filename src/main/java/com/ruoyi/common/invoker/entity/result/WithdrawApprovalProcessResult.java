package com.ruoyi.common.invoker.entity.result;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;


/**
 * 撤销审批过程结果
 *
 * @author REM
 * @date 2023/05/17
 */
@Data
public class WithdrawApprovalProcessResult {
    /**
     * 错误代码
     */
    @JSONField(name = "result")
    private boolean result;
    /**
     * 错误代码
     */
    @JSONField(name = "success")
    private boolean success;

    /**
     * 错误代码
     */
    @JSONField(name = "code")
    private String errCode;
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
