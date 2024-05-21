package com.ruoyi.common.invoker.entity.result;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;


/**
 * 用户信息结果
 *
 * @author REM
 * @date 2023/05/17
 */
@Data
public class UserInfoResult {

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

    /**
     * 错误消息
     */
    @JSONField(name = "errmsg")
    private String errMsg;


    /**
     * 结果
     *
     * @author REM
     * @date 2023/05/17
     */
    @Data
    public static class Result {

        @JSONField(name = "dept_order_list")
        private List<DeptOrderList> deptOrderList;

        @JSONField(name = "name")
        private String name;
    }


    /**
     * 部门排序列表
     *
     * @author REM
     * @date 2023/05/17
     */
    @Data
    public static class DeptOrderList {

        @JSONField(name = "dept_id")
        private String deptId;

        @JSONField(name = "order")
        private String order;

    }

}
