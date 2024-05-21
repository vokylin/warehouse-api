package com.ruoyi.project.common.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: hlp
 * @Date: 2022-05-12 20:38
 **/
@Data
public class WmsCancelDTO {
    /**
     * 业务ID
     */
    private String businessId;
    /**
     * 单据类型
     */
    private String businessType;
    /**
     * 生产的任务ID
     */
    private String taskId;

}
