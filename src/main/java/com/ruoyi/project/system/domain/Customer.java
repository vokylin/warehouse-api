package com.ruoyi.project.system.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 客户对象 tb_sys_customer
 *
 * @author Rem
 * @date 2023-04-11
 */
public class Customer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 简称（6个字以内）
     */
    @Excel(name = "简称", readConverterExp = "6=个字以内")
    private String shortName;

    /**
     * 编码（C00001）
     */
    @Excel(name = "编码", readConverterExp = "C=00001")
    private String customerCode;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String concatUser;

    /**
     * 电话
     */
    @Excel(name = "电话")
    private String tel;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    private String email;

    /**
     * 国家
     */
    @Excel(name = "国家")
    private String country;

    /**
     * 省-中文
     */
    @Excel(name = "省-中文")
    private String province;

    /**
     * 市-中文
     */
    @Excel(name = "市-中文")
    private String city;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String address;

    /**
     * 邮编
     */
    @Excel(name = "邮编")
    private String zipCode;

    /**
     * 企业key(E00002)
     */
    @Excel(name = "企业key(E00002)")
    private String enterpriseKey;

    /**
     * 企业信用统一编码
     */
    @Excel(name = "企业信用统一编码")
    private String enterpriseCode;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updateUserName;

    /**
     * 是否删除：0-未删除 1-已删除
     */
    @Excel(name = " 是否删除：0-未删除 1-已删除")
    private String isDeleted;

    /**
     * 开通账号
     */
    @Excel(name = "开通账号")
    private String openAccount;

    /**
     * 开通账户状态：1-未开通 2-已开通
     */
    @Excel(name = "开通账户状态：1-未开通 2-已开通")
    private String status;

    /**
     * 省-code
     */
    @Excel(name = "省-code")
    private String provinceCode;

    /**
     * 市-code
     */
    @Excel(name = "市-code")
    private String cityCode;

    /**
     * 创建人ID
     */
    @Excel(name = "创建人ID")
    private String createUserId;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createUserName;

    /**
     * 区-中文
     */
    @Excel(name = "区-中文")
    private String region;

    /**
     * 区-code
     */
    @Excel(name = "区-code")
    private String regionCode;

    /**
     * 类型id
     */
    @Excel(name = "类型id")
    private String typeId;

    /**
     * 类型名称
     */
    @Excel(name = "类型名称")
    private String typeName;

    /**
     * 客户类型：大客户、经销商、零售客户、科研机构（从字典取）
     */
    @Excel(name = "客户类型：大客户、经销商、零售客户、科研机构", readConverterExp = "从=字典取")
    private String customerLevel;

    /**
     * 授信期，eg：30
     */
    @Excel(name = "授信期，eg：30")
    private String creditPeriod;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setConcatUser(String concatUser) {
        this.concatUser = concatUser;
    }

    public String getConcatUser() {
        return concatUser;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setEnterpriseKey(String enterpriseKey) {
        this.enterpriseKey = enterpriseKey;
    }

    public String getEnterpriseKey() {
        return enterpriseKey;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setOpenAccount(String openAccount) {
        this.openAccount = openAccount;
    }

    public String getOpenAccount() {
        return openAccount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityCode() {
        return cityCode;
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

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel;
    }

    public String getCustomerLevel() {
        return customerLevel;
    }

    public void setCreditPeriod(String creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    public String getCreditPeriod() {
        return creditPeriod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("customerName", getCustomerName())
                .append("shortName", getShortName())
                .append("customerCode", getCustomerCode())
                .append("concatUser", getConcatUser())
                .append("tel", getTel())
                .append("email", getEmail())
                .append("country", getCountry())
                .append("province", getProvince())
                .append("city", getCity())
                .append("address", getAddress())
                .append("zipCode", getZipCode())
                .append("remark", getRemark())
                .append("enterpriseKey", getEnterpriseKey())
                .append("enterpriseCode", getEnterpriseCode())
                .append("updateUserName", getUpdateUserName())
                .append("updateTime", getUpdateTime())
                .append("isDeleted", getIsDeleted())
                .append("openAccount", getOpenAccount())
                .append("status", getStatus())
                .append("provinceCode", getProvinceCode())
                .append("cityCode", getCityCode())
                .append("createUserId", getCreateUserId())
                .append("createUserName", getCreateUserName())
                .append("createTime", getCreateTime())
                .append("region", getRegion())
                .append("regionCode", getRegionCode())
                .append("typeId", getTypeId())
                .append("typeName", getTypeName())
                .append("customerLevel", getCustomerLevel())
                .append("creditPeriod", getCreditPeriod())
                .toString();
    }
}
