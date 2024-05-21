package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 库存详情对象 wms_item_storage_detail
 *
 * @author Rem
 * @date 2023-04-08
 */
@Data
public class WmsItemStorageDetail {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 库存ID
     */
    @Excel(name = "库存ID")
    private String storageId;

    /**
     * 物料批号
     */
    @Excel(name = "物料批号")
    private String batchNo;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /**
     * 货位ID
     */
    @Excel(name = "货位ID")
    private String locationId;

    private String locationName;

    /**
     * 库存状态（0-合格，1-残次）'
     */
    @Excel(name = "库存状态", readConverterExp = "0=-合格，1-残次")
    private Integer storageStatus;

    /**
     * 作业状态（0-正常，1-作业中）
     */
    @Excel(name = "作业状态", readConverterExp = "0=-正常，1-作业中")
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

    /**
     * 温层
     */
    @Excel(name = "温层")
    private String thermosphere;

    /**
     * 关联业务类型（未关联、上架中、待发货...）
     */
    @Excel(name = "关联业务类型", readConverterExp = "未=关联、上架中、待发货...")
    private Integer relateType;

    private Integer[] relateTypeList;

    /**
     * 关联业务ID
     */
    @Excel(name = "关联业务ID")
    private String relateId;
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

    private String itemCode;

    private String itemName;

    private BigDecimal actualPrice;

    /**
     * 库存金额
     */
    @Excel(name = "库存金额")
    private BigDecimal storagePrice;

    private String warehouseId;

    private String warehouseName;

    private Boolean isStatusEdit;

    /**
     * 分配数量
     */
    private BigDecimal allotQuantity;

    private String targetLocationId;

    private String targetWarehouseId;

    private Integer shelfLifeStatus;

    private Integer limitedStatus;

    private String unit;

    private String spec;

    private List<String> warehouseIdList;

    private List<Integer> storageStatusList;

    private String deliveryNoticeDetailId;

    private String itemCodeOrName;

    private String badCauses;

    private String itemType;
}
