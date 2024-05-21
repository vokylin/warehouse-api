package com.ruoyi.project.common.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BaseDetailDTO {
    // 实际 收/发货 数量
    private BigDecimal quantity;
    //产品/物料编码
    private String itemCode;
    //产品/物料名称
    private String itemName;
    //单位
    private String itemUnit;
    //物料批次
    private String batchNo;
    /**
     * 实际入库数量
     */
    private BigDecimal receiveQuantity;
    /**
     * 规格型号
     */
    private String spec;
    /**
     * 单价 （领出时的实时单价）
     */
    private BigDecimal price;
    /**
     * 生产日期
     */
    private Date produceTime;
    /**
     * 过期日期
     */
    private Date expireTime;

    /**
     * 仓库对接中未使用的字段
     */
    private String id;
    //通知id
    private String deliveryNoticeId;
    //收货通知单号
    private String receiveNoticeId;
    //实际发货的数量
    private BigDecimal deliveryQuantity;
    //单位所属
    private String doseBelongs;
    //主条码
    private String itemBarcode;
    //退货原因
    private String reason;
    //退货说明
    private String warehouseReason;


}
