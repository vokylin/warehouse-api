package com.ruoyi.common.invoker;


import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.invoker.entity.request.AccessTokenRequest;
import com.ruoyi.common.invoker.entity.result.*;
import com.ruoyi.common.utils.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 审批流程调用程序
 *
 * @author zyh
 * @date 2022/07/11
 */
@Slf4j
@Component
public class ApprovalProcessInvoker {

    /**
     * 旧HOST
     */
    private static final String OLD_HOST = "https://oapi.dingtalk.com/topapi/v2";

    /**
     * HOST
     */
    private static final String HOST = "https://api.dingtalk.com";

    /**
     * 获得访问令牌路径
     */
    private static final String GET_ACCESS_TOKEN_PATH = "/v1.0/oauth2/accessToken";

    /**
     * 提交审批流程路径
     */
    private static final String SUBMIT_APPROVAL_PROCESS_PATH = "/v1.0/workflow/processInstances";

    /**
     * 获取用户ID路径
     */
    private static final String GET_USER_ID_PATH = "/user/getbymobile";

    /**
     * 获取用户信息
     */
    private static final String GET_USER_INFO_PATH = "/user/get";

    /**
     * 获取指定用户的所有父部门列表
     */
    private static final String GET_PARENT_DEPT_PATH = "/department/listparentbyuser";

    /**
     * 撤销审批流程路径
     */
    private static final String WITHDRAW_APPROVAL_PROCESS_PATH = "/v1.0/workflow/processInstances/terminate";

    /**
     * 获取审批实例
     */
    private static final String GET_PROCESS_INSTANCE = "/v1.0/workflow/processInstances";

    private static final String HEADER_ACCESS_TOKEN = "x-acs-dingtalk-access-token";


    /**
     * 获取AccessToken
     *
     * @param appKey    应用key
     * @param appSecret 应用密钥
     * @return {@link String}
     */
    public String getAccessToken(String appKey, String appSecret) {
        AccessTokenRequest accessTokenRequest = new AccessTokenRequest(appKey, appSecret);
        String responseStr = HttpRequest.post(HOST + GET_ACCESS_TOKEN_PATH).body(JSON.toJSONString(accessTokenRequest)).execute().body();
        if (StringUtils.isBlank(responseStr)) {
            return null;
        }
        AccessTokenResult accessTokenResult = JSON.parseObject(responseStr, AccessTokenResult.class);
        if (null == accessTokenResult) {
            return null;
        }
        // 解析返回结果
        return accessTokenResult.getAccessToken();
    }


    /**
     * 提交审批流程
     *
     * @param bodyJsonStr 身体json str
     * @param appKey      应用关键
     * @param appSecret   应用程序秘密
     * @return {@link SubmitApprovalProcessResult}
     */
    public SubmitApprovalProcessResult submitApprovalProcess(String bodyJsonStr, String appKey, String appSecret) {
        String accessToken = this.getAccessToken(appKey, appSecret);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        String responseStr = HttpRequest.post(HOST + SUBMIT_APPROVAL_PROCESS_PATH).body(bodyJsonStr).header(HEADER_ACCESS_TOKEN, accessToken).execute().body();
        log.info("提交审批流程返回结果：{}", responseStr);
        if (StringUtils.isBlank(responseStr)) {
            return null;
        }
        // 解析返回结果
        return JSON.parseObject(responseStr, SubmitApprovalProcessResult.class);
    }


    /**
     * 根据手机号获取用户ID
     *
     * @param phoneNumber 电话号码
     * @return {@link UserIdResult}
     */
    public UserIdResult getUserIdByPhone(String phoneNumber, String appKey, String appSecret) {
        String accessToken = this.getAccessToken(appKey, appSecret);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        String requestUrl = OLD_HOST + GET_USER_ID_PATH + "?access_token=" + accessToken;

        Map<String, String> bodys = new HashMap<>();
        bodys.put("mobile", phoneNumber);
        String bodyJsonStr = JSON.toJSONString(bodys);

        String responseStr = "";
        try {
            responseStr = HttpUtil.postJson(requestUrl, bodyJsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isBlank(responseStr)) {
            return null;
        }

        // 解析返回结果
        return JSON.parseObject(responseStr, UserIdResult.class);
    }

    /**
     * 通过用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return {@link UserInfoResult}
     */
    public UserInfoResult getUserInfo(String userId, String appKey, String appSecret) {
        String accessToken = this.getAccessToken(appKey, appSecret);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        String requestUrl = OLD_HOST + GET_USER_INFO_PATH + "?access_token=" + accessToken;

        Map<String, String> bodys = new HashMap<>();
        bodys.put("userid", userId);
        String bodyJsonStr = JSON.toJSONString(bodys);

        String responseStr = "";
        try {
            responseStr = HttpUtil.postJson(requestUrl, bodyJsonStr);
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isBlank(responseStr)) {
            return null;
        }

        // 解析返回结果
        return JSON.parseObject(responseStr, UserInfoResult.class);
    }

    /**
     * 获取指定用户的所有父部门列表
     *
     * @param userId 用户ID
     * @return {@link UserDepartmentListResult}
     */
    public UserDepartmentListResult getParentDept(String userId, String appKey, String appSecret) {
        String accessToken = this.getAccessToken(appKey, appSecret);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        String requestUrl = OLD_HOST + GET_PARENT_DEPT_PATH + "?access_token=" + accessToken;

        Map<String, String> bodys = new HashMap<>();
        bodys.put("userid", userId);
        String bodyJsonStr = JSON.toJSONString(bodys);

        String responseStr = "";
        try {
            responseStr = HttpUtil.postJson(requestUrl, bodyJsonStr);
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isBlank(responseStr)) {
            return null;
        }

        // 解析返回结果
        return JSON.parseObject(responseStr, UserDepartmentListResult.class);
    }


    /**
     * 撤销审批流程
     *
     * @param bodyJsonStr 请求体json str
     * @return {@link SubmitApprovalProcessResult}
     */
    public WithdrawApprovalProcessResult withdrawApprovalProcess(String bodyJsonStr, String appKey, String appSecret) {
        String accessToken = this.getAccessToken(appKey, appSecret);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        String responseStr = HttpRequest.post(HOST + WITHDRAW_APPROVAL_PROCESS_PATH).body(bodyJsonStr).header(HEADER_ACCESS_TOKEN, accessToken).execute().body();
        if (StringUtils.isBlank(responseStr)) {
            return null;
        }

        // 解析返回结果
        return JSON.parseObject(responseStr, WithdrawApprovalProcessResult.class);
    }


    /**
     * 获取流程实例
     *
     * @param processInstanceId 流程实例id
     * @param appKey            应用关键
     * @param appSecret         应用程序秘密
     * @return {@link ProcessInstanceResult}
     */
    public ProcessInstanceResult getProcessInstance(String processInstanceId, String appKey, String appSecret) {
        String accessToken = this.getAccessToken(appKey, appSecret);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        String requestUrl = HOST + GET_PROCESS_INSTANCE + "?processInstanceId=" + processInstanceId;
        String responseStr = HttpRequest.get(requestUrl).header(HEADER_ACCESS_TOKEN, accessToken).execute().body();
        if (StringUtils.isBlank(responseStr)) {
            return null;
        }
        // 解析返回结果
        return JSON.parseObject(responseStr, ProcessInstanceResult.class);
    }
}
