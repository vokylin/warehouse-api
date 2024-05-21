package com.ruoyi.common.enums;

public enum ItemType {

    //0：进料验收 1：进货验收 2：其他
    INBOUND_ACCEPTANCE(0, "进料验收"),
    PURCHASE_ACCEPTANCE(1, "进货验收"),
    OTHER(2, "其他"),
    PROCUREMENT_SCRAP(3, "采购报废"),
    SPECIAL_COLLECTION(4, "特采");


    private Integer code;

    private String info;

    ItemType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static ItemType getItemType(Integer code) {
        for (ItemType itemType : ItemType.values()) {
            if (itemType.getCode().equals(code)) {
                return itemType;
            }
        }
        return null;
    }

    public static String getItemTypeInfo(Integer code) {
        for (ItemType itemType : ItemType.values()) {
            if (itemType.getCode().equals(code)) {
                return itemType.getInfo();
            }
        }
        return null;
    }


}
