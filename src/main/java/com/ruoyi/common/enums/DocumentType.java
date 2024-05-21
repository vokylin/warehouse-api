package com.ruoyi.common.enums;

public enum DocumentType {

//  0进货验收单  1 入库单 2退料单

    PURCHASE_ACCEPTANCE(0, "进货验收单"),
    WAREHOUSE_ENTRY(1, "入库单"),
    RETURN_MATERIAL(2, "退料单"),

    PICKING_LIST(3, "领料单"),

    REPORT_LOSS(4, "报损单");

    private Integer code;
    private String name;

    DocumentType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static DocumentType getDocumentType(Integer code) {
        for (DocumentType documentType : DocumentType.values()) {
            if (documentType.getCode().equals(code)) {
                return documentType;
            }
        }
        return null;
    }

}
