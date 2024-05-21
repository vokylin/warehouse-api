package com.ruoyi.project.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 通知仓库发货Dto
 *
 * @author gaomian
 * @since 2023-04-18
 */
@Data
public class NoticeWmsDeliveryDto extends NoticeWmsBaseDto implements Serializable {

    private static final long serialVersionUID = 6542332506642758107L;

    /**
     * 接收方编码
     */
    private String toCode;
    /**
     * 接收方名称
     */
    private String toName;
    /**
     * 接收方联系人
     */
    private String toContacts;
    /**
     * 接收方联系电话
     */
    private String toContactsTel;
    /**
     * 接收方联系地址
     */
    private String toAddress;

    /**
     * 出库物料明细
     */
    private List<NoticeWmsDeliveryDetailDTO> deliveryDetailList;

}
