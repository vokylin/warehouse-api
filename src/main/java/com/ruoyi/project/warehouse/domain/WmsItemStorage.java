package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 库存汇总对象 wms_item_storage
 *
 * @author Rem
 * @date 2023-04-08
 */
@Data
public class WmsItemStorage {
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

    private String companyName;

    /**
     * 仓库ID
     */
    @Excel(name = "仓库ID")
    private String warehouseId;

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
     * 单位
     */
    @Excel(name = "单位")
    private String unit;

    /**
     * 规格型号
     */
    private String spec;

    /**
     * 总数量
     */
    @Excel(name = "总数量")
    private BigDecimal quantity;

    /**
     * 实时单价
     */
    @Excel(name = "实时单价")
    private BigDecimal actualPrice;

    /**
     * 库存金额
     */
    @Excel(name = "库存金额")
    private BigDecimal storagePrice;

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

    private String relateId;

    private List<WmsItemStorageDetail> children;

    private List<String> warehouseIdList;

    private Integer workStatus;

    private Integer storageStatus;

    private String batchNo;
}
