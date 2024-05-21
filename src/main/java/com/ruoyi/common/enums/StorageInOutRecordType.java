package com.ruoyi.common.enums;

public enum StorageInOutRecordType {

    // 0 出库 1 入库
    OUT(0, "出库"),
    IN(1, "入库");


    private Integer code;
    private String name;

    StorageInOutRecordType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static StorageInOutRecordType getDocumentType(Integer code) {
        for (StorageInOutRecordType documentType : StorageInOutRecordType.values()) {
            if (documentType.getCode().equals(code)) {
                return documentType;
            }
        }
        return null;
    }

}
