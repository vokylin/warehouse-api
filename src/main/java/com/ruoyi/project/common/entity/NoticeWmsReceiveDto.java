package com.ruoyi.project.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 通知仓库接收物品Dto
 *
 * @author gaomian
 * @since 2023-04-18
 */
@Data
public class NoticeWmsReceiveDto extends NoticeWmsBaseDto implements Serializable {

    private static final long serialVersionUID = 4533332506642758107L;

    /**
     * 发货方编码
     */
    private String fromCode;
    /**
     * 发货方名称
     */
    private String fromName;

    /**
     * 生产taskID
     */
    private String taskId;

    private Integer scrapSpecialFlag;

    /**
     * 入库物料详情
     */
    List<NoticeWmsReceiveDetailDTO> receiveDetail;

}
