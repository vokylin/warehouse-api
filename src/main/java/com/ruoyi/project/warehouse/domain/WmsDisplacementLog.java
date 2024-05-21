package com.ruoyi.project.warehouse.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 移位日志对象 wms_displacement_log
 *
 * @author Rem
 * @date 2023-04-25
 */
@Data
public class WmsDisplacementLog {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 仓库ID
     */
    @Excel(name = "仓库ID")
    private String warehouseId;

    private String companyId;

    private String warehouseName;

    /**
     * 物料编码
     */
    @Excel(name = "物料编码")
    private String itemCode;

    /**
     * 物料名称
     */
    @Excel(name = "物料名称")
    private String itemName;

    /**
     * 批次号
     */
    @Excel(name = "批次号")
    private String batchNo;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /**
     * 原库位
     */
    @Excel(name = "原库位")
    private String originalLocation;

    private String originalLocationName;

    /**
     * 目标库位
     */
    @Excel(name = "目标库位")
    private String targetLocation;

    private String targetLocationName;

    private String unit;

    private String spec;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUser;

    private Date createTime;

    private String createUserName;


    private List<String> warehouseIdList;

}
