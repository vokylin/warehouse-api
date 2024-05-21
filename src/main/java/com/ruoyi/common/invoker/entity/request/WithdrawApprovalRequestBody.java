package com.ruoyi.common.invoker.entity.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;


/**
 * 撤销审批过程结果
 *
 * @author REM
 * @date 2023/05/17
 */
@Data
public class WithdrawApprovalRequestBody {

    public WithdrawApprovalRequestBody(String processInstanceId, boolean isSystem) {
        Request req = new Request();
        req.setProcessInstanceId(processInstanceId);
        req.isSystem = isSystem;
        this.request = req;
    }

    @JSONField(name = "request")
    public Request request;

    @Data
    public static class Request {

        /**
         * 审批实例ID
         */
        @JSONField(name = "process_instance_id")
        public String processInstanceId;

        /**
         * 是否通过系统操作：
         * true：由系统直接终止
         * false：由指定的操作者终止
         */
        @JSONField(name = "is_system")
        public boolean isSystem;

        /**
         * 终止说明
         */
        public String remark;

        /**
         * 操作人的userid。
         * 当is_system为false时，该参数必传
         */
        @JSONField(name = "operating_userid")
        public String operatingUserId;

    }
}
