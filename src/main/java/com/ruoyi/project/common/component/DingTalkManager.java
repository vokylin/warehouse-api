package com.ruoyi.project.common.component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.BreakageDocType;
import com.ruoyi.common.enums.ScrapReasonEnum;
import com.ruoyi.common.exception.business.BusinessException;
import com.ruoyi.common.invoker.ApprovalProcessInvoker;
import com.ruoyi.common.invoker.entity.DingCallbackCrypto;
import com.ruoyi.common.invoker.entity.enums.ApprovalInstanceEndTypeEnum;
import com.ruoyi.common.invoker.entity.enums.ApprovalResultEnum;
import com.ruoyi.common.invoker.entity.enums.ApprovalStatusEnum;
import com.ruoyi.common.invoker.entity.enums.EventTypeEnum;
import com.ruoyi.common.invoker.entity.form.ApprovalForm;
import com.ruoyi.common.invoker.entity.form.RowData;
import com.ruoyi.common.invoker.entity.form.TableForm;
import com.ruoyi.common.invoker.entity.request.ApprovalProcessRequestBody;
import com.ruoyi.common.invoker.entity.result.*;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysUserService;
import com.ruoyi.project.warehouse.domain.BreakageDocVO;
import com.ruoyi.project.warehouse.domain.DingWorkflow;
import com.ruoyi.project.warehouse.domain.WmsBreakageApproval;
import com.ruoyi.project.warehouse.domain.WmsBreakageDocDetail;
import com.ruoyi.project.warehouse.service.DingWorkflowService;
import com.ruoyi.project.warehouse.service.IWmsBreakageDocService;
import com.ruoyi.project.warehouse.service.WmsBreakageApprovalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 钉钉Manager
 *
 * @author REM
 * @date 2023/05/23
 */
@Slf4j
@Component
public class DingTalkManager {

    /**
     * 企业应用的appKey
     */
    @Value("${dingtalk.app-key}")
    private String APP_KEY;

    /**
     * 企业应用的appSecret
     */
    @Value("${dingtalk.app-secret}")
    private String APP_SECRET;

    /**
     * 企业应用的EncodingAESKey
     */
    @Value("${dingtalk.aes-key}")
    private String AES_KEY;

    /**
     * 企业应用的Token
     */
    @Value("${dingtalk.aes-token}")
    private String AES_TOKEN;

    private static final String EVENT_TYPE = "EventType";

    private static final String ENCRYPT = "encrypt";


    /**
     * 审批流程code
     */
    @Value("${dingtalk.process-code}")
    private String PROCESS_CODE;

    @Autowired
    private ApprovalProcessInvoker approvalProcessInvoker;

    @Autowired
    private DingWorkflowService dingWorkflowService;

    @Autowired
    private IWmsBreakageDocService wmsBreakageDocService;

    @Autowired
    private WmsBreakageApprovalService wmsBreakageApprovalService;

    @Autowired
    private ISysUserService userService;

    /**
     * 回调处理
     *
     * @param msgSignature 味精签名
     * @param timeStamp    时间戳
     * @param nonce        现时标志
     * @param json         json
     * @return {@link Map}<{@link String}, {@link String}>
     * @throws DingCallbackCrypto.DingTalkEncryptException 丁说加密异常
     */
    public Map<String, String> callBackDispose(String msgSignature, String timeStamp, String nonce, JSONObject json) throws Exception {

        // 钉钉加密解密工具类
        DingCallbackCrypto callbackCrypto = new DingCallbackCrypto(AES_TOKEN, AES_KEY, APP_KEY);
        // 获取加密的消息体
        String encryptMsg = json.getString(ENCRYPT);
        // 解密消息体
        String decryptMsg = callbackCrypto.getDecryptMsg(msgSignature, timeStamp, nonce, encryptMsg);
        // 获取事件类型
        JSONObject eventJson = JSON.parseObject(decryptMsg);
        String eventTypeStr = eventJson.getString(EVENT_TYPE);
        // 获取事件类型枚举
        EventTypeEnum eventType = EventTypeEnum.getByCode(eventTypeStr);
        if (eventType == null) {
            log.info("发生了：{} 事件, 没有处理方式", eventTypeStr);
            return null;
        }
        // 根据EventType分类处理
        switch (eventType) {
            case CHECK_URL:
                log.info("测试回调url的正确性");
                break;
            case BPMS_TASK_CHANGE:
//                this.approvalTaskDispose(decryptMsg);
                log.info("审批任务开始、结束、转交事件");
                break;
            case BPMS_INSTANCE_CHANGE:
                this.approvalInstanceDispose(decryptMsg);
                break;
            default:
                log.info("发生了：{} 事件, 没有处理方式", eventTypeStr);
                break;
        }
        return callbackCrypto.getSuccessBack();
    }

    /**
     * 审批实例回调处理
     *
     * @param decryptMsg 解密消息
     */
    private void approvalInstanceDispose(String decryptMsg) throws Exception {
        CallbackApprovalInstanceEndResult callbackApprovalInstanceEndResult = JSON.parseObject(decryptMsg, CallbackApprovalInstanceEndResult.class);
        log.info("审批实例开始、结束、转交事件-----> {}", callbackApprovalInstanceEndResult);
        String result = callbackApprovalInstanceEndResult.getResult();
        ApprovalInstanceEndTypeEnum resultType = ApprovalInstanceEndTypeEnum.getByCode(result);
        if (null != resultType) {
            switch (resultType) {
                // 审批任务开始
                case START:
                    break;

                // 完成
                case FINISH:
                    completeApproval(callbackApprovalInstanceEndResult.getProcessInstanceId());
                    break;

                // 说明当前节点有多个审批人并且是或签，其中一个人执行了审批，其他审批人会推送cancel类型事件。
                case CANCEL:
                    break;

                // 转交
                case REDIRECT:
                    break;

                // 发起人撤销审批单
                case TERMINATE:
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 审批任务回调处理
     *
     * @param decryptMsg 解密消息
     */
    private void approvalTaskDispose(String decryptMsg) {
        CallbackApprovalTaskEndResult callbackApprovalTaskEndResult = JSON.parseObject(decryptMsg, CallbackApprovalTaskEndResult.class);
        log.info("审批实例开始、结束 事件------->{}", callbackApprovalTaskEndResult);
        String result = callbackApprovalTaskEndResult.getResult();
        // 同意
        if (result.equals(ApprovalResultEnum.AGREE.getCode())) {
            log.info("审批任务回调处理：同意");
        } else {
            // 拒绝
            log.info("审批任务回调处理：拒绝");
        }
        ProcessInstanceResult processInstance = approvalProcessInvoker.getProcessInstance(callbackApprovalTaskEndResult.getProcessInstanceId(), APP_KEY, APP_SECRET);
        log.info("审批任务完成回调, 审批实例详情-----> {}", processInstance);
    }

    /**
     * 提交审批
     */
    public String submitApproval(BreakageDocVO breakageDocVO) {

        String createBy = breakageDocVO.getCreateBy();
        SysUser sysUser = userService.selectUserById(Long.valueOf(createBy));
        if (null == sysUser) {
            throw new BusinessException("用户信息不存在");
        }
        // 获取用户ID
        UserIdResult userIdByPhone = approvalProcessInvoker.getUserIdByPhone(sysUser.getPhonenumber(), APP_KEY, APP_SECRET);
        String userId = userIdByPhone.getResult().getUserId();
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("获取钉钉用户ID失败");
        }

        // 获取用户详情
        UserDepartmentListResult departmentListResult = approvalProcessInvoker.getParentDept(userId, APP_KEY, APP_SECRET);
        if (null == departmentListResult) {
            throw new BusinessException("获取钉钉用户部门失败");
        }

        List<UserDepartmentListResult.DeptOrderList> deptOrderList = departmentListResult.getResult().getDeptOrderList();
        // 获取部门ID
        String deptId = deptOrderList.get(0).getDeptIds().get(0);

        List<WmsBreakageDocDetail> wmsBreakageDocDetailList = breakageDocVO.getWmsBreakageDocDetailList();
        if (CollectionUtils.isEmpty(wmsBreakageDocDetailList)) {
            throw new BusinessException("报废单明细不能为空");
        }
        ApprovalForm forms = new ApprovalForm();
        forms.addForm("报废类别", Objects.requireNonNull(BreakageDocType.getEnumByCode(breakageDocVO.getType())).getInfo());
        forms.addForm("其他说明", breakageDocVO.getDescription());
        // 产成品报废
        if (breakageDocVO.getType().equals(BreakageDocType.FINISHED_PRODUCT.getCode())) {
            forms.addForm("产品报废原因", Objects.requireNonNull(ScrapReasonEnum.getEnumByCode(breakageDocVO.getReason())).getInfo());
            TableForm tableForm = new TableForm();
            wmsBreakageDocDetailList.forEach(item -> {
                RowData rowData = new RowData();
                rowData.addData("产成品名称", item.getItemName());
                rowData.addData("规格/型号", item.getSpec());
                rowData.addData("批号", item.getBatchNo());
                rowData.addData("当前库存数量", item.getTotalQuantity().stripTrailingZeros().toPlainString());
                rowData.addData("报废数量", item.getQuantity().stripTrailingZeros().toPlainString());
                rowData.addData("单位", item.getUnit());
                rowData.addData("责任部门", breakageDocVO.getOfficeName());
                rowData.addTimeForm("有效期始", DateUtils.dateTime(item.getProductDate()), "有效期终", DateUtils.dateTime(item.getExpireDate()));
                tableForm.addRow(rowData);
            });
            forms.addForm("报废金额合计（元）", BigDecimal.ZERO.stripTrailingZeros().toPlainString());
            forms.addTableForm("产成品明细", tableForm);
        } else {
            // 原物料报废
            forms.addForm("原物料报废原因", Objects.requireNonNull(ScrapReasonEnum.getEnumByCode(breakageDocVO.getReason())).getInfo());
            BigDecimal totalAmount = BigDecimal.ZERO;
            TableForm tableForm = new TableForm();
            for (WmsBreakageDocDetail item : wmsBreakageDocDetailList) {
                // 金额保留2位小数，四舍五入
                BigDecimal amount = item.getQuantity().multiply(item.getPrice()).setScale(2, RoundingMode.HALF_UP);
                totalAmount = totalAmount.add(amount);
                RowData rowData = new RowData();
                rowData.addData("物料名称", item.getItemName());
                rowData.addData("规格/型号", item.getSpec());
                rowData.addData("批号", item.getBatchNo());
                rowData.addData("当前库存数量", item.getTotalQuantity().stripTrailingZeros().toPlainString());
                rowData.addData("报废数量", item.getQuantity().stripTrailingZeros().toPlainString());
                rowData.addData("单位", item.getUnit());
                rowData.addData("报废总金额（元）", amount.stripTrailingZeros().toPlainString());
                rowData.addData("责任部门", breakageDocVO.getOfficeName());
                rowData.addTimeForm("有效期始", DateUtils.dateTime(item.getProductDate()), "有效期终", DateUtils.dateTime(item.getExpireDate()));
                tableForm.addRow(rowData);
            }
            forms.addForm("报废金额合计（元）", totalAmount.stripTrailingZeros().toPlainString());
            forms.addTableForm("原物料明细", tableForm);
        }

        // 构建审批实例
        ApprovalProcessRequestBody body = new ApprovalProcessRequestBody(forms, PROCESS_CODE, userId, Long.valueOf(deptId));
        log.info("提交审批参数：{}", JSON.toJSONString(body));
        SubmitApprovalProcessResult submitApprovalProcessResult = approvalProcessInvoker.submitApprovalProcess(JSON.toJSONString(body), APP_KEY, APP_SECRET);
        log.info("提交审批结果：{}", submitApprovalProcessResult);

        if (null == submitApprovalProcessResult) {
            return null;
        }
        if (null == submitApprovalProcessResult.getProcessInstanceId()) {
            return null;
        }

        // 保存审批流程记录
        DingWorkflow dingWorkflow = new DingWorkflow();
        dingWorkflow.setId(IdUtils.fastSimpleUUID());
        dingWorkflow.setBusinessId(breakageDocVO.getId());
        dingWorkflow.setBusinessTag(Constants.BREAKAGE);
        dingWorkflow.setBusinessCode(breakageDocVO.getType());
        dingWorkflow.setApplyUserId(breakageDocVO.getCreateBy());
        dingWorkflow.setApplyUserName(breakageDocVO.getApplicant());
        dingWorkflow.setApplyTime(DateUtils.getNowDate());
        dingWorkflow.setRemark(breakageDocVO.getReason());
        dingWorkflow.setEnterpriseKey(breakageDocVO.getCompanyId());
        dingWorkflow.setProcessCode(PROCESS_CODE);
        dingWorkflow.setDingdingId(submitApprovalProcessResult.getProcessInstanceId());
        dingWorkflowService.insertSelective(dingWorkflow);
        return submitApprovalProcessResult.getProcessInstanceId();
    }


    /**
     * 审批实例完成
     *
     * @param processInstanceId 回调实例结果批准
     * @throws Exception 异常
     */
    public void completeApproval(String processInstanceId) throws Exception {

        // 获取审批实例详情
        ProcessInstanceResult processInstance = approvalProcessInvoker.getProcessInstance(processInstanceId, APP_KEY, APP_SECRET);
        log.info("审批实例完成, 审批实例详情-----> {}", JSON.toJSONString(processInstance));
        if (null == processInstance) {
            throw new BusinessException("审批实例不存在");
        }

        DingWorkflow dingWorkflow = dingWorkflowService.selectByInstanceId(processInstanceId);
        if (null == dingWorkflow) {
            throw new BusinessException("审批流程记录不存在");
        }
        String status = processInstance.getResult().getStatus();
        // 审批实例状态：COMPLETED-完成，CANCELED-取消，TERMINATED-终止
        if (status.equals(ApprovalStatusEnum.CANCELED.getCode()) || status.equals(ApprovalStatusEnum.TERMINATED.getCode())) {
            log.info("审批实例取消或终止");
            wmsBreakageDocService.cancel(dingWorkflow.getBusinessId());
        } else if (status.equals(ApprovalStatusEnum.COMPLETED.getCode())) {
            ProcessInstanceResult.Result result = processInstance.getResult();
            String finalResult = result.getResult();
            if (finalResult.equals(ApprovalResultEnum.AGREE.getCode())) {
                log.info("审批实例完成, 审批实例结果-----> {}", "同意");
                wmsBreakageDocService.approved(dingWorkflow.getBusinessId());

                // 解析数据
                WmsBreakageApproval wmsBreakageApproval = this.parseProcessInstanceResult(processInstance);
                if (null == wmsBreakageApproval) {
                    throw new BusinessException("解析审批实例详情失败");
                }
                // 保存审批节点中的审批人
                wmsBreakageApproval.setBreakageDocId(dingWorkflow.getBusinessId());
                wmsBreakageApprovalService.insertSelective(wmsBreakageApproval);
            } else {
                log.info("审批实例完成, 审批实例结果-----> {}", "拒绝");
                wmsBreakageDocService.rejected(dingWorkflow.getBusinessId());
            }
        }
    }

    /**
     * 解析审批实例详情
     *
     * @param processInstance 流程实例
     * @return {@link WmsBreakageApproval}
     */
    public WmsBreakageApproval parseProcessInstanceResult(ProcessInstanceResult processInstance) {
        // 获取审批实例操作记录
        List<ProcessInstanceResult.Result.OperationRecordsVo> operationRecords = processInstance.getResult().getOperationRecords();
        log.info("获取审批实例操作记录：{}", JSON.toJSONString(operationRecords));
        if (CollectionUtils.isEmpty(operationRecords)) {
            return null;
        }
        WmsBreakageApproval wmsBreakageApproval = new WmsBreakageApproval();
        // 由于审批流程中，第一个节点是发起人，所以审批流程中的第二个节点才是审批人, 并且审批流程固定, 所以这里写死
        List<String> userList = new ArrayList<>();
        // 获取第 2-4 条记录
        List<ProcessInstanceResult.Result.OperationRecordsVo> operationRecordsVos = operationRecords.subList(1, 4);
        operationRecordsVos.forEach(
                operationRecordsVo -> {
                    UserInfoResult userInfo = approvalProcessInvoker.getUserInfo(operationRecordsVo.getUserId(), APP_KEY, APP_SECRET);
                    log.info("获取用户详情：{}", JSON.toJSONString(userInfo));
                    if (null == userInfo) {
                        throw new BusinessException("获取用户详情失败");
                    }
                    userList.add(userInfo.getResult().getName());
                }
        );

        // 填表部门主管
        wmsBreakageApproval.setDeptSupervisor(userList.get(0));
        // 品管
        wmsBreakageApproval.setQualitySupervisor(userList.get(1));
        // 质量部主管
        wmsBreakageApproval.setQuantityDeptSupervisor(userList.get(2));
        return wmsBreakageApproval;
    }

    /**
     * 获取审批实例详情
     *
     * @param processInstanceId 流程实例id
     * @return {@link ProcessInstanceResult}
     * @throws Exception 异常
     */
    public ProcessInstanceResult getProcessInstance(String processInstanceId) throws Exception {
        return approvalProcessInvoker.getProcessInstance(processInstanceId, APP_KEY, APP_SECRET);
    }

    public UserIdResult getUserId(String phoneNumber) throws Exception {
        return approvalProcessInvoker.getUserIdByPhone(phoneNumber, APP_KEY, APP_SECRET);
    }
}
