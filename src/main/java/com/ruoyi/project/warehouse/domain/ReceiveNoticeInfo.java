package com.ruoyi.project.warehouse.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 收货通知单信息
 *
 * @author REM
 * @date 2023/04/08
 */
@Data
@Builder
public class ReceiveNoticeInfo {

    public WmsReceiveNotice wmsReceiveNotice;

    public WmsReceiveNoticeDetail wmsReceiveNoticeDetail;

    public WmsReceiveItemDetail wmsReceiveItemDetail;

    public List<WmsReceiveNoticeDetail> wmsReceiveNoticeDetailList;

    public List<WmsReceiveItemDetail> wmsReceiveItemDetailList;

    public String workNoticeId;

}
