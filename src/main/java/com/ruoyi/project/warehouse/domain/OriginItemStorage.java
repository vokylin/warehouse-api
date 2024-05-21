package com.ruoyi.project.warehouse.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 物料存储
 */
@Data
public class OriginItemStorage {
    private String id;

    /**
     * 公司
     */
    private String companyId;

    /**
     * 仓库
     */
    private String warehouseId;

    /**
     * 物料
     */
    private String itemId;

    private String itemCode;

    private String itemName;

    /**
     * 单位
     */
    private String unit;

    /**
     * 规格型号
     */
    private String spec;

    /**
     * 货位
     */
    private String locationId;

    /**
     * 容器
     */
    private String containerId;

    /**
     * 关联id
     */
    private String relateId;

    /**
     * 关联业务类型
     */
    private String relateType;

    /**
     * 批次号
     */
    private String lot;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 作业状态（0-正常，1-作业中，2-锁定）
     */
    private String workStatus;

    /**
     * 库存状态（0-合格，1-残次）
     */
    private String storageStatus;

    /**
     * 收货日期
     */
    private Date receiveDate;

    /**
     * 生产日期
     */
    private Date productDate;

    /**
     * 过期日期
     */
    private Date expireDate;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 唯一标识
     */
    private String cacheId;

    /**
     * 库存类型（0-在库型，1-通过型）
     */
    private String isThrough;

    /**
     * 温层
     */
    private String inventoryType;

    /**
     * 库位类型(0-存储，1-入库，2-出库，3-分拣，4-盘点，5-虚拟，6-通过)
     */
    private String locationType;

    /**
     * 项目号
     */
    private String businessId;

    /**
     * 是否降级使用 与字典一致
     */
    private String isDownGrade;

    /**
     * 降级描述
     */
    private String downGradeDesc;

    /**
     * 降级的复检单据
     */
    private String downGradeTestingId;

    /**
     * 物料批次
     */
    private String itemLot;
}
