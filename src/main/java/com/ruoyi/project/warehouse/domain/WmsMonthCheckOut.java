package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 月结对象 wms_month_check_out
 *
 * @author gaomian
 * @date 2023-05-27
 */
@Data
public class WmsMonthCheckOut {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 公司id
     */
    @Excel(name = "公司id")
    private String companyId;

    /**
     * 年份-月份
     */
    @Excel(name = "年份-月份")
    private String yearMonths;

    /**
     * 年份
     */
    @Excel(name = "年份")
    private String yearStr;

    /**
     * 月份
     */
    @Excel(name = "月份")
    private String monthStr;

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
     * 规格
     */
    @Excel(name = "规格")
    private String itemSpec;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String itemUnit;

    /**
     * 是否删除 （0-正常 1-已经删除）
     */
    @Excel(name = "是否删除 ", readConverterExp = "0=-正常,1=-已经删除")
    private Integer isDelete;

    /**
     * 期初数量
     */
    @Excel(name = "期初数量")
    private BigDecimal openingQuantity;

    /**
     * 期初价格
     */
    @Excel(name = "期初价格")
    private BigDecimal openingPrice;

    /**
     * 期初总额
     */
    @Excel(name = "期初总额")
    private BigDecimal openingTotal;

    /**
     * 期末库存
     */
    @Excel(name = "期末库存")
    private BigDecimal endingQuantity;

    /**
     * 期末价格
     */
    @Excel(name = "期末价格")
    private BigDecimal endingPrice;

    /**
     * 期末总额
     */
    @Excel(name = "期末总额")
    private BigDecimal endingTotal;

    /**
     * 本月入库数量
     */
    @Excel(name = "本月入库数量")
    private BigDecimal inQuantity;

    /**
     * 入库平均价格
     */
    @Excel(name = "入库平均价格")
    private BigDecimal inPrice;

    /**
     * 入库总额
     */
    @Excel(name = "入库总额")
    private BigDecimal inTotal;

    /**
     * 本月出库数量
     */
    @Excel(name = "本月出库数量")
    private BigDecimal outQuantity;

    /**
     * 出库平均价格
     */
    @Excel(name = "出库平均价格")
    private BigDecimal outPrice;

    /**
     * 出库总额
     */
    @Excel(name = "出库总额")
    private BigDecimal outTotal;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
