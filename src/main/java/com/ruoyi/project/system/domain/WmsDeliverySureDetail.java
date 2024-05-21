package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 出库确认记录详情对象 wms_delivery_sure_detail
 *
 * @author Rem
 * @date 2023-04-17
 */
@Data
public class WmsDeliverySureDetail {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 确认ID
     */
    @Excel(name = "确认ID")
    private String sureId;

    /**
     * 检查项ID
     */
    @Excel(name = "检查项ID")
    private String checkId;

    /**
     * 检查项目
     */
    @Excel(name = "检查项目")
    private String checkItem;

    /**
     * 要求
     */
    @Excel(name = "要求")
    private String checkRequirement;

    /**
     * 是否合格：是/否
     */
    @Excel(name = "是否合格：是/否")
    private Integer result;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sort;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
