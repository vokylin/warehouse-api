package com.ruoyi.project.warehouse.domain;

import com.ruoyi.common.enums.RelateType;
import lombok.Data;


/**
 * 物料详情DTO
 *
 * @author REM
 * @date 2023/05/04
 */
@Data
public class ItemStorageDetailDto {

    /**
     * 关联业务ID
     */
    private String relateId;

    /**
     * 关联业务类型（未关联、上架中、待发货...）
     */
    private Integer relateType;

    /**
     * 物料批号
     */
    private String batchNo;

    /**
     * 物料编码
     */
    private String itemCode;

    private String locationId;

    public ItemStorageDetailDto(WmsItemStorageDetail storageDetail) {
        this.relateId = storageDetail.getRelateId();
        this.relateType = storageDetail.getRelateType();
        this.batchNo = storageDetail.getBatchNo();
        this.itemCode = storageDetail.getItemCode();
        this.locationId = storageDetail.getLocationId();
    }

    public ItemStorageDetailDto(WmsPickingWorkNoticeDetail workNoticeDetail, String locationId) {
        this.relateId = workNoticeDetail.getWorkNoticeId();
        this.relateType = RelateType.PICKING.getCode();
        this.batchNo = workNoticeDetail.getBatchNo();
        this.itemCode = workNoticeDetail.getItemCode();
        this.locationId = locationId;
    }
}
