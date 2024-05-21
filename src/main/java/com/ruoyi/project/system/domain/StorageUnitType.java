package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 存储单元类型对象 storage_unit_type
 *
 * @author Rem
 * @date 2023-03-30
 */
@Data
public class StorageUnitType {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 编码
     */
    @Excel(name = "编码")
    private String code;

    private String electronicCodeType;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String description;

    /**
     * 父级ID
     */
    private String parentId;

    /**
     * 单元路径
     */
    private String parentIds;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createUserName;

    /**
     * 修改人名称
     */
    @Excel(name = "修改人名称")
    private String updateUserName;
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

    private List<StorageUnitType> children = new ArrayList<>();
}
