package com.ruoyi.common.invoker.entity.request;


import com.alibaba.fastjson2.annotation.JSONField;
import com.ruoyi.common.invoker.entity.form.ApprovalForm;
import lombok.Data;

/**
 * 审批流程请求体
 *
 * @author zyh
 * @date 2022/07/11
 */
@Data
public class ApprovalProcessRequestBody {

    /**
     * 表单组件值
     */
    @JSONField(name = "formComponentValues")
    public ApprovalForm approvalForm;

    /**
     * 审批流唯一编码
     */
    @JSONField(name = "processCode")
    public String processCode;

    /**
     * 发起者用户ID
     */
    @JSONField(name = "originatorUserId")
    public String originatorUserId;

    /**
     * 部门ID
     */
    @JSONField(name = "deptId")
    public Long deptId;

    public ApprovalProcessRequestBody(ApprovalForm approvalForm, String processCode, String originatorUserId, Long deptId) {
        this.approvalForm = approvalForm;
        this.processCode = processCode;
        this.originatorUserId = originatorUserId;
        this.deptId = deptId;
    }
}
