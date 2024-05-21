package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 物料仓储属性对象 wms_item_attribute
 *
 * @author ruoyi
 * @date 2023-04-11
 */
@Data
public class WmsItemAttribute {
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
     * 物料ID
     */
    @Excel(name = "物料编码")
    private String itemCode;


    /**
     * 上限数量
     */
    @Excel(name = "上限数量")
    private Long upQuality;

    /**
     * 下限数量
     */
    @Excel(name = "下限数量")
    private Long downQuality;

    /**
     * 提前预警天数
     */
    @Excel(name = "提前预警天数")
    private Integer remindDay;

    /**
     * 限量状态（0-正常 1-超过上限 2-超过下限）
     */
    @Excel(name = "限量状态", readConverterExp = "0=-正常,1=-超过上限,2=-超过下限")
    private Integer limitedStatus;


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
