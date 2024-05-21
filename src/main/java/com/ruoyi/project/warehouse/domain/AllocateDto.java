package com.ruoyi.project.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 调拨DTO
 *
 * @author REM
 * @date 2023/05/05
 */
@Data
public class AllocateDto {
    private String deliveryType;

    private String receiveType;

    private String receiveNoticeId;

    private String deliveryNoticeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date occTime;

    private List<WmsItemStorageDetail> itemStorageDetailList;
}
