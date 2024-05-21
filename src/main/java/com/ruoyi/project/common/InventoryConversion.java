package com.ruoyi.project.common;

import com.ruoyi.common.enums.StorageStatus;
import com.ruoyi.common.enums.WorkStatus;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.project.warehouse.domain.WmsItemStorage;
import com.ruoyi.project.warehouse.domain.WmsItemStorageDetail;
import com.ruoyi.project.warehouse.mapper.OriginItemStorageMapper;
import com.ruoyi.project.warehouse.service.IWmsItemStorageDetailService;
import com.ruoyi.project.warehouse.service.IWmsItemStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class InventoryConversion implements ApplicationRunner {

    @Autowired
    private OriginItemStorageMapper originItemStorageMapper;

    @Autowired
    private IWmsItemStorageService wmsItemStorageService;

    @Autowired
    private IWmsItemStorageDetailService wmsItemStorageDetailService;

    @Transactional(rollbackFor = Exception.class)
    public void convert() {
        log.info("库存转换开始 ======================================");
        originItemStorageMapper.selectAll().forEach(item -> {
            // 查询是否存在库存汇总
            WmsItemStorage wmsItemStorage = new WmsItemStorage();
            wmsItemStorage.setCompanyId(item.getCompanyId());
            wmsItemStorage.setWarehouseId(item.getWarehouseId());
            wmsItemStorage.setItemCode(item.getItemCode());
            WmsItemStorage itemStorage = wmsItemStorageService.selectWmsItemStorage(wmsItemStorage);
            String storageId = IdUtils.fastSimpleUUID();
            if (null != itemStorage) {
                storageId = itemStorage.getId();
                itemStorage.setQuantity(item.getQuantity().add(itemStorage.getQuantity()));
                wmsItemStorageService.updateWmsItemStorage(itemStorage);
            } else {
                WmsItemStorage addStorage = new WmsItemStorage();
                addStorage.setId(storageId);
                addStorage.setCompanyId(item.getCompanyId());
                addStorage.setWarehouseId(item.getWarehouseId());
                addStorage.setItemCode(item.getItemCode());
                addStorage.setItemName(item.getItemName());
                addStorage.setUnit(item.getUnit());
                addStorage.setSpec(item.getSpec());
                addStorage.setQuantity(item.getQuantity());
                wmsItemStorageService.insertWmsItemStorage(addStorage);
            }
            // 查询是否存在库存明细
            WmsItemStorageDetail itemStorageDetail = new WmsItemStorageDetail();
            itemStorageDetail.setWarehouseId(item.getWarehouseId());
            itemStorageDetail.setLocationId(item.getLocationId());
            itemStorageDetail.setItemCode(item.getItemCode());
            String[] split = item.getLot().split("-");
            String batchNo = split[split.length - 1];
            itemStorageDetail.setBatchNo(batchNo);
            WmsItemStorageDetail storageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(itemStorageDetail);
            if (null != storageDetail) {
                storageDetail.setQuantity(item.getQuantity().add(storageDetail.getQuantity()));
                wmsItemStorageDetailService.updateWmsItemStorageDetail(storageDetail);
            } else {
                WmsItemStorageDetail addDetail = new WmsItemStorageDetail();
                addDetail.setId(IdUtils.fastSimpleUUID());
                addDetail.setStorageId(storageId);
                addDetail.setQuantity(item.getQuantity());
                addDetail.setWarehouseId(item.getWarehouseId());
                addDetail.setLocationId(item.getLocationId());
                addDetail.setBatchNo(batchNo);
                addDetail.setExpireDate(item.getExpireDate());
                addDetail.setProductDate(item.getProductDate());
                addDetail.setItemCode(item.getItemCode());
                addDetail.setItemName(item.getItemName());
                addDetail.setWorkStatus(WorkStatus.NORMAL.getCode());
                addDetail.setStorageStatus(StorageStatus.QUALIFIED.getCode());
                wmsItemStorageDetailService.insertWmsItemStorageDetail(addDetail);
            }
        });
        log.info("库存转换结束");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        this.convert();
    }
}
