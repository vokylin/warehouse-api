package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 进料验收单采购内容VO
 *
 * @author gaomian
 * @since 2023-04-30
 */
@Data
public class FeedstockPurchaseInfoVO {
    /**
     * 进料验收单编号
     */
    private String formNumber;
    /**
     * 单据日期
     */
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private Date formDate;
    /**
     * 物料编码
     */
    private String itemCode;
    /**
     * 物料名称
     */
    private String itemName;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 储存条件
     */
    private String storageConditions;
    /**
     * 规格型号
     */
    private String spec;

    /**
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private Date produceTime;

    /**
     * 保质期至
     */
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private Date expireTime;
    /**
     * 生产批号
     */
    private String batchNo;

    /**
     * 加密编码
     */
    private String encryptionCode;

    /**
     * 物料备注
     */
    private String remark;

    /**
     * 采购订单号
     */
    private String orderSn;

    /**
     * 采购签收人
     */
    private String signer;
    /**
     * 进料数量
     */
    private BigDecimal quantity;
    /**
     * 库存单位
     */
    private String unit;

    /**
     * 请验附件 （供方品质证明、其他）
     */
    private String annex;
    /**
     * 其他描述
     */
    private String annexDesc;
    /**
     * 购买备注（正常购料、补/换货、赠送、其他）
     */
    private String purchaseType;
    /**
     * 购买备注说明
     */
    private String purchaseTypeDesc;

    /**
     * 采购经办
     */
    private String confirmer;

}
