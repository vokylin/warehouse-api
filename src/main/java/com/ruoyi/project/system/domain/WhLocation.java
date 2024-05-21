package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 库位对象 wh_location
 *
 * @author Rem
 * @date 2023-03-28
 */
@Data
public class WhLocation {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 编码
     */
    @Excel(name = "编码")
    private String code;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private Integer type;

    /**
     * 库区ID
     */
    @Excel(name = "库区ID")
    private String whAreaId;

    private List<String> whAreaIds;

    private String warehouseId;

    /**
     * 工作空间
     */
    @Excel(name = "工作空间")
    private String workSpace;

    /**
     * 混放策略
     */
    @Excel(name = "混放策略")
    private Integer mixedPolicy;

    /**
     * 锁定策略
     */
    @Excel(name = "锁定策略")
    private Integer lockedPolicy;

    /**
     * 巷道
     */
    @Excel(name = "巷道")
    private String tunnel;

    /**
     * 排
     */
    @Excel(name = "排")
    private String arrange;

    /**
     * 层
     */
    @Excel(name = "层")
    private String storey;

    /**
     * 列
     */
    @Excel(name = "列")
    private String column;

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
}
