package com.ruoyi.common.invoker.entity.result;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 用户id结果
 *
 * @author REM
 * @date 2023/05/17
 */
@Data
public class UserIdResult {

    /**
     * 错误代码
     */
    @JSONField(name = "errcode")
    private Integer errCode;

    /**
     * 结果
     */
    @JSONField(name = "result")
    private Result result;

    @Data
    public static class Result {

        /**
         * 用户ID
         */
        @JSONField(name = "userid")
        private String userId;
    }

}
