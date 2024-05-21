package com.ruoyi.project.warehouse.domain;

import lombok.Data;

@Data
public class FeedstockFormPrintResult {

    private String code;

    private String message;

    private FeedstockFormPrintDetailVO data;
}
