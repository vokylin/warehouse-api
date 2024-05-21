package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 公司管理对象 company
 *
 * @author Rem
 * @date 2023-03-27
 */
@Data
public class Company {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 公司编码
     */
    @Excel(name = "公司编码")
    private String code;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String name;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contactUser;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String contactPhone;

    /**
     * 联系邮箱
     */
    @Excel(name = "联系邮箱")
    private String email;

    /**
     * 邮编
     */
    @Excel(name = "邮编")
    private String postCode;

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
     * 区
     */
    @Excel(name = "区")
    private String county;

    /**
     * 详细地址
     */
    @Excel(name = "详细地址")
    private String address;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 所属公司ID
     */
    private String parentId;

    /**
     * 所属公司IDS
     */
    private String parentIds;

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
     * 备注
     */
    private String remark;

    /**
     * 子公司
     */
    private List<Company> children = new ArrayList<Company>();

    private List<Warehouse> warehouses = new ArrayList<Warehouse>();

}
