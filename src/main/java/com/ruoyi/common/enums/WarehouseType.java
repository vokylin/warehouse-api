package com.ruoyi.common.enums;

/**
 * 仓库类型
 *
 * @author REM
 * @date 2023/04/06
 */
public enum WarehouseType {

    /**
     * 1,成品仓
     * 2,原辅料仓
     * 3,半成品仓
     * 4,退货仓
     * 5,包材仓
     * 6,耗材仓
     * 7,研发仓类型
     * 9,客户来料仓
     */

    PRODUCT(1, "成品仓"),
    RAW(2, "原辅料仓"),
    SEMI(3, "半成品仓"),
    RETURN(4, "退货仓"),
    PACKING(5, "包材仓"),
    CONSUMABLE(6, "耗材仓"),
    RESEARCH(7, "研发仓类型"),
    CUSTOMER(9, "客户来料仓");

    private final Integer code;
    private final String info;

    WarehouseType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static WarehouseType getWarehouseType(Integer code) {
        for (WarehouseType warehouseType : WarehouseType.values()) {
            if (warehouseType.getCode().equals(code)) {
                return warehouseType;
            }
        }
        return null;
    }

    public static String getWarehouseTypeDesc(Integer code) {
        for (WarehouseType warehouseType : WarehouseType.values()) {
            if (warehouseType.getCode().equals(code)) {
                return warehouseType.getInfo();
            }
        }
        return null;
    }

    public static WarehouseType getWarehouseType(String info) {
        for (WarehouseType warehouseType : WarehouseType.values()) {
            if (warehouseType.getInfo().equals(info)) {
                return warehouseType;
            }
        }
        return null;
    }

}
