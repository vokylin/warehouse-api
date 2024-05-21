package com.ruoyi.project.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class BaseNoticeDTO implements Serializable {
    // wms的通知单id
    private String id;
    // 公司编码 K00001
    private String companyId;
    // 企通链的业务推送ID
    private String businessId;
    // 单据类型
    private String businessType;
    // 发货数量
    private BigDecimal quantity;

    // 是否是采购报废单
    private Integer scrapSpecialFlag;

    //通知单明细明细
    private List<BaseDetailDTO> detailDTOList;
}
