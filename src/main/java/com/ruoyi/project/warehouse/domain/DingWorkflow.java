package com.ruoyi.project.warehouse.domain;

import lombok.Data;

import java.util.Date;

@Data
public class DingWorkflow {
    private String id;

    private String businessId;

    private String businessCode;

    private String businessName;

    private String businessTag;

    private String applyUserId;

    private String applyUserName;

    private Date applyTime;

    private String dingdingId;

    private String dingdingName;

    private String auditFinalStatus;

    private Date auditFinalTime;

    private String enterpriseKey;

    private String isDeleted;

    private String title;

    private String remark;

    private String type;

    private String processCode;

    private String dingBusinessId;
}
