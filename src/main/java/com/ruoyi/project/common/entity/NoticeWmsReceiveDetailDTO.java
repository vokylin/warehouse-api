package com.ruoyi.project.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 入库通知物料明细
 * @Author: gaomian
 * @Date: 2023-04-18
 **/
@Data
public class NoticeWmsReceiveDetailDTO extends NoticeWmsDetailBaseDto implements Serializable {

    private static final long serialVersionUID = -1653432506642758107L;

    /**
     * 批号
     */
    private String batchNo;

    /**
     * 物料单价
     */
    private BigDecimal itemPrice;

    /**
     * 货物类型： 进料、进货、其他
     */
    private Integer itemType;
    /**
     * 验收单ID
     */
    private String formId;
    /**
     * 生产日期
     */
    private Date productDate;
    /**
     * 过期日期
     */
    private Date expireTime;

}
