package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 质检作业对象 wms_examine_work
 *
 * @author Rem
 * @date 2023-04-07
 */

@Data
public class WmsExamineWork {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    private String[] ids;

    /**
     * 通知明细ID
     */
    @Excel(name = "通知明细ID")
    private String receiveItemDetailId;

    /**
     * 入库通知ID
     */
    @Excel(name = "入库通知ID")
    private String receiveNoticeId;

    /**
     * 物料编码
     */
    @Excel(name = "物料编码")
    private String itemCode;

    /**
     * 物料批次
     */
    @Excel(name = "物料批次")
    private String batchNo;

    /**
     * 质检状态
     */
    @Excel(name = "质检状态")
    private Integer status;

    private List<Integer> statusArr;

    /**
     * 抽样数量
     */
    @Excel(name = "抽样数量")
    private BigDecimal sampleQuantity;

    /**
     * 留样数量
     */
    @Excel(name = "留样数量")
    private BigDecimal keepQuantity;

    /**
     * 不合格数
     */
    @Excel(name = "不合格数")
    private BigDecimal failQuantity;

    /**
     * 报废数量
     */
    @Excel(name = "报废数量")
    private BigDecimal scrapQuantity;

    /**
     * 合格数量
     */
    @Excel(name = "合格数量")
    private BigDecimal passQuantity;

    /**
     * 抽样退回数量
     */
    private BigDecimal sampleRefundQuantity;

    /**
     * 质检总数
     */
    @Excel(name = "质检总数")
    private BigDecimal allQuantity;

    /**
     * 检验员
     */
    @Excel(name = "检验员")
    private String examineBy;

    /**
     * 检验主管
     */
    @Excel(name = "检验主管")
    private String examineManager;

    /**
     * 修改人
     */
    @Excel(name = "修改人")
    private Long updateUp;

    /**
     * 通知状态 (0:未通知，1：已通知)
     */
    @Excel(name = "通知状态 (0:未通知，1：已通知)")
    private Integer operateStatus;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private String companyId;

    /**
     * 质检类型
     */
    @Excel(name = "质检类型")
    private Integer checkType;

    /**
     * 通知详情ID
     */
    @Excel(name = "通知详情ID")
    private String noticeDetailId;

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

    /**
     * 物料名称
     */
    private String itemName;

    /**
     * 单位
     */
    private String unit;
}
