package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否逻辑删除
 */
@Getter
@AllArgsConstructor
public enum IsDeletedEnum {

    NORMAL(0, "未删除"),
    DELETED(1, "已删除");

    private Integer val;

    private String desc;
}
