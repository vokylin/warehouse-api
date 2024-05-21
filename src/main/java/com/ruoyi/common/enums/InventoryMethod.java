package com.ruoyi.common.enums;

public enum InventoryMethod {
    BRIGHT(0, "明盘"),

    BLIND(1, "盲盘");


    private Integer code;

    private String info;

    InventoryMethod(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static InventoryMethod getInventoryMethod(Integer code) {
        for (InventoryMethod inventoryMethod : InventoryMethod.values()) {
            if (inventoryMethod.getCode().equals(code)) {
                return inventoryMethod;
            }
        }
        return null;
    }

    public static String getInventoryMethodInfo(Integer code) {
        for (InventoryMethod inventoryMethod : InventoryMethod.values()) {
            if (inventoryMethod.getCode().equals(code)) {
                return inventoryMethod.getInfo();
            }
        }
        return null;
    }


}
