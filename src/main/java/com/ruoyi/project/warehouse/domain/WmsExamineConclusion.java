package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * examineConclusion对象 wms_examine_conclusion
 *
 * @author Rem
 * @date 2023-04-21
 */
@Data
public class WmsExamineConclusion {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 质检任务ID
     */
    @Excel(name = "质检任务ID")
    private String examineId;

    /**
     * 收货状态 （全收、全退、部分收）
     */
    @Excel(name = "收货状态 ", readConverterExp = "全=收、全退、部分收")
    private Integer receiveStatus;

    /**
     * 接收数量
     */
    @Excel(name = "接收数量")
    private BigDecimal receiveQuantity;

    /**
     * 退还数量
     */
    @Excel(name = "退还数量")
    private BigDecimal refundQuantity;

    /**
     * 特采编号
     */
    @Excel(name = "特采编号")
    private String specialNo;

    /**
     * 备注
     */
    private String remark;

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

}
