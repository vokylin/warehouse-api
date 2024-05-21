package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 库区对象 wh_area
 *
 * @author Rem
 * @date 2023-03-27
 */
@Data
public class WhArea {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 库区编码
     */
    @Excel(name = "库区编码")
    private String areaCode;

    /**
     * 库区名称
     */
    @Excel(name = "库区名称")
    private String areaName;

    /**
     * 仓库ID
     */
    @Excel(name = "仓库ID")
    private String whId;

    private List<String> whIds;

    /**
     * 仓库名称
     */
    @Excel(name = "仓库名称")
    private String whName;

    /**
     * 温层
     */
    @Excel(name = "温层")
    private String thermosphere;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String desc;

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
