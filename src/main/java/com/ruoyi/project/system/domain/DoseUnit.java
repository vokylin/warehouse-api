package com.ruoyi.project.system.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 剂量单位对象 tb_dose_unit
 *
 * @author Rem
 * @date 2023-04-27
 */
public class DoseUnit extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 商品编码
     */
    @Excel(name = "商品编码")
    private String productCode;

    /**
     * 商品名称
     */
    @Excel(name = "商品名称")
    private String productName;

    /**
     * 主计量单位（eg:kg）
     */
    @Excel(name = "主计量单位", readConverterExp = "e=g:kg")
    private String masterUnit;

    /**
     * 换算率（eg:5）
     */
    @Excel(name = "换算率", readConverterExp = "e=g:5")
    private BigDecimal conversionRate;

    /**
     * 所属：sales-销售 purchase-采购 manufacture-生产 warehouse-库存剂量单位
     */
    @Excel(name = "所属：sales-销售 purchase-采购 manufacture-生产 warehouse-库存剂量单位")
    private String belongs;

    /**
     * 采购/库存/生产/销售/剂量单位 =主剂量单位*换算率
     */
    @Excel(name = "采购/库存/生产/销售/剂量单位 =主剂量单位*换算率")
    private String doseUnit;

    /**
     * 创建人id
     */
    @Excel(name = "创建人id")
    private String createUserId;

    /**
     * 创建人姓名
     */
    @Excel(name = "创建人姓名")
    private String createUserName;

    /**
     * 修改人id
     */
    @Excel(name = "修改人id")
    private String updateUserId;

    /**
     * 修改人姓名
     */
    @Excel(name = "修改人姓名")
    private String updateUserName;

    /**
     * 企业唯一key ：E00001
     */
    @Excel(name = "企业唯一key ：E00001")
    private String enterpriseKey;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setMasterUnit(String masterUnit) {
        this.masterUnit = masterUnit;
    }

    public String getMasterUnit() {
        return masterUnit;
    }

    public void setConversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public void setBelongs(String belongs) {
        this.belongs = belongs;
    }

    public String getBelongs() {
        return belongs;
    }

    public void setDoseUnit(String doseUnit) {
        this.doseUnit = doseUnit;
    }

    public String getDoseUnit() {
        return doseUnit;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setEnterpriseKey(String enterpriseKey) {
        this.enterpriseKey = enterpriseKey;
    }

    public String getEnterpriseKey() {
        return enterpriseKey;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("productCode", getProductCode())
                .append("productName", getProductName())
                .append("masterUnit", getMasterUnit())
                .append("conversionRate", getConversionRate())
                .append("belongs", getBelongs())
                .append("doseUnit", getDoseUnit())
                .append("createTime", getCreateTime())
                .append("createUserId", getCreateUserId())
                .append("createUserName", getCreateUserName())
                .append("updateTime", getUpdateTime())
                .append("updateUserId", getUpdateUserId())
                .append("updateUserName", getUpdateUserName())
                .append("enterpriseKey", getEnterpriseKey())
                .toString();
    }
}
