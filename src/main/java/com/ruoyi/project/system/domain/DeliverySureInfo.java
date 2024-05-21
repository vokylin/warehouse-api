package com.ruoyi.project.system.domain;

import com.ruoyi.project.warehouse.domain.DeliveryNoticeInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DeliverySureInfo {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 出通知单ID
     */
    private String deliveryNoticeId;

    private DeliveryNoticeInfo deliveryNotice;

    private Integer result;

    private String remark;

    private String createUserName;

    private Date createTime;

    private List<WmsDeliverySureDetail> deliverySureDetailList;
}
