package com.ruoyi.common.enums;

/**
 * 树数据类型枚举
 *
 * @author Rem
 * @date 2023/03/28
 */
public enum TreeDataEnums {

    COMPANY(0), WAREHOUSE(1);

    private final int type;

    TreeDataEnums(int type) {
        this.type = type;
    }

    public int getValue() {
        return type;
    }
}
