package com.ruoyi.common.enums;

public enum BillType {

    // 0 出库 1 入库
    IN(0, "入库"),
    RETURN(1, "退料"),
    RECEIVE(2, "领料"),

    SCRAP(3, "报废"),

    INVENTORY(4, "盘点");


    private Integer code;
    private String name;

    BillType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


}
