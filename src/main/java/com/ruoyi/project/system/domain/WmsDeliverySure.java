package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;


/**
 * 出库确认记录对象 wms_delivery_sure
 *
 * @author Rem
 * @date 2023-04-17
 */
@Data
public class WmsDeliverySure {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 出通知单ID
     */
    @Excel(name = "出通知单ID")
    private String deliveryNoticeId;


    private String remark;

    private Integer result;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
