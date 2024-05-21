package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 出入库记录对象 wms_storage_in_out_record
 *
 * @author Rem
 * @date 2023-04-08
 */
@Data
public class WmsStorageInOutRecord {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 公司ID
     */
    private String companyId;

    @Excel(name = "仓库")
    private String warehouseName;

    @Excel(name = "库位")
    private String locationName;
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
     * 物料批次
     */
    @Excel(name = "物料批次")
    private String batchNo;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;

    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String spec;

    /**
     * 类型（入库、出库）
     */
    @Excel(name = "类型", readConverterExp = "1=入库,0=出库")
    private Integer type;

    /**
     * 单据说明
     */
    @Excel(name = "单据说明")
    private String noticeDesc;

    @Excel(name = "备注")
    private String remark;

    /**
     * 滚算前单价
     */
    private BigDecimal beforeRollingPrice;

    /**
     * 滚算前总库存金额
     */
    private BigDecimal beforeRollingTotalPrice;

    /**
     * 滚算前总库存数
     */
    private BigDecimal beforeRollingQuantity;

    /**
     * 期初数量（物料批次原库存数量）
     */
    private BigDecimal openingQuantity;

    /**
     * 期初金额 （物料批次原库存金额）
     */
    private BigDecimal openingAmount;

    /**
     * 发生时单价
     */
    private BigDecimal occPrice;

    /**
     * 发生时总价 （最后一批出库时，要用总库存金额）
     */
    @Excel(name = "金额")
    private BigDecimal occTotalPrice;

    /**
     * 发生时数量
     */
    private BigDecimal occQuantity;

    /**
     * 滚算后单价
     */
    private BigDecimal afterRollingPrice;

    /**
     * 滚算后总库存金额
     */
    private BigDecimal afterRollingTotalPrice;

    /**
     * 滚算后总库存数量
     */
    private BigDecimal afterRollingQuantity;


    /**
     * 结存数量（物料批次最新数量）
     */
    private BigDecimal surplusQuantity;

    /**
     * 结存金额（物料批次最新金额）
     */
    private BigDecimal surplusAmount;

    /**
     * 业务编号
     */
    @Excel(name = "业务编号")
    private String businessNo;

    /**
     * 通知ID
     */
    @Excel(name = "通知编号")
    private String noticeId;

    /**
     * 发生时间（出/入库选择时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date occTime;

    /**
     * 收发类别
     */
    private String receiveSendType;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd")
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

    private String warehouseId;

    private String warehouseCode;

    private String locationId;

    private String beginTime;

    private String endTime;

    private List<String> warehouseIdList;

    /**
     * 出入库单的部门名称
     */
    @Excel(name = "部门")
    private String deptName;

    /**
     * 出入库完成时间
     */
    private Date completionTime;

    @Excel(name = "单号")
    private String number;

    /**
     * 单据类型(入库、退料、领料）
     */
    @Excel(name = "单据类型", readConverterExp = "0=入库单,1=退料单,2=领料单,3=不合格品处理单,4=盘点")
    private Integer billType;
}
