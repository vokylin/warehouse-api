package com.ruoyi.project.warehouse.domain;


import com.ruoyi.project.system.domain.WmsDeliverySureDetail;
import lombok.Data;

import java.util.List;

/**
 * 出库确认信息
 *
 * @author REM
 * @date 2023/04/17
 */
@Data
public class DeliveryConfirmInfo {

    /**
     * 出库通知单号
     */
    private String deliveryNoticeId;

    /**
     * 发运结果
     */
    private Integer deliveryResult;

    /**
     * 备注
     */
    private String remark;


    /**
     * 出库确认项明细
     */
    private List<WmsDeliverySureDetail> wmsDeliverySureDetailList;


}
