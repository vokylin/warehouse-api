package com.ruoyi.project.common.entity;

import lombok.Data;

/**
 * @Description: 发货完成后，wms回调企通链的 请求实体
 * @Author: hlp
 * @Date: 2021-04-09 16:41
 **/
@Data
public class DeliveryNoticeDTO extends BaseNoticeDTO {

    private String carriersCode;        // 发运承运商编码
    private String carriersName;        // 承运商名称
    private String carInfo;        // 车辆信息
    private String logisticsNo;        // 物流单号信息
}
