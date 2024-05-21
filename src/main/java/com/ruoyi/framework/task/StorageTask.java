package com.ruoyi.framework.task;

import com.ruoyi.common.enums.LimitedStatus;
import com.ruoyi.project.system.domain.WmsItemAttribute;
import com.ruoyi.project.system.service.IWmsItemAttributeService;
import com.ruoyi.project.warehouse.domain.WmsItemStorageDetail;
import com.ruoyi.project.warehouse.service.IWmsItemStorageDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component("storageTask")
public class StorageTask {

    @Autowired
    private IWmsItemStorageDetailService wmsItemStorageDetailService;

    @Autowired
    private IWmsItemAttributeService wmsItemAttributeService;

    /**
     * 临期预警
     */
    public void earlyWarning() {

        // 更新过期库存
        wmsItemStorageDetailService.updateTheExpiredItemStatus();

        // 查询出临期的库存
        List<WmsItemStorageDetail> wmsItemStorageDetails = wmsItemStorageDetailService.selectAdventItem();
        if (wmsItemStorageDetails.isEmpty()) {
            return;
        }

        // 更新临期库存
        wmsItemStorageDetailService.updateAdventStatus(wmsItemStorageDetails.stream().map(WmsItemStorageDetail::getId).collect(Collectors.toList()));
    }

    /**
     * 上下限预警
     */
    public void limitWarning() {
        List<WmsItemAttribute> wmsItemAttributes = wmsItemAttributeService.selectAll();
        if (wmsItemAttributes.isEmpty()) {
            return;
        }
        wmsItemAttributes.forEach((itemAttribute) -> {
            WmsItemStorageDetail itemStorageDetail = new WmsItemStorageDetail();
            itemStorageDetail.setItemCode(itemAttribute.getItemCode());
            List<WmsItemStorageDetail> wmsItemStorageDetails = wmsItemStorageDetailService.selectWmsItemStorageDetailList(itemStorageDetail);
            if (wmsItemStorageDetails.isEmpty()) {
                return;
            }
            wmsItemStorageDetails.forEach((storageDetail) -> {
                Long upQuality = itemAttribute.getUpQuality();
                Long downQuality = itemAttribute.getDownQuality();
                BigDecimal itemQty = storageDetail.getQuantity();
                if (itemQty.compareTo(new BigDecimal(upQuality)) > 0) {
                    // 超过上限
                    itemAttribute.setLimitedStatus(LimitedStatus.UP.getCode());
                    wmsItemAttributeService.updateWmsItemAttribute(itemAttribute);
                } else if (itemQty.compareTo(new BigDecimal(downQuality)) < 0) {
                    // 超过下限
                    itemAttribute.setLimitedStatus(LimitedStatus.DOWN.getCode());
                    wmsItemAttributeService.updateWmsItemAttribute(itemAttribute);
                }
            });
        });
    }


}
