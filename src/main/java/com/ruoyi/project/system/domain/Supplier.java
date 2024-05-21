package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 供应商对象 supplier
 *
 * @author Rem
 * @date 2023-03-28
 */
@Data
public class Supplier extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private String id;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private String companyId;

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
     * 编码
     */
    @Excel(name = "编码")
    private String supplierCode;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String supplierName;

    /**
     * 简称（6个字以内）
     */
    @Excel(name = "简称", readConverterExp = "6=个字以内")
    private String shortName;

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
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
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
     * 注册地址
     */
    @Excel(name = "注册地址")
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
     * 从仓储同步过来的供应商id
     */
    @Excel(name = "从仓储同步过来的供应商id")
    private String wmsSupplierId;

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
     * 开通账号
     */
    @Excel(name = "开通账号")
    private String openAccount;

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
     * 开通账户状态：1-未开通 2-已开通
     */
    @Excel(name = "开通账户状态：1-未开通 2-已开通")
    private String status;

    /**
     * 供应商分类：1-原材料；2-耗材；3-试剂；4-产品包装；5-设备；6-其他
     */
    @Excel(name = "供应商分类：1-原材料；2-耗材；3-试剂；4-产品包装；5-设备；6-其他")
    private String bigClass;

    /**
     * 供应商评级：1-零星采购；2-长期合作；3-战略长期合作
     */
    @Excel(name = "供应商评级：1-零星采购；2-长期合作；3-战略长期合作")
    private String supplierLevel;

    /**
     * 供应商分型：A类、B类、C类、D类(多选用逗号隔开，eg：A,B)
     */
    @Excel(name = "供应商分型：A类、B类、C类、D类(多选用逗号隔开，eg：A,B)")
    private String levelClass;
}
