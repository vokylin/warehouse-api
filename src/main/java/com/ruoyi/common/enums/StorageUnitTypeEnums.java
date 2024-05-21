package com.ruoyi.common.enums;

/**
 * 用户状态
 *
 * @author ruoyi
 */
public enum StorageUnitTypeEnums {
    /**
     * 仓库
     */
    WAREHOUSE(0),
    STORAGE_UNIT(1);

    private final int code;

    StorageUnitTypeEnums(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
