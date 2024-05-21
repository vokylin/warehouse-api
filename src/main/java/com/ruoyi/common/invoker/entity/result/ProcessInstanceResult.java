package com.ruoyi.common.invoker.entity.result;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 钉钉审批实例返回实体
 *
 * @author REM
 * @date 2023/05/17
 */

@Data
public class ProcessInstanceResult {
    /**
     * 请求ID
     */
    @JSONField(name = "requestid")
    private String requestId;
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

    private String isSuccess;

    /**
     * 审批实例详情
     */
    @JSONField(name = "result")
    private Result result;

    @Data
    public static class Result {

        /**
         * 审批实例标题
         */
        @JSONField(name = "title")
        private String title;
        /**
         * 开始时间
         */
        @JSONField(name = "createTime")
        private String createTime;
        /**
         * 结束时间
         */
        @JSONField(name = "finishTime")
        private String finishTime;
        /**
         * 发起人的userid
         */
        @JSONField(name = "originatorUserId")
        private String originatorUserId;
        /**
         * 发起人的部门。-1表示根部门。
         */
        @JSONField(name = "originatorDeptId")
        private String originatorDeptId;

        /**
         * 发起部门
         */
        @JSONField(name = "originatorDeptName")
        private String originatorDeptName;

        /**
         * 审批状态：
         * <p>
         * NEW：新创建
         * <p>
         * RUNNING：审批中
         * <p>
         * TERMINATED：被终止
         * <p>
         * COMPLETED：完成
         * <p>
         * CANCELED：取消
         */
        @JSONField(name = "status")
        private String status;
        /**
         * 审批人userid
         */
        @JSONField(name = "approverUserIds")
        private String[] approverUserIds;
        /**
         * 抄送人userid
         */
        @JSONField(name = "ccUserIds")
        private String[] ccUserIds;
        /**
         * 审批结果
         * agree：同意
         * <p>
         * refuse：拒绝
         */
        @JSONField(name = "result")
        private String result;
        /**
         * 审批实例业务编号
         */
        @JSONField(name = "businessId")
        private String businessId;

        /**
         * 操作记录列表
         */
        @JSONField(name = "operationRecords")
        private List<OperationRecordsVo> operationRecords;

        /**
         * 任务列表
         */
        @JSONField(name = "tasks")
        private List<Task> tasks;


        /**
         * 审批实例业务动作
         */
        @JSONField(name = "bizAction")
        private String bizAction;


        /**
         * 审批附属实例列表
         */
        @JSONField(name = "attachedProcessInstanceIds")
        private String attachedProcessInstanceIds;

        /**
         * 表单详情列表
         */
        @JSONField(name = "formComponentValues")
        private List<Result.formComponentValues> formComponentValues;

        /**
         * 主流程实例标识
         */
        @JSONField(name = "mainProcessInstanceId")
        private String mainProcessInstanceId;


        /**
         * 操作记录详情
         */
        @Data
        public static class OperationRecordsVo {
            /**
             * 操作人userid
             */
            @JSONField(name = "userId")
            private String userId;

            /**
             * 操作时间
             */
            @JSONField(name = "date")
            private Date date;

            /**
             * 操作类型
             */
            @JSONField(name = "type")
            private String type;

            /**
             * 操作结果
             */
            @JSONField(name = "result")
            private String result;

            /**
             * 评论内容
             */
            @JSONField(name = "remark")
            private String remark;

            /**
             * 评论附件
             */
            @JSONField(name = "attachments")
            private List<Attachment> attachments;

        }


        /**
         * 评论附件
         */
        @Data
        public static class Attachment {
            /**
             * 附件名称
             */
            @JSONField(name = "fileName")
            private String fileName;

            /**
             * 附件大小
             */
            @JSONField(name = "fileSize")
            private String fileSize;

            /**
             * 附件ID
             */
            @JSONField(name = "fileId")
            private String fileId;

            /**
             * 附件类型
             */
            @JSONField(name = "fileType")
            private String fileType;
        }

        /**
         * 任务列表
         */
        @Data
        public static class Task {

            /**
             * 任务节点ID
             */
            @JSONField(name = "taskId")
            private String taskId;

            /**
             * 任务处理人ID
             */
            @JSONField(name = "userId")
            private String userId;

            /**
             * 任务状态
             * <p>
             * NEW：未启动
             * <p>
             * RUNNING：处理中
             * <p>
             * PAUSED：暂停
             * <p>
             * CANCELED：取消
             * <p>
             * COMPLETED：完成
             * <p>
             * TERMINATED：终止
             */
            @JSONField(name = "status")
            private String status;

            /**
             * 结果
             * AGREE：同意
             * <p>
             * REFUSE：拒绝
             * <p>
             * REDIRECTED：转交
             */
            @JSONField(name = "result")
            private String result;

            /**
             * 开始时间
             */
            @JSONField(name = "createTime")
            private String createTime;

            /**
             * 结束时间
             */
            @JSONField(name = "finishTime")
            private String finishTime;

            /**
             * 任务URL
             */
            @JSONField(name = "mobileUrl")
            private String mobileUrl;

            /**
             * 任务URL
             */
            @JSONField(name = "pcUrl")
            private String pcUrl;

            @JSONField(name = "processInstanceId")
            private String processInstanceId;

            @JSONField(name = "activityId")
            private String activityId;

        }


        /**
         * 表单详情列表
         */
        @Data
        public static class formComponentValues {

            /**
             * 组件ID
             */
            @JSONField(name = "id")
            private String id;

            /**
             * 标签名
             */
            @JSONField(name = "name")
            private String name;

            /**
             * 标签值
             */
            @JSONField(name = "value")
            private String value;

            /**
             * 标签扩展值
             */
            @JSONField(name = "extValue")
            private String extValue;

            /**
             * 组件类型
             */
            @JSONField(name = "componentType")
            private String componentType;

            /**
             * 表单标签
             */
            private String bizAlias;
        }
    }
}
