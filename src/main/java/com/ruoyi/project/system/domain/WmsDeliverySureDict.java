package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 出库确认项对象 wms_delivery_sure_dict
 *
 * @author Rem
 * @date 2023-04-12
 */
@Data
public class WmsDeliverySureDict {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private String companyId;

    /**
     * 仓库ID
     */
    @Excel(name = "仓库ID")
    private String warehouseId;

    /**
     * 检查项
     */
    @Excel(name = "检查项")
    private String checkItem;

    /**
     * 检查要求
     */
    @Excel(name = "检查要求")
    public String checkRequirement;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sort;

    /**
     * 温层
     */
    @Excel(name = "温层")
    private String thermosphere;


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


    private String businessType;
}
