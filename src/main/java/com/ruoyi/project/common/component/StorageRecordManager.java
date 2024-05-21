package com.ruoyi.project.common.component;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.BillType;
import com.ruoyi.common.enums.ScrapSpecialFlag;
import com.ruoyi.common.enums.StorageInOutRecordType;
import com.ruoyi.common.enums.WmsReceiveTypeEnum;
import com.ruoyi.common.exception.business.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.project.system.domain.Warehouse;
import com.ruoyi.project.system.service.IWarehouseService;
import com.ruoyi.project.warehouse.domain.*;
import com.ruoyi.project.warehouse.mapper.WmsStorageInOutDetailMapper;
import com.ruoyi.project.warehouse.mapper.WmsStorageInOutRecordMapper;
import com.ruoyi.project.warehouse.service.IWmsItemStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 出入库记录服务
 *
 * @author REM
 * @date 2023/04/08
 */
@Component
@Slf4j
public class StorageRecordManager {

    @Autowired
    private WmsStorageInOutRecordMapper wmsStorageInOutRecordMapper;

    @Autowired
    private WmsStorageInOutDetailMapper wmsStorageInOutDetailMapper;

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IWmsItemStorageService wmsItemStorageService;


    /**
     * 保存或更新 入库记录
     *
     * @param receiveNoticeInfo 收到通知信息
     * @return int
     */
    public List<BigDecimal> saveInRecord(ReceiveNoticeInfo receiveNoticeInfo) {
        // 入库通知分配明细
        WmsReceiveItemDetail wmsReceiveItemDetail = receiveNoticeInfo.getWmsReceiveItemDetail();
        // 入库通知明细
        WmsReceiveNoticeDetail wmsReceiveNoticeDetail = receiveNoticeInfo.getWmsReceiveNoticeDetail();
        // 入库通知
        WmsReceiveNotice wmsReceiveNotice = receiveNoticeInfo.getWmsReceiveNotice();

        // 查询物料的库存汇总
        List<WmsItemStorage> wmsItemStorage = getWmsItemStorage(wmsReceiveItemDetail.getItemCode(), wmsReceiveNotice.getCompanyId());
        // 滚算前的价格、数量、总的库存金额、取公司的所有仓库中该物料的数量
        BigDecimal beforeRollingPrice = BigDecimal.ZERO;
        BigDecimal beforeRollingTotalPrice = BigDecimal.ZERO;
        BigDecimal beforeRollingQuantity = BigDecimal.ZERO;
        if (CollectionUtils.isNotEmpty(wmsItemStorage)) {
            // 滚算前物料库存数量、单价
            beforeRollingQuantity = wmsItemStorage.stream().map(WmsItemStorage::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal actualPrice = wmsItemStorage.get(0).getActualPrice();
            beforeRollingPrice = actualPrice == null ? BigDecimal.ZERO : actualPrice;
            // 滚算前库存金额
            BigDecimal storagePrice = wmsItemStorage.get(0).getStoragePrice();
            beforeRollingTotalPrice = storagePrice == null ? BigDecimal.ZERO : storagePrice;
        }
        // 入库的物料数量
        BigDecimal quantity;

        // 入库类型为报废或特采时，取不合格数量
        if (ScrapSpecialFlag.SCRAP.getCode().equals(wmsReceiveNotice.getScrapSpecialFlag()) || ScrapSpecialFlag.SPECIAL.getCode().equals(wmsReceiveNotice.getScrapSpecialFlag())) {
            quantity = wmsReceiveItemDetail.getFailQuantity();
        } else {
            quantity = wmsReceiveItemDetail.getPassQuantity();
        }

        BigDecimal itemPrice = wmsReceiveNoticeDetail.getItemPrice() == null ? BigDecimal.ZERO : wmsReceiveNoticeDetail.getItemPrice();
        BigDecimal itemTotalPrice;
        // 未税金额 = 入库单价 * 入库数量
        itemTotalPrice = itemPrice.multiply(quantity);
        // 采购入库的未税金额保留2位小数 (上传发票计算差值时不会出错)
        if (WmsReceiveTypeEnum.PURCHASE.getCode().equals(wmsReceiveNotice.getBusinessType())) {
            itemTotalPrice = itemTotalPrice.setScale(2, RoundingMode.HALF_UP);
        }

        //库存金额 : 滚算前库存金额 + 入库的未税金额 = 新的库存金额
        BigDecimal storageAmount = beforeRollingTotalPrice.add(itemTotalPrice);
        BigDecimal totalQuantity = beforeRollingQuantity.add(quantity);

        // 滚算后单价
        BigDecimal afterRollingPrice = storageAmount.divide(totalQuantity, 6, RoundingMode.HALF_UP);
        // 分别存放滚算后单价、新的库存金额
        List<BigDecimal> priceInfo = new ArrayList<>();
        priceInfo.add(afterRollingPrice);
        priceInfo.add(storageAmount);

        WmsStorageInOutRecord wmsStorageInOutRecord = new WmsStorageInOutRecord();
        wmsStorageInOutRecord.setItemCode(wmsReceiveItemDetail.getItemCode());
        wmsStorageInOutRecord.setBatchNo(wmsReceiveItemDetail.getBatchNo());
        wmsStorageInOutRecord.setNoticeId(wmsReceiveNotice.getId());
        wmsStorageInOutRecord.setType(StorageInOutRecordType.IN.getCode());
        wmsStorageInOutRecord.setCompanyId(wmsReceiveNotice.getCompanyId());
        WmsStorageInOutRecord storageInOutRecord = wmsStorageInOutRecordMapper.selectByBatchNo(wmsStorageInOutRecord);

        // 查询物料批次的库存数量
        BigDecimal itemBatchNoQuantity = getItemBatchNoQuantity(wmsReceiveItemDetail.getItemCode(),
                wmsReceiveItemDetail.getBatchNo(), wmsReceiveNotice.getCompanyId());
        // 物料批次的原库存数量、结存库存数量
        BigDecimal openingQuantity = itemBatchNoQuantity == null ? BigDecimal.ZERO : itemBatchNoQuantity;
        BigDecimal surplusQuantity = openingQuantity.add(quantity);

        if (null == storageInOutRecord) {
            // 不存在则新增
            WmsStorageInOutRecord record = new WmsStorageInOutRecord();
            record.setId(IdUtils.fastSimpleUUID());
            record.setType(StorageInOutRecordType.IN.getCode());
            record.setNoticeId(wmsReceiveNotice.getId());
            record.setItemCode(wmsReceiveItemDetail.getItemCode());
            record.setBatchNo(wmsReceiveItemDetail.getBatchNo());
            record.setItemName(wmsReceiveItemDetail.getItemName());
            record.setUnit(wmsReceiveNoticeDetail.getUnit());
            record.setSpec(wmsReceiveNoticeDetail.getSpec());
            record.setCompanyId(wmsReceiveNotice.getCompanyId());
            record.setQuantity(quantity);
            record.setBeforeRollingPrice(beforeRollingPrice);
            record.setBeforeRollingQuantity(beforeRollingQuantity);
            record.setBeforeRollingTotalPrice(beforeRollingTotalPrice);
            record.setOccTime(wmsReceiveNotice.getStartTime());
            record.setOccPrice(itemPrice);
            record.setOccQuantity(quantity);
            record.setOccTotalPrice(itemTotalPrice);
            record.setAfterRollingPrice(afterRollingPrice);
            record.setAfterRollingQuantity(totalQuantity);
            record.setAfterRollingTotalPrice(storageAmount);
            record.setOpeningQuantity(openingQuantity);
            record.setOpeningAmount(openingQuantity.multiply(beforeRollingPrice));
            record.setSurplusQuantity(surplusQuantity);
            record.setSurplusAmount(surplusQuantity.multiply(afterRollingPrice));
            record.setBusinessNo(wmsReceiveNotice.getBusinessId());
            Integer billType = BillType.IN.getCode();
            // 生产退料、研发退料、质量留样退料是 退料单
            if (WmsReceiveTypeEnum.MANUFACTURE_REFUND.getCode().equals(wmsReceiveNotice.getBusinessType()) ||
                    WmsReceiveTypeEnum.DEVELOP_REFUND.getCode().equals(wmsReceiveNotice.getBusinessType()) ||
                    WmsReceiveTypeEnum.SAMPLE_REFUND.getCode().equals(wmsReceiveNotice.getBusinessType())) {
                billType = BillType.RETURN.getCode();
            }
            record.setBillType(billType);
            record.setReceiveSendType(wmsReceiveNotice.getReceiveSendType());
            record.setCreateBy(SecurityUtils.getUserId());
            record.setCreateTime(DateUtils.getNowDate());
            wmsStorageInOutRecordMapper.insertWmsStorageInOutRecord(record);
            log.info("insertWmsStorageInOutRecord：出入库记录ID ======> {}, 物料编码：{} ,滚算前库存金额===>{} , 滚算前库存数量====>{}, " +
                            "物料入库单价====> {}, 未税金额=====>{}, 物料入库数量 =====> {}, 滚算后单价 =====> {}", record.getId(), record.getItemCode(), beforeRollingTotalPrice,
                    beforeRollingQuantity, itemPrice, itemTotalPrice, quantity, afterRollingPrice);
        } else {
            // 存在则更新，更新时保持出入库记录原有的滚算前单价、数量；以现有库存的单价、数量做计算后更新滚算后的数量
            storageInOutRecord.setQuantity(storageInOutRecord.getQuantity().add(quantity));
            storageInOutRecord.setOccQuantity(storageInOutRecord.getOccQuantity().add(quantity));
            storageInOutRecord.setOccTotalPrice(storageInOutRecord.getOccQuantity().multiply(storageInOutRecord.getOccPrice()));
            storageInOutRecord.setAfterRollingPrice(afterRollingPrice);
            storageInOutRecord.setAfterRollingQuantity(totalQuantity);
            storageInOutRecord.setAfterRollingTotalPrice(storageAmount);
            BigDecimal hisOccQuantity = storageInOutRecord.getOccQuantity() == null ? BigDecimal.ZERO : storageInOutRecord.getOccQuantity();
            storageInOutRecord.setOpeningQuantity(surplusQuantity.subtract(hisOccQuantity));
            storageInOutRecord.setOpeningAmount(storageInOutRecord.getOpeningQuantity().multiply(storageInOutRecord.getBeforeRollingPrice()));
            storageInOutRecord.setSurplusQuantity(surplusQuantity);
            storageInOutRecord.setSurplusAmount(surplusQuantity.multiply(afterRollingPrice));
            storageInOutRecord.setUpdateBy(SecurityUtils.getUserId());
            storageInOutRecord.setUpdateTime(DateUtils.getNowDate());
            wmsStorageInOutRecordMapper.updateWmsStorageInOutRecord(storageInOutRecord);
            log.info("updateWmsStorageInOutRecord：出入库记录ID ======> {}, 物料编码：{} ,滚算前库存金额===>{} , 滚算前库存数量====>{}, " +
                            "物料入库单价====> {}, 未税金额=====>{}, 物料入库数量 =====> {}, 滚算后单价 =====> {}", storageInOutRecord.getId(), storageInOutRecord.getItemCode(),
                    storageInOutRecord.getBeforeRollingTotalPrice(), storageInOutRecord.getBeforeRollingQuantity(), itemPrice,
                    itemTotalPrice, quantity, afterRollingPrice);
        }
        return priceInfo;
    }

    /**
     * 查询物料的库存汇总
     *
     * @param itemCode
     * @param companyId
     * @return
     */
    private List<WmsItemStorage> getWmsItemStorage(String itemCode, String companyId) {
        // 查询物料的库存汇总
        WmsItemStorage itemStorage = new WmsItemStorage();
        itemStorage.setItemCode(itemCode);
        itemStorage.setCompanyId(companyId);
        return wmsItemStorageService.selectWmsItemStorageByCompany(itemStorage);
    }

    /**
     * 查询物料某个批次的库存数量
     *
     * @return
     */
    private BigDecimal getItemBatchNoQuantity(String itemCode, String batchNo, String companyId) {
        WmsItemStorage itemInfo = new WmsItemStorage();
        itemInfo.setItemCode(itemCode);
        itemInfo.setBatchNo(batchNo);
        itemInfo.setCompanyId(companyId);
        return wmsItemStorageService.getItemStorageBatchNoQuantity(itemInfo);
    }

    /**
     * 保存记录详细
     *
     * @param wmsWorkNoticeAllot wms注意分配工作
     */
    public void saveInRecordDetail(WmsWorkNoticeAllot wmsWorkNoticeAllot) {

        // 根据物料编码查询出入库记录
        WmsStorageInOutRecord wmsStorageInOutRecord = new WmsStorageInOutRecord();
        wmsStorageInOutRecord.setItemCode(wmsWorkNoticeAllot.getItemCode());
        wmsStorageInOutRecord.setBatchNo(wmsWorkNoticeAllot.getBatchNo());
        wmsStorageInOutRecord.setType(StorageInOutRecordType.IN.getCode());
        wmsStorageInOutRecord.setNoticeId(wmsWorkNoticeAllot.getNoticeId());
        WmsStorageInOutRecord storageInOutRecord = wmsStorageInOutRecordMapper.selectByBatchNo(wmsStorageInOutRecord);

        // 查询仓库信息
        Warehouse warehouse = warehouseService.selectWarehouseById(wmsWorkNoticeAllot.getWarehouseId());

        // 保存出入库明细
        WmsStorageInOutDetail wmsStorageInOutDetail = new WmsStorageInOutDetail();
        wmsStorageInOutDetail.setId(IdUtils.fastSimpleUUID());
        wmsStorageInOutDetail.setRecodeId(storageInOutRecord.getId());
        wmsStorageInOutDetail.setWarehouseCode(warehouse.getCode());
        wmsStorageInOutDetail.setItemCode(wmsWorkNoticeAllot.getItemCode());
        wmsStorageInOutDetail.setWarehouseId(wmsWorkNoticeAllot.getWarehouseId());
        wmsStorageInOutDetail.setLocationId(wmsWorkNoticeAllot.getTargetLocationId());
        wmsStorageInOutDetail.setQuantity(wmsWorkNoticeAllot.getQuantity());
        wmsStorageInOutDetail.setCreateBy(SecurityUtils.getUserId());
        wmsStorageInOutDetail.setCreateTime(DateUtils.getNowDate());
        wmsStorageInOutDetailMapper.insertWmsStorageInOutDetail(wmsStorageInOutDetail);
    }


    /**
     * 保存出库通知的出库记录
     */
    public void saveStorageOutRecord(WmsItemStorageDetail wmsItemStorageDetail, WmsDeliveryNotice wmsDeliveryNotice) {
        // 查询物料的库存汇总, 获取出库前物料库存数量
        List<WmsItemStorage> wmsItemStorage = getWmsItemStorage(wmsItemStorageDetail.getItemCode(), wmsDeliveryNotice.getCompanyId());
        BigDecimal beforeQuantity = wmsItemStorage.stream().map(WmsItemStorage::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        WmsItemStorage storage = wmsItemStorage.get(0);

        // 出库数量
        BigDecimal quantity = wmsItemStorageDetail.getQuantity();
        // 出库数量 > 库存数量发生异常
        if (quantity.compareTo(beforeQuantity) > 0) {
            throw new BusinessException(String.format("物料编码：%s的出库数量大于库存数量", wmsItemStorageDetail.getItemCode()));
        }
        // 物料使用完的标志
        boolean isUseUp = beforeQuantity.equals(quantity);

        // 查询物料批次的库存数量
        BigDecimal itemBatchNoQuantity = getItemBatchNoQuantity(wmsItemStorageDetail.getItemCode(),
                wmsItemStorageDetail.getBatchNo(), wmsDeliveryNotice.getCompanyId());
        if (itemBatchNoQuantity == null) {
            throw new BusinessException(String.format("物料编码：%s的批次%s不存在，无法出库",
                    wmsItemStorageDetail.getItemCode(), wmsItemStorageDetail.getBatchNo()));
        }
        BigDecimal surplusQuantity = itemBatchNoQuantity.subtract(quantity);

        // 查询出入库记录
        WmsStorageInOutRecord inOutRecord = new WmsStorageInOutRecord();
        inOutRecord.setItemCode(wmsItemStorageDetail.getItemCode());
        inOutRecord.setBatchNo(wmsItemStorageDetail.getBatchNo());
        inOutRecord.setNoticeId(wmsDeliveryNotice.getId());
        inOutRecord.setType(StorageInOutRecordType.OUT.getCode());
        inOutRecord.setCompanyId(wmsDeliveryNotice.getCompanyId());
        WmsStorageInOutRecord storageInOutRecord = wmsStorageInOutRecordMapper.selectByBatchNo(inOutRecord);
        BigDecimal outStoragePrice = storage.getActualPrice();
        if (null == storageInOutRecord) {
            // 不存在则新增
            WmsStorageInOutRecord wmsStorageInOutRecord = new WmsStorageInOutRecord();
            wmsStorageInOutRecord.setId(IdUtils.fastSimpleUUID());
            wmsStorageInOutRecord.setItemCode(wmsItemStorageDetail.getItemCode());
            wmsStorageInOutRecord.setItemName(wmsItemStorageDetail.getItemName());
            wmsStorageInOutRecord.setUnit(wmsItemStorageDetail.getUnit());
            wmsStorageInOutRecord.setSpec(wmsItemStorageDetail.getSpec());
            wmsStorageInOutRecord.setCompanyId(wmsDeliveryNotice.getCompanyId());
            wmsStorageInOutRecord.setBatchNo(wmsItemStorageDetail.getBatchNo());
            wmsStorageInOutRecord.setQuantity(quantity);
            wmsStorageInOutRecord.setType(StorageInOutRecordType.OUT.getCode());
            // 出库前单价、库存金额、库存数量
            wmsStorageInOutRecord.setBeforeRollingPrice(outStoragePrice);
            wmsStorageInOutRecord.setBeforeRollingTotalPrice(storage.getStoragePrice());
            wmsStorageInOutRecord.setBeforeRollingQuantity(beforeQuantity);
            wmsStorageInOutRecord.setOccQuantity(quantity);
            wmsStorageInOutRecord.setOpeningQuantity(itemBatchNoQuantity);
            if (isUseUp) {
                // 物料使用完出库金额就是库存的金额，重新计算出库的单价
                BigDecimal storagePrice = storage.getStoragePrice();
                wmsStorageInOutRecord.setOccTotalPrice(storagePrice);
                wmsStorageInOutRecord.setOccPrice(storagePrice.divide(quantity, 6, RoundingMode.HALF_UP));
                wmsStorageInOutRecord.setAfterRollingPrice(BigDecimal.ZERO);
                wmsStorageInOutRecord.setAfterRollingQuantity(BigDecimal.ZERO);
                wmsStorageInOutRecord.setAfterRollingTotalPrice(BigDecimal.ZERO);
                // 物料此时使用完，则用库存金额作为期初的金额，重新计算期初单价
                wmsStorageInOutRecord.setOpeningAmount(storagePrice);
                wmsStorageInOutRecord.setBeforeRollingPrice(storagePrice.divide(quantity, 6, RoundingMode.HALF_UP));
            } else {
                // 本批次物料未使用完以 单价 * 数量得到出库金额
                wmsStorageInOutRecord.setOccPrice(outStoragePrice);
                BigDecimal occTotalPrice = wmsStorageInOutRecord.getOccPrice().multiply(wmsStorageInOutRecord.getOccQuantity());
                wmsStorageInOutRecord.setOccTotalPrice(occTotalPrice);
                wmsStorageInOutRecord.setAfterRollingPrice(outStoragePrice);
                wmsStorageInOutRecord.setAfterRollingQuantity(beforeQuantity.subtract(wmsItemStorageDetail.getQuantity()));
                wmsStorageInOutRecord.setAfterRollingTotalPrice(storage.getStoragePrice().subtract(occTotalPrice));
                wmsStorageInOutRecord.setOpeningAmount(itemBatchNoQuantity.multiply(outStoragePrice));
            }
            wmsStorageInOutRecord.setSurplusQuantity(surplusQuantity);
            wmsStorageInOutRecord.setSurplusAmount(surplusQuantity.multiply(outStoragePrice));
            wmsStorageInOutRecord.setOccTime(wmsDeliveryNotice.getDeliveryStartTime());
            wmsStorageInOutRecord.setNoticeId(wmsDeliveryNotice.getId());
            wmsStorageInOutRecord.setBusinessNo(wmsDeliveryNotice.getBusinessId());
            wmsStorageInOutRecord.setBillType(BillType.RECEIVE.getCode());
            wmsStorageInOutRecord.setReceiveSendType(wmsDeliveryNotice.getReceiveSendType());
            wmsStorageInOutRecord.setCreateBy(SecurityUtils.getUserId());
            wmsStorageInOutRecord.setCreateTime(DateUtils.getNowDate());
            wmsStorageInOutRecord.setUpdateBy(SecurityUtils.getUserId());
            wmsStorageInOutRecord.setUpdateTime(DateUtils.getNowDate());
            wmsStorageInOutRecordMapper.insertWmsStorageInOutRecord(wmsStorageInOutRecord);
            storageInOutRecord = wmsStorageInOutRecord;
        } else {
            // 存在则更新，更新时保持出入库记录原有出库单价、数量；以现有库存的单价、数量做计算后更新滚算后的数量
            storageInOutRecord.setQuantity(storageInOutRecord.getQuantity().add(quantity));
            BigDecimal hisOccQuantity = storageInOutRecord.getOccQuantity() == null ? BigDecimal.ZERO : storageInOutRecord.getOccQuantity();
            storageInOutRecord.setOccQuantity(hisOccQuantity.add(quantity));
            if (isUseUp) {
                // 物料用完时，领走的金额为之前领走金额 + 剩余库存金额
                storageInOutRecord.setOccTotalPrice(storageInOutRecord.getOccTotalPrice().add(storage.getStoragePrice()));
                storageInOutRecord.setOccPrice(storageInOutRecord.getOccTotalPrice().divide(storageInOutRecord.getOccQuantity(), 6, RoundingMode.HALF_UP));
                storageInOutRecord.setAfterRollingPrice(BigDecimal.ZERO);
                storageInOutRecord.setAfterRollingQuantity(BigDecimal.ZERO);
                storageInOutRecord.setAfterRollingTotalPrice(BigDecimal.ZERO);
            } else {
                storageInOutRecord.setOccTotalPrice(storageInOutRecord.getOccQuantity().multiply(storageInOutRecord.getOccPrice()));
                storageInOutRecord.setAfterRollingQuantity(storageInOutRecord.getBeforeRollingQuantity().subtract(storageInOutRecord.getOccQuantity()));
                storageInOutRecord.setAfterRollingTotalPrice(storageInOutRecord.getBeforeRollingTotalPrice()
                        .subtract(storageInOutRecord.getOccTotalPrice()));
            }
            // 物料批次当前库存数量 + 计算过的出库数量 = 出库前库存数量
            storageInOutRecord.setOpeningQuantity(itemBatchNoQuantity.add(hisOccQuantity));
            storageInOutRecord.setOpeningAmount(storageInOutRecord.getOpeningQuantity().multiply(storageInOutRecord.getBeforeRollingPrice()));
            storageInOutRecord.setSurplusQuantity(surplusQuantity);
            storageInOutRecord.setSurplusAmount(surplusQuantity.multiply(storageInOutRecord.getAfterRollingPrice()));
            storageInOutRecord.setUpdateBy(SecurityUtils.getUserId());
            storageInOutRecord.setUpdateTime(DateUtils.getNowDate());
            wmsStorageInOutRecordMapper.updateWmsStorageInOutRecord(storageInOutRecord);
        }

        // 查询仓库信息
        Warehouse warehouse = warehouseService.selectWarehouseById(wmsItemStorageDetail.getWarehouseId());

        // 记录出库明细
        WmsStorageInOutDetail wmsStorageInOutDetail = new WmsStorageInOutDetail();
        wmsStorageInOutDetail.setId(IdUtils.fastSimpleUUID());
        wmsStorageInOutDetail.setRecodeId(storageInOutRecord.getId());
        wmsStorageInOutDetail.setWarehouseCode(warehouse.getCode());
        wmsStorageInOutDetail.setWarehouseId(warehouse.getId());
        wmsStorageInOutDetail.setLocationId(wmsItemStorageDetail.getLocationId());
        wmsStorageInOutDetail.setQuantity(wmsItemStorageDetail.getQuantity());
        wmsStorageInOutDetail.setItemCode(wmsItemStorageDetail.getItemCode());
        wmsStorageInOutDetail.setCreateBy(SecurityUtils.getUserId());
        wmsStorageInOutDetail.setCreateTime(DateUtils.getNowDate());
        wmsStorageInOutDetailMapper.insertWmsStorageInOutDetail(wmsStorageInOutDetail);
    }


    /**
     * 保存调拨的出入库记录
     *
     * @param allocateDto
     */
    public void saveStorageInOutRecord(AllocateDto allocateDto) {
        String companyId = ServletUtils.getCompanyId();
        allocateDto.getItemStorageDetailList().forEach(wmsItemStorageDetail -> {
            // 查询物料的库存汇总, 获取出库前物料库存数量
            List<WmsItemStorage> wmsItemStorage = getWmsItemStorage(wmsItemStorageDetail.getItemCode(), companyId);
            BigDecimal beforeQuantity = wmsItemStorage.stream().map(WmsItemStorage::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
            WmsItemStorage storage = wmsItemStorage.get(0);
            BigDecimal price = BigDecimal.ZERO;
            if (null != wmsItemStorageDetail.getActualPrice()) {
                price = wmsItemStorageDetail.getActualPrice();
            }

            // 出库记录
            WmsStorageInOutRecord wmsStorageInOutRecord = new WmsStorageInOutRecord();
            wmsStorageInOutRecord.setId(IdUtils.fastSimpleUUID());
            wmsStorageInOutRecord.setItemCode(wmsItemStorageDetail.getItemCode());
            wmsStorageInOutRecord.setItemName(wmsItemStorageDetail.getItemName());
            wmsStorageInOutRecord.setUnit(wmsItemStorageDetail.getUnit());
            wmsStorageInOutRecord.setSpec(wmsItemStorageDetail.getSpec());
            // 调拨前后、单价保持不变，数量一进一出，总数量保持不变
            wmsStorageInOutRecord.setBeforeRollingPrice(price);
            wmsStorageInOutRecord.setBeforeRollingTotalPrice(storage.getStoragePrice());
            wmsStorageInOutRecord.setBeforeRollingQuantity(beforeQuantity);
            wmsStorageInOutRecord.setOccPrice(price);
            wmsStorageInOutRecord.setOccQuantity(wmsItemStorageDetail.getAllotQuantity());
            wmsStorageInOutRecord.setOccTotalPrice(price.multiply(wmsItemStorageDetail.getAllotQuantity()));
            wmsStorageInOutRecord.setAfterRollingPrice(price);
            wmsStorageInOutRecord.setAfterRollingTotalPrice(storage.getStoragePrice().subtract(wmsStorageInOutRecord.getOccTotalPrice()));
            wmsStorageInOutRecord.setAfterRollingQuantity(beforeQuantity.subtract(wmsItemStorageDetail.getAllotQuantity()));
            // 调拨不记录批次物料的期初、结存数据（物料数量、金额没有变动）
            wmsStorageInOutRecord.setCompanyId(companyId);
            wmsStorageInOutRecord.setBatchNo(wmsItemStorageDetail.getBatchNo());
            wmsStorageInOutRecord.setQuantity(wmsItemStorageDetail.getAllotQuantity());
            wmsStorageInOutRecord.setNoticeId(allocateDto.getDeliveryNoticeId());
            wmsStorageInOutRecord.setCreateBy(SecurityUtils.getUserId());
            wmsStorageInOutRecord.setType(StorageInOutRecordType.OUT.getCode());
            wmsStorageInOutRecord.setBillType(BillType.RECEIVE.getCode());
            wmsStorageInOutRecord.setReceiveSendType(allocateDto.getDeliveryType());
            wmsStorageInOutRecord.setCreateTime(DateUtils.getNowDate());
            wmsStorageInOutRecord.setOccTime(allocateDto.getOccTime());
            wmsStorageInOutRecordMapper.insertWmsStorageInOutRecord(wmsStorageInOutRecord);

            // 查询仓库信息
            Warehouse warehouse = warehouseService.selectWarehouseById(wmsItemStorageDetail.getWarehouseId());

            // 出库明细
            WmsStorageInOutDetail wmsStorageInOutDetail = new WmsStorageInOutDetail();
            wmsStorageInOutDetail.setId(IdUtils.fastSimpleUUID());
            wmsStorageInOutDetail.setRecodeId(wmsStorageInOutRecord.getId());
            wmsStorageInOutDetail.setWarehouseCode(warehouse.getCode());
            wmsStorageInOutDetail.setWarehouseId(warehouse.getId());
            wmsStorageInOutDetail.setLocationId(wmsItemStorageDetail.getLocationId());
            wmsStorageInOutDetail.setQuantity(wmsItemStorageDetail.getAllotQuantity());
            wmsStorageInOutDetail.setItemCode(wmsItemStorageDetail.getItemCode());
            wmsStorageInOutDetail.setCreateBy(SecurityUtils.getUserId());
            wmsStorageInOutDetail.setCreateTime(DateUtils.getNowDate());
            wmsStorageInOutDetailMapper.insertWmsStorageInOutDetail(wmsStorageInOutDetail);

            // 入库
            WmsStorageInOutRecord outRecord = new WmsStorageInOutRecord();
            outRecord.setId(IdUtils.fastSimpleUUID());
            outRecord.setItemCode(wmsItemStorageDetail.getItemCode());
            outRecord.setItemName(wmsItemStorageDetail.getItemName());
            outRecord.setUnit(wmsItemStorageDetail.getUnit());
            outRecord.setSpec(wmsItemStorageDetail.getSpec());
            // 调拨前后、单价保持不变，数量一进一出，总数量保持不变
            outRecord.setBeforeRollingPrice(price);
            outRecord.setBeforeRollingTotalPrice(wmsStorageInOutRecord.getAfterRollingTotalPrice());
            outRecord.setBeforeRollingQuantity(wmsStorageInOutRecord.getAfterRollingQuantity());
            outRecord.setOccPrice(price);
            outRecord.setOccQuantity(wmsItemStorageDetail.getAllotQuantity());
            outRecord.setOccTotalPrice(price.multiply(wmsItemStorageDetail.getAllotQuantity()));
            outRecord.setAfterRollingPrice(price);
            outRecord.setAfterRollingTotalPrice(storage.getStoragePrice());
            outRecord.setAfterRollingQuantity(beforeQuantity);
            // 调拨不记录批次物料的期初、结存数据（物料数量、金额没有变动）
            outRecord.setCompanyId(companyId);
            outRecord.setBatchNo(wmsItemStorageDetail.getBatchNo());
            outRecord.setQuantity(wmsItemStorageDetail.getAllotQuantity());
            outRecord.setNoticeId(allocateDto.getReceiveNoticeId());
            outRecord.setCreateBy(SecurityUtils.getUserId());
            outRecord.setType(StorageInOutRecordType.IN.getCode());
            outRecord.setBillType(BillType.IN.getCode());
            outRecord.setReceiveSendType(allocateDto.getReceiveType());
            outRecord.setCreateTime(DateUtils.getNowDate());
            outRecord.setOccTime(allocateDto.getOccTime());
            wmsStorageInOutRecordMapper.insertWmsStorageInOutRecord(outRecord);

            // 记录入库明细
            Warehouse targetWarehouse = warehouseService.selectWarehouseById(wmsItemStorageDetail.getTargetWarehouseId());
            WmsStorageInOutDetail outDetail = new WmsStorageInOutDetail();
            outDetail.setId(IdUtils.fastSimpleUUID());
            outDetail.setRecodeId(outRecord.getId());
            outDetail.setWarehouseId(targetWarehouse.getId());
            outDetail.setWarehouseCode(targetWarehouse.getCode());
            outDetail.setLocationId(wmsItemStorageDetail.getTargetLocationId());
            outDetail.setQuantity(wmsItemStorageDetail.getAllotQuantity());
            outDetail.setItemCode(wmsItemStorageDetail.getItemCode());
            outDetail.setCreateBy(SecurityUtils.getUserId());
            outDetail.setCreateTime(DateUtils.getNowDate());
            wmsStorageInOutDetailMapper.insertWmsStorageInOutDetail(outDetail);

        });
    }


    /**
     * 盘盈 入库记录
     *
     * @param wmsInventoryPlan wms库存计划
     * @param inventoryDetail  库存细节
     * @param itemPriceDto     物品价格dto
     */
    public void supplementStorageRecord(WmsInventoryPlan wmsInventoryPlan, WmsInventoryDetail inventoryDetail, ItemPriceDto itemPriceDto) {

        String companyId = wmsInventoryPlan.getCompanyId();
        // 查询仓库信息
        Warehouse warehouse = warehouseService.selectWarehouseById(inventoryDetail.getWarehouseId());
        Date nowDate = DateUtils.getNowDate();

        BigDecimal beforeRollingPrice = itemPriceDto.getActualPrice();
        BigDecimal afterRollingPrice = itemPriceDto.getAfterRollingPrice();
        BigDecimal storagePrice = itemPriceDto.getStoragePrice();
        BigDecimal beforeRollingQuantity = itemPriceDto.getTotalQuantity();

        // 盘盈  记录入库记录
        BigDecimal quantity = inventoryDetail.getDiffQuantity();
        WmsStorageInOutRecord outRecord = new WmsStorageInOutRecord();
        outRecord.setId(IdUtils.fastSimpleUUID());
        outRecord.setItemCode(inventoryDetail.getItemCode());
        outRecord.setItemName(inventoryDetail.getItemName());
        outRecord.setUnit(inventoryDetail.getUnit());
        outRecord.setSpec(inventoryDetail.getSpec());
        outRecord.setNoticeId(wmsInventoryPlan.getId());
        outRecord.setCreateBy(Constants.SYSTEM_USER_ID);
        outRecord.setType(StorageInOutRecordType.IN.getCode());
        outRecord.setBillType(BillType.INVENTORY.getCode());
        outRecord.setCompanyId(companyId);
        outRecord.setBatchNo(inventoryDetail.getBatchNo());

        outRecord.setQuantity(quantity);
        outRecord.setOccQuantity(quantity);
        outRecord.setOccPrice(BigDecimal.ZERO);
        outRecord.setOccTotalPrice(BigDecimal.ZERO);

        // 盘盈时库存中没有该物料，盘盈时的单价为0
        if (itemPriceDto.isNullAdd()) {
            outRecord.setBeforeRollingPrice(BigDecimal.ZERO);
            outRecord.setBeforeRollingTotalPrice(BigDecimal.ZERO);
            outRecord.setBeforeRollingQuantity(BigDecimal.ZERO);

            outRecord.setAfterRollingPrice(BigDecimal.ZERO);
            outRecord.setAfterRollingQuantity(quantity);
            outRecord.setAfterRollingTotalPrice(BigDecimal.ZERO);

            outRecord.setOpeningQuantity(BigDecimal.ZERO);
            outRecord.setOpeningAmount(BigDecimal.ZERO);
            outRecord.setSurplusQuantity(quantity);
            outRecord.setSurplusAmount(BigDecimal.ZERO);
        } else {
            // 查询物料批次的库存数量
            BigDecimal itemBatchNoQuantity = getItemBatchNoQuantity(inventoryDetail.getItemCode(),
                    inventoryDetail.getBatchNo(), wmsInventoryPlan.getCompanyId());

            outRecord.setBeforeRollingPrice(beforeRollingPrice);
            outRecord.setBeforeRollingTotalPrice(storagePrice);
            outRecord.setBeforeRollingQuantity(beforeRollingQuantity);

            outRecord.setAfterRollingPrice(afterRollingPrice);
            outRecord.setAfterRollingQuantity(beforeRollingQuantity.add(quantity));
            outRecord.setAfterRollingTotalPrice(storagePrice);

            BigDecimal openingQuantity = itemBatchNoQuantity.subtract(quantity);
            outRecord.setOpeningQuantity(openingQuantity);
            outRecord.setOpeningAmount(openingQuantity.multiply(beforeRollingPrice));
            outRecord.setSurplusQuantity(itemBatchNoQuantity);
            outRecord.setSurplusAmount(itemBatchNoQuantity.multiply(afterRollingPrice));
        }

        outRecord.setCreateTime(nowDate);
        outRecord.setOccTime(nowDate);
        wmsStorageInOutRecordMapper.insertWmsStorageInOutRecord(outRecord);

        // 记录入库明细
        WmsStorageInOutDetail outDetail = new WmsStorageInOutDetail();
        outDetail.setId(IdUtils.fastSimpleUUID());
        outDetail.setRecodeId(outRecord.getId());
        outDetail.setWarehouseId(warehouse.getId());
        outDetail.setWarehouseCode(warehouse.getCode());
        outDetail.setLocationId(inventoryDetail.getLocationId());
        outDetail.setQuantity(inventoryDetail.getDiffQuantity());
        outDetail.setItemCode(inventoryDetail.getItemCode());
        outDetail.setCreateBy(Constants.SYSTEM_USER_ID);
        outDetail.setCreateTime(nowDate);
        wmsStorageInOutDetailMapper.insertWmsStorageInOutDetail(outDetail);
    }


    /**
     * 盘亏 出库记录
     *
     * @param wmsInventoryPlan wms库存计划
     * @param inventoryDetail  库存细节
     * @param itemPriceDto     物品价格dto
     */
    public void lossStorageRecord(WmsInventoryPlan wmsInventoryPlan, WmsInventoryDetail inventoryDetail, ItemPriceDto itemPriceDto) {
        String companyId = wmsInventoryPlan.getCompanyId();
        // 查询仓库信息
        Warehouse warehouse = warehouseService.selectWarehouseById(inventoryDetail.getWarehouseId());
        Date nowDate = DateUtils.getNowDate();

        BigDecimal beforeRollingPrice = itemPriceDto.getActualPrice();
        BigDecimal afterRollingPrice = itemPriceDto.getAfterRollingPrice();
        BigDecimal storagePrice = itemPriceDto.getStoragePrice();
        BigDecimal beforeRollingQuantity = itemPriceDto.getTotalQuantity();
        BigDecimal quantity = inventoryDetail.getDiffQuantity().abs();

        // 盘亏 记录出库记录
        WmsStorageInOutRecord wmsStorageInOutRecord = new WmsStorageInOutRecord();
        wmsStorageInOutRecord.setId(IdUtils.fastSimpleUUID());
        wmsStorageInOutRecord.setItemCode(inventoryDetail.getItemCode());
        wmsStorageInOutRecord.setItemName(inventoryDetail.getItemName());
        wmsStorageInOutRecord.setUnit(inventoryDetail.getUnit());
        wmsStorageInOutRecord.setSpec(inventoryDetail.getSpec());
        wmsStorageInOutRecord.setCompanyId(companyId);
        wmsStorageInOutRecord.setBatchNo(inventoryDetail.getBatchNo());
        wmsStorageInOutRecord.setNoticeId(wmsInventoryPlan.getId());
        wmsStorageInOutRecord.setType(StorageInOutRecordType.OUT.getCode());
        wmsStorageInOutRecord.setBillType(BillType.INVENTORY.getCode());
        wmsStorageInOutRecord.setQuantity(quantity);

        // 出库前单价、库存金额、库存数量
        wmsStorageInOutRecord.setBeforeRollingPrice(beforeRollingPrice);
        wmsStorageInOutRecord.setBeforeRollingTotalPrice(storagePrice);
        wmsStorageInOutRecord.setBeforeRollingQuantity(beforeRollingQuantity);

        wmsStorageInOutRecord.setOccQuantity(quantity);
        wmsStorageInOutRecord.setOccTotalPrice(BigDecimal.ZERO);
        wmsStorageInOutRecord.setOccPrice(BigDecimal.ZERO);

        // 查询物料批次的库存数量
        BigDecimal itemBatchNoQuantity = getItemBatchNoQuantity(inventoryDetail.getItemCode(),
                inventoryDetail.getBatchNo(), wmsInventoryPlan.getCompanyId());
        itemBatchNoQuantity = itemBatchNoQuantity == null ? BigDecimal.ZERO : itemBatchNoQuantity;


        // 是否将剩余库存全部盘亏
        if (itemPriceDto.isLossAll()) {
            // 全部盘亏 出库金额就是库存的金额，重新计算出库的单价
            wmsStorageInOutRecord.setOccTotalPrice(storagePrice);
            wmsStorageInOutRecord.setOccPrice(storagePrice.divide(quantity, 6, RoundingMode.HALF_UP));

            wmsStorageInOutRecord.setAfterRollingPrice(BigDecimal.ZERO);
            wmsStorageInOutRecord.setAfterRollingQuantity(BigDecimal.ZERO);
            wmsStorageInOutRecord.setAfterRollingTotalPrice(BigDecimal.ZERO);

            wmsStorageInOutRecord.setOpeningQuantity(quantity);
            wmsStorageInOutRecord.setOpeningAmount(storagePrice);

            wmsStorageInOutRecord.setSurplusQuantity(BigDecimal.ZERO);
            wmsStorageInOutRecord.setSurplusAmount(BigDecimal.ZERO);
        } else {

            wmsStorageInOutRecord.setAfterRollingPrice(afterRollingPrice);
            wmsStorageInOutRecord.setAfterRollingQuantity(beforeRollingQuantity.subtract(quantity));
            wmsStorageInOutRecord.setAfterRollingTotalPrice(storagePrice);

            wmsStorageInOutRecord.setOpeningQuantity(itemBatchNoQuantity.add(quantity));
            wmsStorageInOutRecord.setOpeningAmount(itemBatchNoQuantity.add(quantity).multiply(beforeRollingPrice));

            wmsStorageInOutRecord.setSurplusQuantity(itemBatchNoQuantity);
            wmsStorageInOutRecord.setSurplusAmount(itemBatchNoQuantity.multiply(afterRollingPrice));
        }

        wmsStorageInOutRecord.setCreateBy(Constants.SYSTEM_USER_ID);
        wmsStorageInOutRecord.setCreateTime(nowDate);
        wmsStorageInOutRecord.setOccTime(nowDate);
        wmsStorageInOutRecordMapper.insertWmsStorageInOutRecord(wmsStorageInOutRecord);

        // 出库明细
        WmsStorageInOutDetail wmsStorageInOutDetail = new WmsStorageInOutDetail();
        wmsStorageInOutDetail.setId(IdUtils.fastSimpleUUID());
        wmsStorageInOutDetail.setRecodeId(wmsStorageInOutRecord.getId());
        wmsStorageInOutDetail.setWarehouseCode(warehouse.getCode());
        wmsStorageInOutDetail.setWarehouseId(warehouse.getId());
        wmsStorageInOutDetail.setLocationId(inventoryDetail.getLocationId());
        wmsStorageInOutDetail.setQuantity(inventoryDetail.getDiffQuantity().negate());
        wmsStorageInOutDetail.setItemCode(inventoryDetail.getItemCode());
        wmsStorageInOutDetail.setCreateBy(Constants.SYSTEM_USER_ID);
        wmsStorageInOutDetail.setCreateTime(nowDate);
        wmsStorageInOutDetailMapper.insertWmsStorageInOutDetail(wmsStorageInOutDetail);
    }

}
