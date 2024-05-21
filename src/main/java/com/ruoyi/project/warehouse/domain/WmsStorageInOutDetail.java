package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出入库记录详情对象 wms_storage_in_out_detail
 *
 * @author Rem
 * @date 2023-04-08
 */
@Data
public class WmsStorageInOutDetail {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 记录ID
     */
    @Excel(name = "记录ID")
    private String recodeId;

    /**
     * 仓库ID
     */
    @Excel(name = "仓库ID")
    private String warehouseId;

    @Excel(name = "库位ID")
    private String locationId;

    /**
     * 库别（仓库编码）
     */
    @Excel(name = "库别", readConverterExp = "仓=库编码")
    private String warehouseCode;

    /**
     * 物料编码
     */
    @Excel(name = "物料编码")
    private String itemCode;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private BigDecimal quantity;
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

    private String remark;
}
