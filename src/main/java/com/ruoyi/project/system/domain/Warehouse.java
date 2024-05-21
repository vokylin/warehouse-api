package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 仓库对象 warehouse
 *
 * @author Rem
 * @date 2023-03-27
 */
@Data
public class Warehouse {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 仓库编码
     */
    @Excel(name = "仓库编码")
    private String code;

    /**
     * 仓库名称
     */
    @Excel(name = "仓库名称")
    private String name;

    /**
     * 所属公司
     */
    private String belongCompany;

    /**
     * 所属公司名称
     */
    @Excel(name = "所属公司")
    private String belongCompanyName;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contractUser;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String contractPhone;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    private String email;

    /**
     * 仓库类型
     */
    @Excel(name = "仓库类型")
    private Integer type;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建者用户名
     */
    private String createUserName;

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
     * 更新者用户名
     */
    private String updateUserName;

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
     * 存储单元类型
     */
    private Integer storageUnitType;

    /**
     * 存储单元
     */
    private List<StorageUnit> storageUnits = new ArrayList<>();

}
