package com.ruoyi.project.system.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 物料主档对象 product_info
 *
 * @author Rem
 * @date 2023-03-28
 */
@Data
public class ProductInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 物料编码
     */
    @Excel(name = "物料编码")
    private String productCode;

    /**
     * 物料名称
     */
    @Excel(name = "物料名称")
    private String productName;

    /**
     * 创建企业密钥
     */
    @Excel(name = "创建企业密钥")
    private String createEnterpriseKey;

    /**
     * 物料代码企业
     */
    @Excel(name = "物料代码企业")
    private String materialCodeEnterprise;

    /**
     * 类别
     */
    @Excel(name = "类别")
    private String category;

    /**
     * 条形码
     */
    @Excel(name = "条形码")
    private String barcode;

    /**
     * 零件编号
     */
    @Excel(name = "零件编号")
    private String partNo;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;

    /**
     * 规范
     */
    @Excel(name = "规范")
    private String spec;

    /**
     * 单价
     */
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /**
     * 长度
     */
    @Excel(name = "长度")
    private String length;

    /**
     * 宽度
     */
    @Excel(name = "宽度")
    private String width;

    /**
     * 高度
     */
    @Excel(name = "高度")
    private String height;

    /**
     * 卷
     */
    @Excel(name = "卷")
    private String volume;

    /**
     * 重量
     */
    @Excel(name = "重量")
    private String weight;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private String quantity;

    /**
     * 在线
     */
    @Excel(name = "在线")
    private String onLine;

    /**
     * 供应商ID
     */
    @Excel(name = "供应商ID")
    private String supplierId;

    /**
     * 供应商编码
     */
    @Excel(name = "供应商编码")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    private String supplierName;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createName;

    /**
     * 更新人ID
     */
    @Excel(name = "更新人ID")
    private String updateUserId;

    /**
     * 是否删除
     */
    @Excel(name = "是否删除")
    private Integer isDeleted;

    /**
     * 类别属性
     */
    @Excel(name = "类别属性")
    private String categoryProperties;

    /**
     * 创建人ID
     */
    @Excel(name = "创建人ID")
    private String createUserId;

    /**
     * 企业密钥
     */
    @Excel(name = "企业密钥")
    private String enterpriseKey;

    /**
     * 锁定状态
     */
    @Excel(name = "锁定状态")
    private Integer locked;

    /**
     * 剂型
     */
    @Excel(name = "剂型")
    private String dosageForm;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;

    /**
     * 批次号
     */
    @Excel(name = "批次号")
    private String approvalNo;

    /**
     * 规范包
     */
    @Excel(name = "规范包")
    private String specPackage;

    /**
     * 管理范围
     */
    @Excel(name = "管理范围")
    private String managerScope;

    /**
     * 分类二
     */
    @Excel(name = "分类二")
    private String classifyTwo;

    /**
     * 临床分类
     */
    @Excel(name = "临床分类")
    private String classifyClinical;

    /**
     * 分类行业
     */
    @Excel(name = "分类行业")
    private String classifyIndustry;

    /**
     * 最小套餐
     */
    @Excel(name = "最小套餐")
    private String minPackage;

    /**
     * CG价格
     */
    @Excel(name = "CG价格")
    private Long cgPrice;

    /**
     * 税率
     */
    @Excel(name = "税率")
    private Long taxRate;

    /**
     * 税点
     */
    @Excel(name = "税点")
    private Long tax;

    /**
     * 审核状态
     */
    @Excel(name = "审核状态")
    private String auditStatus;

    /**
     * 材料类型
     */
    @Excel(name = "材料类型")
    private String materialType;

    /**
     * 应用类型
     */
    @Excel(name = "应用类型")
    private String applyType;

    /**
     * 剂型规格
     */
    @Excel(name = "剂型规格")
    private String dosageFormSpec;

    /**
     * 产品编号
     */
    @Excel(name = "产品编号")
    private String productNumber;

    /**
     * 生效结束日期
     */
    @Excel(name = "生效结束日期")
    private String effectiveEndDate;

    /**
     * 材料代码供应商
     */
    @Excel(name = "材料代码供应商")
    private String materialCodeSupplier;

    /**
     * 准备日
     */
    @Excel(name = "准备日")
    private String prepareDay;

    /**
     * 测量方法
     */
    @Excel(name = "测量方法")
    private String measurementMethods;

    /**
     * 最小单位
     */
    @Excel(name = "最小单位")
    private String minUnit;

    /**
     * 大类
     */
    @Excel(name = "大类")
    private String bigClass;

    /**
     * 类别等级
     */
    @Excel(name = "类别等级")
    private String levelClass;

    /**
     * 无税价格
     */
    @Excel(name = "无税价格")
    private BigDecimal notaxPrice;

    /**
     * 简称
     */
    @Excel(name = "简称")
    private String shortName;

    /**
     * 英文名称
     */
    @Excel(name = "英文名称")
    private String englishName;

    /**
     * 库存定义
     */
    @Excel(name = "库存定义")
    private String stockDefined;

    /**
     * 剂量属于
     */
    @Excel(name = "剂量属于")
    private String doseBelongs;

    /**
     * 主单位
     */
    @Excel(name = "主单位")
    private String masterUnit;

    /**
     * if_group
     */
    @Excel(name = "if_group")
    private String ifGroup;


    /**
     * 上限数量
     */
    private Long upQuality;

    /**
     * 下限数量
     */
    private Long downQuality;

    /**
     * 提前预警天数
     */
    private Integer remindDay;

    private String itemCodeOrName;

}
