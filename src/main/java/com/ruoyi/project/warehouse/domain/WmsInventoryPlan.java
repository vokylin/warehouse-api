package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;


/**
 * wms库存计划
 * 盘点计划对象 wms_inventory_plan
 *
 * @author Rem
 * @date 2023-05-21
 */
@Data
public class WmsInventoryPlan {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    private String planName;

    /**
     * 公司id
     */
    @Excel(name = "公司id")
    private String companyId;

    /**
     * 仓库ids
     */
    @Excel(name = "仓库ids")
    private String warehouseIds;

    /**
     * 盘点方法 (明盘、盲盘)
     */
    @Excel(name = "盘点方法 (明盘、盲盘)")
    private Integer inventoryMethod;

    /**
     * 库存状态（合格、残次、特采）
     */
    @Excel(name = "库存状态", readConverterExp = "合=格、残次、特采")
    private String storageStatus;

    /**
     * 计划时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "计划时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planTime;

    /**
     * 状态(待盘点、盘点中、审批中、已通过、未通过)
     */
    @Excel(name = "状态(待盘点、盘点中、审批中、已通过、未通过)")
    private Integer status;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 审核人ID
     */
    @Excel(name = "审核人ID")
    private String reviewerId;

    /**
     * 审核人
     */
    @Excel(name = "审核人")
    private String reviewedBy;

    /**
     * 创建者
     */
    private Long createBy;

    private String createByName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;


    /**
     * 更新者
     */
    private Long updateBy;

    private String remark;

}
