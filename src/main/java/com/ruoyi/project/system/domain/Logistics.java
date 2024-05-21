package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 承运商对象 tb_logistics
 *
 * @author Rem
 * @date 2023-04-11
 */
public class Logistics extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 编码
     */
    @Excel(name = "编码")
    private String logisticsCode;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String logisticsName;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contacts;

    /**
     * 联系人电话
     */
    @Excel(name = "联系人电话")
    private String contactsTel;

    /**
     * 联系人邮箱
     */
    @Excel(name = "联系人邮箱")
    private String contactsEmail;

    /**
     * 国家
     */
    @Excel(name = "国家")
    private String country;

    /**
     * 省
     */
    @Excel(name = "省")
    private String province;

    /**
     * 市
     */
    @Excel(name = "市")
    private String city;

    /**
     * 区-中文
     */
    @Excel(name = "区-中文")
    private String region;

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
     * 描述
     */
    @Excel(name = "描述")
    private String description;

    /**
     * 开通账户状态：0-正常 1-停用
     */
    @Excel(name = "开通账户状态：0-正常 1-停用")
    private String status;

    /**
     * 创建人id
     */
    @Excel(name = "创建人id")
    private String createId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;

    /**
     * 是否删除：0-未删除 1-已删除
     */
    @Excel(name = "是否删除：0-未删除 1-已删除")
    private String isDeleted;

    /**
     * 企业信用统一编码
     */
    @Excel(name = "企业信用统一编码")
    private String enterpriseCode;

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
     * 区-code
     */
    @Excel(name = "区-code")
    private String regionCode;

    /**
     * 简称（6个字以内）
     */
    @Excel(name = "简称", readConverterExp = "6=个字以内")
    private String shortName;

    /**
     * 企业key(E00002) 创建人的企业Key
     */
    @Excel(name = "企业key(E00002) 创建人的企业Key")
    private String enterpriseKey;

    /**
     * 审核状态：0-待提交审核；1-拒绝；2-已通过；3-审核中
     */
    @Excel(name = "审核状态：0-待提交审核；1-拒绝；2-已通过；3-审核中")
    private String auditStatus;

    /**
     * 经营范围
     */
    @Excel(name = "经营范围")
    private String managerScope;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;

    /**
     * 申请类型：add-新增；update-修改
     */
    @Excel(name = "申请类型：add-新增；update-修改")
    private String applyType;

    /**
     * 证件过期锁定: 1- 证件过期锁定  2- 未锁定
     */
    @Excel(name = "证件过期锁定: 1- 证件过期锁定  2- 未锁定")
    private String locked;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContactsTel(String contactsTel) {
        this.contactsTel = contactsTel;
    }

    public String getContactsTel() {
        return contactsTel;
    }

    public void setContactsEmail(String contactsEmail) {
        this.contactsEmail = contactsEmail;
    }

    public String getContactsEmail() {
        return contactsEmail;
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

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
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

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setEnterpriseKey(String enterpriseKey) {
        this.enterpriseKey = enterpriseKey;
    }

    public String getEnterpriseKey() {
        return enterpriseKey;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setManagerScope(String managerScope) {
        this.managerScope = managerScope;
    }

    public String getManagerScope() {
        return managerScope;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getLocked() {
        return locked;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("logisticsCode", getLogisticsCode())
                .append("logisticsName", getLogisticsName())
                .append("contacts", getContacts())
                .append("contactsTel", getContactsTel())
                .append("contactsEmail", getContactsEmail())
                .append("country", getCountry())
                .append("province", getProvince())
                .append("city", getCity())
                .append("region", getRegion())
                .append("address", getAddress())
                .append("zipCode", getZipCode())
                .append("description", getDescription())
                .append("status", getStatus())
                .append("createId", getCreateId())
                .append("createBy", getCreateBy())
                .append("createDate", getCreateDate())
                .append("updateBy", getUpdateBy())
                .append("updateDate", getUpdateDate())
                .append("isDeleted", getIsDeleted())
                .append("enterpriseCode", getEnterpriseCode())
                .append("provinceCode", getProvinceCode())
                .append("cityCode", getCityCode())
                .append("regionCode", getRegionCode())
                .append("shortName", getShortName())
                .append("enterpriseKey", getEnterpriseKey())
                .append("auditStatus", getAuditStatus())
                .append("managerScope", getManagerScope())
                .append("type", getType())
                .append("applyType", getApplyType())
                .append("locked", getLocked())
                .toString();
    }
}
