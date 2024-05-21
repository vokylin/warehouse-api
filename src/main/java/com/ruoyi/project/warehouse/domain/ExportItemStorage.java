package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存汇总对象 wms_item_storage
 *
 * @author Rem
 * @date 2023-04-08
 */
@Data
public class ExportItemStorage {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 公司ID
     */
    private String companyId;

    private String companyName;

    /**
     * 仓库ID
     */
    private String warehouseId;

    @Excel(name = "仓库")
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
     * 总数量
     */
    private BigDecimal totalQuantity;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /**
     * 单位
     */
    @Excel(name = "物料单位")
    private String unit;

    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String spec;

    /**
     * 批次号
     */
    @Excel(name = "批次号")
    private String batchNo;

    /**
     * 货位ID
     */
    private String locationId;

    /**
     * 货位名称
     */
    @Excel(name = "货位")
    private String locationName;

    /**
     * 实时单价
     */
    private BigDecimal actualPrice;

    /**
     * 库存金额
     */
    @Excel(name = "库存金额")
    private BigDecimal storagePrice;

    /**
     * 库存状态（0-合格，1-残次）'
     */
    private Integer storageStatus;

    /**
     * 作业状态（0-正常，1-作业中）
     */
    private Integer workStatus;

    /**
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productDate;

    /**
     * 过期日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "过期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireDate;

    private Integer shelfLifeStatus;

    /**
     * 限量状态
     */
    private Integer limitedStatus;

}
