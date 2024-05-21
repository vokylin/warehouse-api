package com.ruoyi.common.enums;

public enum InventoryPlanStatus {

    //待盘点、盘点中、审批中、已通过、未通过

    WAITING(0, "待盘点"),

    INVENTORYING(1, "盘点中"),

    APPROVING(2, "审批中"),

    PASSED(3, "已通过"),

    NOT_PASSED(4, "未通过");


    private final Integer code;

    private final String info;

    InventoryPlanStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static InventoryPlanStatus getInventoryPlanStatus(Integer code) {
        for (InventoryPlanStatus inventoryPlanStatus : InventoryPlanStatus.values()) {
            if (inventoryPlanStatus.getCode().equals(code)) {
                return inventoryPlanStatus;
            }
        }
        return null;
    }

    public static String getInventoryPlanStatusInfo(Integer code) {
        for (InventoryPlanStatus inventoryPlanStatus : InventoryPlanStatus.values()) {
            if (inventoryPlanStatus.getCode().equals(code)) {
                return inventoryPlanStatus.getInfo();
            }
        }
        return null;
    }
}
