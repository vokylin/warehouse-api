package com.ruoyi.common.invoker.entity.result;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 访问令牌结果
 *
 * @author REM
 * @date 2023/05/17
 */
@Data
public class AccessTokenResult {

    /**
     * 访问令牌
     */
    @JSONField(name = "accessToken")
    private String accessToken;

    /**
     * 过期时间
     */
    @JSONField(name = "expiresIn")
    private Integer expiresIn;

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

}
