package com.ruoyi.common.constant;

import io.jsonwebtoken.Claims;

/**
 * 通用常量信息
 *
 * @author ruoyi
 */
public class Constants {
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * www主域
     */
    public static final String WWW = "www.";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = Claims.SUBJECT;

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi:";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS 远程方法调用
     */
    public static final String LOOKUP_LDAPS = "ldaps:";

    /**
     * 定时任务白名单配置（仅允许访问的包名，如其他需要可以自行添加）
     */
    public static final String[] JOB_WHITELIST_STR = {"com.ruoyi"};

    /**
     * 定时任务违规的字符
     */
    public static final String[] JOB_ERROR_STR = {"java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache", "com.ruoyi.common.utils.file", "com.ruoyi.framework.config"};
    /**
     * 公司根id
     */
    public static final String ROOT_NODE = "root";

    public static final String BREAKAGE = "breakage";
    public static final Integer YES = 1;
    public static final Integer NO = 0;

    /**
     * 成功代码
     */
    public static final int SUCCESS_CODE = 1;

    /**
     * 入库通知编号前缀
     */
    public static final String RECEIVE_NOTICE_CODE_PREFIX = "RK";

    /**
     * 出库通知编号前缀
     */
    public static final String SEND_NOTICE_CODE_PREFIX = "CK";

    /**
     * 质检通知编号前缀
     */
    public static final String EXAMINE_WORK_CODE_PREFIX = "ZJ";

    /**
     * 上架作业通知编号前缀
     */
    public static final String SHELF_NOTICE_CODE_PREFIX = "SJ";

    /**
     * 拣货作业通知编号前缀
     */
    public static final String PICK_NOTICE_CODE_PREFIX = "JH";

    /**
     * 库存盘点通知编号前缀
     */
    public static final String INVENTORY_PLAN_PREFIX = "PD";

    /**
     * 发运单号前缀
     */
    public static final String BILL_OF_LOADING_CODE_PREFIX = "FY";

    /**
     * 退货通知编号前缀
     */
    public static final String RETURN_NOTICE_CODE_PREFIX = "TH";

    /**
     * 报损单号前缀
     */
    public static final String BREAKAGE_DOC_CODE_PREFIX = "BS";

    /**
     * 不合格品处置单号前缀
     */
    public static final String DISPOSAL_CODE_PREFIX = "NP";

    /**
     * 出库通知检查项单据类型字典CODE
     */
    public static final String DELIVERY_CHECK_BILL_TYPE = "delivery_check_bill_type";

    /**
     * 需要生成发运单的业务类型字典CODE
     */
    public static final String GENERATE_BILL_OF_LOADING_BUSINESS_TYPE = "generate_bill_of_loading_business_type";

    public static final Long SYSTEM_USER_ID = -1L;

    /**
     * 月结的初始月份（从哪年哪月开始）
     */
    public static final String WMS_MONTH_CHECK_OUT_INIT = "wms_month_check_out_init";

    /*
     * 部门主管
     */
    public static final String DEPT_MANAGER = "dept_manager";


}
