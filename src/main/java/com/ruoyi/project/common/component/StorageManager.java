package com.ruoyi.project.common.component;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.*;
import com.ruoyi.common.exception.business.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.entity.InventoryPlanDto;
import com.ruoyi.project.system.domain.ProductInfo;
import com.ruoyi.project.system.domain.StorageUnit;
import com.ruoyi.project.system.service.IProductInfoService;
import com.ruoyi.project.system.service.IStorageUnitService;
import com.ruoyi.project.warehouse.domain.*;
import com.ruoyi.project.warehouse.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存管理
 *
 * @author REM
 * @date 2023/04/08
 */
@Component
public class StorageManager {

    @Autowired
    private IWmsItemStorageService wmsItemStorageService;

    @Autowired
    private IWmsItemStorageDetailService wmsItemStorageDetailService;

    @Autowired
    private IWmsDeliveryNoticeDetailService wmsDeliveryNoticeDetailService;

    @Autowired
    private StorageRecordManager storageRecordManager;

    @Autowired
    private CodeService codeService;

    @Autowired
    private IStorageUnitService storageUnitService;

    @Autowired
    private IProductInfoService productInfoService;

    @Autowired
    private IWmsDisplacementLogService wmsDisplacementLogService;

    @Autowired
    private IWmsReceiveNoticeDetailService wmsReceiveNoticeDetailService;

    @Autowired
    private IWmsDeliveryNoticeService wmsDeliveryNoticeService;

    @Autowired
    private IWmsReceiveNoticeService wmsReceiveNoticeService;


    /**
     * 入库
     *
     * @param receiveNoticeInfo 入库通知单信息 priceInfo 价格信息
     * @return int
     */
    public void storage(ReceiveNoticeInfo receiveNoticeInfo, List<BigDecimal> priceInfo) {

        WmsReceiveItemDetail wmsReceiveItemDetail = receiveNoticeInfo.getWmsReceiveItemDetail();
        WmsReceiveNotice wmsReceiveNotice = receiveNoticeInfo.getWmsReceiveNotice();
        // 查询分入仓库的库存汇总
        WmsItemStorage itemStorage = new WmsItemStorage();
        itemStorage.setWarehouseId(receiveNoticeInfo.getWmsReceiveNoticeDetail().getWarehouseId());
        itemStorage.setItemCode(wmsReceiveItemDetail.getItemCode());
        WmsItemStorage wmsItemStorage = wmsItemStorageService.selectWmsItemStorage(itemStorage);

        BigDecimal quantity;
        // 如果是报废或特采入库，入库数量为不合格数量
        if (ScrapSpecialFlag.SCRAP.getCode().equals(wmsReceiveNotice.getScrapSpecialFlag()) || ScrapSpecialFlag.SPECIAL.getCode().equals(wmsReceiveNotice.getScrapSpecialFlag())) {
            quantity = wmsReceiveItemDetail.getFailQuantity();
        } else {
            quantity = wmsReceiveItemDetail.getPassQuantity();
        }

        if (null == wmsItemStorage) {
            // 如果库存汇总不存在，新增
            wmsItemStorage = new WmsItemStorage();
            wmsItemStorage.setId(IdUtils.fastSimpleUUID());
            wmsItemStorage.setItemCode(wmsReceiveItemDetail.getItemCode());
            wmsItemStorage.setUnit(wmsReceiveItemDetail.getUnit());
            wmsItemStorage.setSpec(receiveNoticeInfo.getWmsReceiveNoticeDetail().getSpec());
            wmsItemStorage.setItemName(wmsReceiveItemDetail.getItemName());
            wmsItemStorage.setQuantity(quantity);
            wmsItemStorage.setWarehouseId(receiveNoticeInfo.getWmsReceiveNoticeDetail().getWarehouseId());
            wmsItemStorage.setCompanyId(receiveNoticeInfo.getWmsReceiveNotice().getCompanyId());
            wmsItemStorage.setCreateBy(SecurityUtils.getUserId());
            wmsItemStorage.setCreateTime(DateUtils.getNowDate());
            wmsItemStorageService.insertWmsItemStorage(wmsItemStorage);
        } else {
            // 如果库存汇总存在，更新数量
            wmsItemStorage.setQuantity(wmsItemStorage.getQuantity().add(quantity));
            wmsItemStorage.setUpdateBy(SecurityUtils.getUserId());
            wmsItemStorage.setUpdateTime(DateUtils.getNowDate());
            wmsItemStorageService.updateWmsItemStorage(wmsItemStorage);
        }

        // 更新该物料的的所有库存汇总的实时单价、总库存金额
        WmsItemStorage storage = new WmsItemStorage();
        storage.setItemCode(wmsReceiveItemDetail.getItemCode());
        storage.setActualPrice(priceInfo.get(0));
        storage.setStoragePrice(priceInfo.get(1));
        storage.setCompanyId(receiveNoticeInfo.getWmsReceiveNotice().getCompanyId());
        wmsItemStorageService.updateWmsItemStorageAllPrice(storage);

        StorageUnit storageUnit = storageUnitService.selectStorageUnitById(receiveNoticeInfo.getWmsReceiveNoticeDetail().getLocationId());

        // 判断库存明细是否存在 存在则更新数量  不存在则新增
        WmsItemStorageDetail selectDetail = new WmsItemStorageDetail();
        selectDetail.setBatchNo(wmsReceiveItemDetail.getBatchNo());
        selectDetail.setItemCode(wmsReceiveItemDetail.getItemCode());
        selectDetail.setRelateType(RelateType.PUTAWAY.getCode());
        selectDetail.setWorkStatus(WorkStatus.WORKING.getCode());
        selectDetail.setRelateId(receiveNoticeInfo.getWorkNoticeId());
        WmsItemStorageDetail wmsItemStorageDetailInfo = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(selectDetail);

        if (null != wmsItemStorageDetailInfo) {
            // 更新库存明细
            wmsItemStorageDetailInfo.setQuantity(wmsItemStorageDetailInfo.getQuantity().add(quantity));
            wmsItemStorageDetailInfo.setUpdateBy(SecurityUtils.getUserId());
            wmsItemStorageDetailInfo.setUpdateTime(DateUtils.getNowDate());
            wmsItemStorageDetailService.updateWmsItemStorageDetail(wmsItemStorageDetailInfo);

        } else {
            // 新增库存明细
            WmsItemStorageDetail wmsItemStorageDetail = new WmsItemStorageDetail();
            wmsItemStorageDetail.setId(IdUtils.fastSimpleUUID());
            wmsItemStorageDetail.setStorageId(wmsItemStorage.getId());
            wmsItemStorageDetail.setBatchNo(wmsReceiveItemDetail.getBatchNo());
            wmsItemStorageDetail.setQuantity(quantity);
            wmsItemStorageDetail.setProductDate(wmsReceiveItemDetail.getProductDate());
            wmsItemStorageDetail.setLocationId(storageUnit.getId());
            wmsItemStorageDetail.setWarehouseId(storageUnit.getWarehouseId());
            wmsItemStorageDetail.setExpireDate(wmsReceiveItemDetail.getExpireTime());
            // 作业状态为作业中
            wmsItemStorageDetail.setWorkStatus(WorkStatus.WORKING.getCode());

            // 如果是采购报废入库，库存状态为残次
            if (ScrapSpecialFlag.SCRAP.getCode().equals(wmsReceiveNotice.getScrapSpecialFlag())) {
                wmsItemStorageDetail.setStorageStatus(StorageStatus.DEFECTIVE.getCode());
            } else if (ScrapSpecialFlag.SPECIAL.getCode().equals(wmsReceiveNotice.getScrapSpecialFlag())) {
                // 如果是特采入库，库存状态为特采
                wmsItemStorageDetail.setStorageStatus(StorageStatus.SPECIAL.getCode());
            } else {
                wmsItemStorageDetail.setStorageStatus(StorageStatus.QUALIFIED.getCode());
            }
            wmsItemStorageDetail.setCreateBy(SecurityUtils.getUserId());
            wmsItemStorageDetail.setCreateTime(DateUtils.getNowDate());
            wmsItemStorageDetail.setRelateId(receiveNoticeInfo.getWorkNoticeId());
            wmsItemStorageDetail.setRelateType(RelateType.PUTAWAY.getCode());
            wmsItemStorageDetailService.insertWmsItemStorageDetail(wmsItemStorageDetail);
        }
    }


    /**
     * 盘点操作库存
     *
     * @param inventoryPlanDto 库存计划dto
     */
    public void inventoryCount(InventoryPlanDto inventoryPlanDto) {
        // 盘点计划
        WmsInventoryPlan inventoryPlan = inventoryPlanDto.getInventoryPlan();
        // 盘点计划明细
        WmsInventoryDetail inventoryDetail = inventoryPlanDto.getInventoryPlanDetail();

        String itemCode = inventoryDetail.getItemCode();
        String companyId = inventoryPlan.getCompanyId();
        WmsItemStorage itemStorage = new WmsItemStorage();
        itemStorage.setCompanyId(companyId);
        itemStorage.setItemCode(itemCode);
        List<WmsItemStorage> wmsItemStorages = wmsItemStorageService.selectWmsItemStorageByCompany(itemStorage);
        ItemPriceDto itemPriceDto = new ItemPriceDto(itemCode);
        // 如果库存汇总不为空, 则计算滚算后单价
        if (CollectionUtils.isNotEmpty(wmsItemStorages)) {
            // 总库存数
            BigDecimal totalQuantity = wmsItemStorages.stream().map(WmsItemStorage::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
            // 当前实时单价
            BigDecimal actualPrice = wmsItemStorages.get(0).getActualPrice();
            // 物料的库存金额
            BigDecimal storagePrice = wmsItemStorages.get(0).getStoragePrice();
            storagePrice = storagePrice == null ? BigDecimal.ZERO : storagePrice;
            // 剩余库存数
            BigDecimal remainQuantity = totalQuantity.add(inventoryDetail.getDiffQuantity());
            BigDecimal rollingPrice = BigDecimal.ZERO;
            if (remainQuantity.compareTo(BigDecimal.ZERO) != 0) {
                // 盘盈/ 部分盘亏,库存金额不变，滚算出最新单价
                rollingPrice = storagePrice.divide(totalQuantity.add(inventoryDetail.getDiffQuantity()), 6, RoundingMode.HALF_UP);
            }
            // 更新该物料的的所有库存汇总的实时单价
            WmsItemStorage storage = new WmsItemStorage();
            storage.setItemCode(itemCode);
            storage.setActualPrice(rollingPrice);
            storage.setCompanyId(companyId);
            wmsItemStorageService.updateWmsItemStoragePrice(storage);

            itemPriceDto = new ItemPriceDto(itemCode, actualPrice, rollingPrice, storagePrice, totalQuantity);
            // 是否亏损所有库存
            itemPriceDto.setLossAll(remainQuantity.compareTo(BigDecimal.ZERO) == 0);
        }

        // 增补库存
        if (inventoryDetail.getIsAdd().equals(Constants.YES)) {
            supplementStorage(inventoryPlan, inventoryDetail, itemPriceDto);
        } else {
            // 盘盈/盘亏
            profitAndLossStorage(inventoryPlan, inventoryDetail, itemPriceDto);
        }

    }


    /**
     * 增补库存
     */
    public void supplementStorage(WmsInventoryPlan inventoryPlan, WmsInventoryDetail inventoryDetail, ItemPriceDto itemPriceDto) {

        // 查询当前仓库库存汇总
        WmsItemStorage itemStorage = new WmsItemStorage();
        itemStorage.setWarehouseId(inventoryDetail.getWarehouseId());
        itemStorage.setItemCode(inventoryDetail.getItemCode());
        WmsItemStorage wmsItemStorage = wmsItemStorageService.selectWmsItemStorage(itemStorage);

        StorageUnit storageUnit = storageUnitService.selectStorageUnitById(inventoryDetail.getLocationId());

        BigDecimal quantity = inventoryDetail.getDiffQuantity();

        if (null == wmsItemStorage) {
            // 如果库存汇总不存在，新增
            wmsItemStorage = new WmsItemStorage();
            wmsItemStorage.setId(IdUtils.fastSimpleUUID());
            wmsItemStorage.setItemCode(inventoryDetail.getItemCode());
            wmsItemStorage.setUnit(inventoryDetail.getUnit());
            wmsItemStorage.setSpec(inventoryDetail.getSpec());
            wmsItemStorage.setItemName(inventoryDetail.getItemName());
            wmsItemStorage.setQuantity(quantity);
            wmsItemStorage.setWarehouseId(inventoryDetail.getWarehouseId());
            wmsItemStorage.setCompanyId(inventoryPlan.getCompanyId());
            wmsItemStorage.setActualPrice(itemPriceDto.getAfterRollingPrice());
            wmsItemStorage.setStoragePrice(itemPriceDto.getStoragePrice());
            wmsItemStorage.setCreateBy(Constants.SYSTEM_USER_ID);
            wmsItemStorage.setCreateTime(DateUtils.getNowDate());
            wmsItemStorageService.insertWmsItemStorage(wmsItemStorage);
        } else {
            // 如果库存汇总存在，更新数量
            wmsItemStorage.setQuantity(wmsItemStorage.getQuantity().add(quantity));
            wmsItemStorage.setUpdateBy(Constants.SYSTEM_USER_ID);
            wmsItemStorage.setUpdateTime(DateUtils.getNowDate());
            wmsItemStorageService.updateWmsItemStorage(wmsItemStorage);
        }
        // 新增库存明细
        WmsItemStorageDetail wmsItemStorageDetail = new WmsItemStorageDetail();
        wmsItemStorageDetail.setId(IdUtils.fastSimpleUUID());
        wmsItemStorageDetail.setStorageId(wmsItemStorage.getId());
        wmsItemStorageDetail.setBatchNo(inventoryDetail.getBatchNo());
        wmsItemStorageDetail.setQuantity(quantity);
        wmsItemStorageDetail.setProductDate(inventoryDetail.getProductDate());
        wmsItemStorageDetail.setLocationId(storageUnit.getId());
        wmsItemStorageDetail.setWarehouseId(storageUnit.getWarehouseId());
        wmsItemStorageDetail.setExpireDate(getExpireDate(inventoryDetail.getProductDate(), inventoryDetail.getBatchNo(), inventoryDetail.getItemCode()));
        wmsItemStorageDetail.setWorkStatus(WorkStatus.NORMAL.getCode());
        wmsItemStorageDetail.setStorageStatus(inventoryDetail.getStorageStatus());
        wmsItemStorageDetail.setCreateBy(Constants.SYSTEM_USER_ID);
        wmsItemStorageDetail.setCreateTime(DateUtils.getNowDate());
        wmsItemStorageDetail.setRelateId("");
        wmsItemStorageDetail.setRelateType(RelateType.NONE.getCode());
        wmsItemStorageDetailService.insertWmsItemStorageDetail(wmsItemStorageDetail);

        // 增补出入库记录
        storageRecordManager.supplementStorageRecord(inventoryPlan, inventoryDetail, itemPriceDto);
    }

    /**
     * 盘盈/盘亏
     */
    public void profitAndLossStorage(WmsInventoryPlan inventoryPlan, WmsInventoryDetail inventoryDetail, ItemPriceDto itemPriceDto) {

        // 查询库存汇总
        WmsItemStorage itemStorage = new WmsItemStorage();
        itemStorage.setWarehouseId(inventoryDetail.getWarehouseId());
        itemStorage.setItemCode(inventoryDetail.getItemCode());
        WmsItemStorage wmsItemStorage = wmsItemStorageService.selectWmsItemStorage(itemStorage);
        Long userId = Constants.SYSTEM_USER_ID;

        // 查询库存明细
        WmsItemStorageDetail wmsItemStorageDetailInfo = wmsItemStorageDetailService.selectWmsItemStorageDetailById(inventoryDetail.getStorageDetailId());

        if (null == wmsItemStorage) {
            throw new BusinessException("库存汇总不存在");
        }

        if (null == wmsItemStorageDetailInfo) {
            throw new BusinessException("库存明细不存在");
        }

        // 盘盈
        if (inventoryDetail.getDiffQuantity().compareTo(BigDecimal.ZERO) > 0) {
            // 更新库存汇总数量
            wmsItemStorage.setQuantity(wmsItemStorage.getQuantity().add(inventoryDetail.getDiffQuantity()));
            wmsItemStorage.setUpdateBy(userId);
            wmsItemStorage.setUpdateTime(DateUtils.getNowDate());
            wmsItemStorageService.updateWmsItemStorage(wmsItemStorage);

            // 更新库存明细数量
            wmsItemStorageDetailInfo.setQuantity(wmsItemStorageDetailInfo.getQuantity().add(inventoryDetail.getDiffQuantity()));
            wmsItemStorageDetailInfo.setUpdateBy(userId);
            wmsItemStorageDetailInfo.setUpdateTime(DateUtils.getNowDate());
            wmsItemStorageDetailService.updateWmsItemStorageDetail(wmsItemStorageDetailInfo);

            // 盘盈入库记录
            storageRecordManager.supplementStorageRecord(inventoryPlan, inventoryDetail, itemPriceDto);

        } else {
            // 盘亏
            if (wmsItemStorage.getQuantity().add(inventoryDetail.getDiffQuantity()).compareTo(BigDecimal.ZERO) == 0) {
                // 如果盘亏后数量为0，则删除库存汇总
                wmsItemStorageService.deleteWmsItemStorageById(wmsItemStorage.getId());
                // 删除库存明细
                wmsItemStorageDetailService.deleteWmsItemStorageDetailById(wmsItemStorageDetailInfo.getId());

            } else {
                // 更新库存汇总数量
                wmsItemStorage.setQuantity(wmsItemStorage.getQuantity().add(inventoryDetail.getDiffQuantity()));
                wmsItemStorage.setUpdateBy(userId);
                wmsItemStorage.setUpdateTime(DateUtils.getNowDate());
                wmsItemStorageService.updateWmsItemStorage(wmsItemStorage);

                // 判断是否亏损当前货位的所有库存
                if (wmsItemStorageDetailInfo.getQuantity().add(inventoryDetail.getDiffQuantity()).compareTo(BigDecimal.ZERO) == 0) {
                    // 如果亏损当前货位的所有库存，则删除库存明细
                    wmsItemStorageDetailService.deleteWmsItemStorageDetailById(wmsItemStorageDetailInfo.getId());
                } else {
                    // 更新库存明细数量
                    wmsItemStorageDetailInfo.setQuantity(wmsItemStorageDetailInfo.getQuantity().add(inventoryDetail.getDiffQuantity()));
                    wmsItemStorageDetailInfo.setUpdateBy(userId);
                    wmsItemStorageDetailInfo.setUpdateTime(DateUtils.getNowDate());
                    wmsItemStorageDetailService.updateWmsItemStorageDetail(wmsItemStorageDetailInfo);
                }
            }

            // 盘亏出库记录
            storageRecordManager.lossStorageRecord(inventoryPlan, inventoryDetail, itemPriceDto);
        }

    }

    /**
     * 更新库存信息
     *
     * @param workNoticeDetail   工作中注意细节
     * @param wmsWorkNoticeAllot wms注意分配工作
     * @throws Exception 异常
     */
    public void updateStorageInfo(WmsWorkNoticeDetail workNoticeDetail, WmsWorkNoticeAllot wmsWorkNoticeAllot) throws Exception {

        // 总数量
        BigDecimal noticeDetailQuantity = workNoticeDetail.getQuantity();
        //  上架数量
        BigDecimal quantity = wmsWorkNoticeAllot.getQuantity();

        // 来源仓库
        String sourceWarehouseId = workNoticeDetail.getSourceWarehouseId();
        // 目标仓库
        String targetWarehouseId = wmsWorkNoticeAllot.getWarehouseId();

        // 查询来源库存汇总
        WmsItemStorage itemStorage = new WmsItemStorage();
        itemStorage.setWarehouseId(sourceWarehouseId);
        itemStorage.setItemCode(wmsWorkNoticeAllot.getItemCode());
        WmsItemStorage sourceStorage = wmsItemStorageService.selectWmsItemStorage(itemStorage);

        // 查询目标库存汇总
        WmsItemStorage targetStorage = new WmsItemStorage();
        targetStorage.setWarehouseId(targetWarehouseId);
        targetStorage.setItemCode(wmsWorkNoticeAllot.getItemCode());
        WmsItemStorage targetItemStorage = wmsItemStorageService.selectWmsItemStorage(targetStorage);

        String storageId = sourceStorage.getId();
        // 如果来源仓库和目标仓库不相同 则需要修改库存汇总信息
        if (!sourceWarehouseId.equals(targetWarehouseId)) {
            // 不同 则判断目标库存汇总是否存在
            if (null == targetItemStorage) {
                storageId = IdUtils.fastSimpleUUID();
                // 不存在 则新增
                targetItemStorage = new WmsItemStorage();
                targetItemStorage.setId(storageId);
                targetItemStorage.setWarehouseId(targetWarehouseId);
                targetItemStorage.setItemCode(sourceStorage.getItemCode());
                targetItemStorage.setItemName(sourceStorage.getItemName());
                targetItemStorage.setCompanyId(sourceStorage.getCompanyId());
                targetItemStorage.setUnit(sourceStorage.getUnit());
                targetItemStorage.setSpec(sourceStorage.getSpec());
                targetItemStorage.setQuantity(quantity);
                targetItemStorage.setActualPrice(sourceStorage.getActualPrice());
                targetItemStorage.setCreateBy(SecurityUtils.getUserId());
                targetItemStorage.setCreateTime(DateUtils.getNowDate());
                wmsItemStorageService.insertWmsItemStorage(targetItemStorage);
            } else {
                storageId = targetItemStorage.getId();
                // 存在 则更新数量
                targetItemStorage.setQuantity(targetItemStorage.getQuantity().add(quantity));
                targetItemStorage.setUpdateBy(SecurityUtils.getUserId());
                targetItemStorage.setUpdateTime(DateUtils.getNowDate());
                wmsItemStorageService.updateWmsItemStorage(targetItemStorage);
            }

            // 更新来源库存汇总
            sourceStorage.setQuantity(sourceStorage.getQuantity().subtract(quantity));
            sourceStorage.setUpdateBy(SecurityUtils.getUserId());
            sourceStorage.setUpdateTime(DateUtils.getNowDate());
            wmsItemStorageService.updateWmsItemStorage(sourceStorage);

        }


        // 查询库存明细
        WmsItemStorageDetail selectDetail = new WmsItemStorageDetail();
        selectDetail.setBatchNo(wmsWorkNoticeAllot.getBatchNo());
        selectDetail.setItemCode(workNoticeDetail.getItemCode());
        selectDetail.setRelateType(RelateType.PUTAWAY.getCode());
        selectDetail.setWorkStatus(WorkStatus.WORKING.getCode());
        selectDetail.setRelateId(workNoticeDetail.getWorkNoticeId());
        WmsItemStorageDetail sourceStorageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(selectDetail);
        if (null == sourceStorageDetail) {
            throw new Exception("库存明细不存在");
        }

        // 查询目标库位是否存在相同批次的物料
        selectDetail.setRelateType(RelateType.NONE.getCode());
        selectDetail.setWorkStatus(WorkStatus.NORMAL.getCode());
        selectDetail.setStorageStatus(sourceStorageDetail.getStorageStatus());
        selectDetail.setRelateId("");
        selectDetail.setItemCode(workNoticeDetail.getItemCode());
        selectDetail.setLocationId(wmsWorkNoticeAllot.getTargetLocationId());
        WmsItemStorageDetail targetItemStorageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(selectDetail);

        // 上架数量等于库存数量 直接修改库存状态
        if (sourceStorageDetail.getQuantity().compareTo(quantity) == 0) {
            // 判断目标库位是否存在相同批次的物料
            if (null == targetItemStorageDetail) {
                // 不存在 则修改库存明细
                sourceStorageDetail.setStorageId(storageId);
                sourceStorageDetail.setWorkStatus(WorkStatus.NORMAL.getCode());
                sourceStorageDetail.setRelateId("");
                sourceStorageDetail.setRelateType(RelateType.NONE.getCode());
                sourceStorageDetail.setLocationId(wmsWorkNoticeAllot.getTargetLocationId());
                sourceStorageDetail.setUpdateBy(SecurityUtils.getUserId());
                sourceStorageDetail.setUpdateTime(DateUtils.getNowDate());
                wmsItemStorageDetailService.updateWmsItemStorageDetail(sourceStorageDetail);
            } else {
                // 更新库存明细
                targetItemStorageDetail.setStorageId(storageId);
                targetItemStorageDetail.setQuantity(targetItemStorageDetail.getQuantity().add(quantity));
                targetItemStorageDetail.setUpdateBy(SecurityUtils.getUserId());
                targetItemStorageDetail.setUpdateTime(DateUtils.getNowDate());
                wmsItemStorageDetailService.updateWmsItemStorageDetail(targetItemStorageDetail);

                // 删除原库存明细
                wmsItemStorageDetailService.deleteWmsItemStorageDetailById(sourceStorageDetail.getId());
            }
            // 删除原库存汇总
            if (sourceStorage.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                wmsItemStorageService.deleteWmsItemStorageById(sourceStorage.getId());
            }
        }

        // 上架数量小于库存数量 需要拆分库存明细
        if (sourceStorageDetail.getQuantity().compareTo(quantity) > 0) {
            // 判断目标库位是否存在相同批次的物料
            if (null == targetItemStorageDetail) {
                // 新增库存明细
                WmsItemStorageDetail wmsItemStorageDetail = new WmsItemStorageDetail();
                wmsItemStorageDetail.setId(IdUtils.fastSimpleUUID());
                wmsItemStorageDetail.setStorageId(storageId);
                wmsItemStorageDetail.setBatchNo(wmsWorkNoticeAllot.getBatchNo());
                wmsItemStorageDetail.setQuantity(quantity);
                wmsItemStorageDetail.setProductDate(sourceStorageDetail.getProductDate());
                wmsItemStorageDetail.setLocationId(wmsWorkNoticeAllot.getTargetLocationId());
                wmsItemStorageDetail.setWarehouseId(sourceStorageDetail.getWarehouseId());
                wmsItemStorageDetail.setExpireDate(sourceStorageDetail.getExpireDate());
                wmsItemStorageDetail.setWorkStatus(WorkStatus.NORMAL.getCode());
                wmsItemStorageDetail.setStorageStatus(sourceStorageDetail.getStorageStatus());
                wmsItemStorageDetail.setCreateBy(SecurityUtils.getUserId());
                wmsItemStorageDetail.setCreateTime(DateUtils.getNowDate());
                wmsItemStorageDetail.setRelateId("");
                wmsItemStorageDetail.setRelateType(RelateType.NONE.getCode());
                wmsItemStorageDetailService.insertWmsItemStorageDetail(wmsItemStorageDetail);

            } else {
                // 更目标新库存明细
                targetItemStorageDetail.setStorageId(storageId);
                targetItemStorageDetail.setQuantity(targetItemStorageDetail.getQuantity().add(quantity));
                targetItemStorageDetail.setUpdateBy(SecurityUtils.getUserId());
                targetItemStorageDetail.setUpdateTime(DateUtils.getNowDate());
                wmsItemStorageDetailService.updateWmsItemStorageDetail(targetItemStorageDetail);
            }

            // 更新原库存明细
            WmsItemStorageDetail itemStorageDetail = new WmsItemStorageDetail();
            itemStorageDetail.setId(sourceStorageDetail.getId());
            itemStorageDetail.setQuantity(sourceStorageDetail.getQuantity().subtract(quantity));
            itemStorageDetail.setUpdateBy(SecurityUtils.getUserId());
            itemStorageDetail.setUpdateTime(DateUtils.getNowDate());
            wmsItemStorageDetailService.updateWmsItemStorageDetail(itemStorageDetail);
        }

    }


    /**
     * 出库
     * <p>
     * 将库存中关联编号是该出库通知ID、关联状态状态为‘代发货’、作业状态为‘作业中’的库存数据，
     * 记录在出入库记录表中，记录完成后将这些库存删除，出库通知状态变为‘已完成’，将各个物料的发货数量写入出库通知明细中
     *
     * @param deliveryNoticeId 交货通知id
     * @return int
     */
    @Transactional(rollbackFor = Exception.class)
    public void outBound(String deliveryNoticeId) {

        WmsDeliveryNotice wmsDeliveryNotice = wmsDeliveryNoticeService.selectWmsDeliveryNoticeById(deliveryNoticeId);

        // 查询待发货的库存信息
        WmsItemStorageDetail selectDetail = new WmsItemStorageDetail();
        selectDetail.setRelateType(RelateType.DELIVERY.getCode());
        selectDetail.setWorkStatus(WorkStatus.WORKING.getCode());
        selectDetail.setRelateId(deliveryNoticeId);
        // 现同物料、相同批号挨在一起
        List<WmsItemStorageDetail> wmsItemStorageDetails = wmsItemStorageDetailService.selectByDeliveryNoticeId(selectDetail);

        // 删除库存明细
        wmsItemStorageDetails.forEach(wmsItemStorageDetail -> {
            // 保存出入库记录
            storageRecordManager.saveStorageOutRecord(wmsItemStorageDetail, wmsDeliveryNotice);
            wmsItemStorageDetailService.deleteWmsItemStorageDetailById(wmsItemStorageDetail.getId());
            // 更新库存汇总
            WmsItemStorage storage = wmsItemStorageService.selectWmsItemStorageById(wmsItemStorageDetail.getStorageId());

            WmsItemStorage wmsItemStorage = new WmsItemStorage();
            wmsItemStorage.setId(storage.getId());
            wmsItemStorage.setQuantity(storage.getQuantity().subtract(wmsItemStorageDetail.getQuantity()));
            if (wmsItemStorage.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                wmsItemStorageService.deleteWmsItemStorageById(wmsItemStorage.getId());
            } else {
                wmsItemStorage.setUpdateBy(SecurityUtils.getUserId());
                wmsItemStorage.setUpdateTime(DateUtils.getNowDate());
                wmsItemStorageService.updateWmsItemStorage(wmsItemStorage);
            }

            // 库存金额扣减掉出库金额，更新库存金额
            BigDecimal actualPrice = storage.getActualPrice() == null ? BigDecimal.ZERO : storage.getActualPrice();
            BigDecimal outPrice = actualPrice.multiply(wmsItemStorageDetail.getQuantity());
            BigDecimal storageAmount = storage.getStoragePrice().subtract(outPrice);
            storage.setStoragePrice(storageAmount);
            wmsItemStorageService.updateWmsItemStorageAllPrice(storage);
        });
    }


    /**
     * 分配库存
     *
     * @param wmsItemStorageDetail wms项存储细节
     * @return int
     */
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult allotItemStorage(WmsItemStorageDetail wmsItemStorageDetail) {

        // 出库通知ID
        String deliveryNoticeId = wmsItemStorageDetail.getRelateId();
        BigDecimal quantity = wmsItemStorageDetail.getQuantity();
        BigDecimal allotQuantity = wmsItemStorageDetail.getAllotQuantity();
        if (allotQuantity.compareTo(quantity) > 0) {
            return AjaxResult.error("分配数量不能大于库存数量");
        }
        WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail = wmsDeliveryNoticeDetailService.selectWmsDeliveryNoticeDetailById(wmsItemStorageDetail.getDeliveryNoticeDetailId());
        // 校验库存单位和通知单位是否一致
        if (checkUnit(wmsDeliveryNoticeDetail)) {
            return AjaxResult.error("库存单位和通知单位不一致");
        }

        // 查询是否分配过
        WmsItemStorageDetail selectDetail = new WmsItemStorageDetail();
        selectDetail.setItemCode(wmsItemStorageDetail.getItemCode());
        selectDetail.setBatchNo(wmsItemStorageDetail.getBatchNo());
        selectDetail.setRelateType(RelateType.ALLOCATION.getCode());
        selectDetail.setWorkStatus(WorkStatus.WORKING.getCode());
        selectDetail.setRelateId(wmsItemStorageDetail.getRelateId());
        selectDetail.setLocationId(wmsItemStorageDetail.getLocationId());
        WmsItemStorageDetail wmsItemStorageDetailInfo = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(selectDetail);

        if (allotQuantity.compareTo(quantity) == 0) {
            if (null != wmsItemStorageDetailInfo) {
                // 分配过 更新分配后库存详情的数量
                wmsItemStorageDetailInfo.setQuantity(allotQuantity.add(wmsItemStorageDetailInfo.getQuantity()));
                wmsItemStorageDetailService.updateWmsItemStorageDetail(wmsItemStorageDetailInfo);

                // 删除原有库存详情
                wmsItemStorageDetailService.deleteWmsItemStorageDetailById(wmsItemStorageDetail.getId());

            } else {
                // 没有分配过 直接修改作业状态为作业中，关联类型为分配中
                wmsItemStorageDetail.setWorkStatus(WorkStatus.WORKING.getCode());
                wmsItemStorageDetail.setRelateType(RelateType.ALLOCATION.getCode());
                wmsItemStorageDetail.setRelateId(deliveryNoticeId);
                wmsItemStorageDetailService.updateWmsItemStorageDetail(wmsItemStorageDetail);
            }
        }
        if (allotQuantity.compareTo(quantity) < 0) {
            // 分配数量小于库存数量，修改库存数量，新增一条库存详情
            if (null == wmsItemStorageDetailInfo) {
                // 新增一条库存详情
                WmsItemStorageDetail addItemStorageDetailInfo = new WmsItemStorageDetail();
                addItemStorageDetailInfo.setId(IdUtils.fastSimpleUUID());
                addItemStorageDetailInfo.setStorageId(wmsItemStorageDetail.getStorageId());
                addItemStorageDetailInfo.setBatchNo(wmsItemStorageDetail.getBatchNo());
                addItemStorageDetailInfo.setQuantity(allotQuantity);
                addItemStorageDetailInfo.setLocationId(wmsItemStorageDetail.getLocationId());
                addItemStorageDetailInfo.setStorageStatus(wmsItemStorageDetail.getStorageStatus());
                addItemStorageDetailInfo.setWorkStatus(WorkStatus.WORKING.getCode());
                addItemStorageDetailInfo.setProductDate(wmsItemStorageDetail.getProductDate());
                addItemStorageDetailInfo.setExpireDate(wmsItemStorageDetail.getExpireDate());
                addItemStorageDetailInfo.setThermosphere(wmsItemStorageDetail.getThermosphere());
                addItemStorageDetailInfo.setRelateType(RelateType.ALLOCATION.getCode());
                addItemStorageDetailInfo.setRelateId(deliveryNoticeId);
                addItemStorageDetailInfo.setCreateBy(SecurityUtils.getUserId());
                addItemStorageDetailInfo.setCreateTime(DateUtils.getNowDate());
                addItemStorageDetailInfo.setItemCode(wmsItemStorageDetail.getItemCode());
                addItemStorageDetailInfo.setItemName(wmsItemStorageDetail.getItemName());
                addItemStorageDetailInfo.setActualPrice(wmsItemStorageDetail.getActualPrice());
                wmsItemStorageDetailService.insertWmsItemStorageDetail(addItemStorageDetailInfo);
            } else {
                // 更新库存详情
                wmsItemStorageDetailInfo.setQuantity(wmsItemStorageDetailInfo.getQuantity().add(allotQuantity));
                wmsItemStorageDetailService.updateWmsItemStorageDetail(wmsItemStorageDetailInfo);
            }

            // 扣减原库存数量
            WmsItemStorageDetail itemStorageDetail = new WmsItemStorageDetail();
            itemStorageDetail.setId(wmsItemStorageDetail.getId());
            itemStorageDetail.setQuantity(quantity.subtract(allotQuantity));
            wmsItemStorageDetailService.updateWmsItemStorageDetail(itemStorageDetail);

        }
        // 更新出库通知详情数量
        wmsDeliveryNoticeDetail.setAllotQuantity(wmsDeliveryNoticeDetail.getAllotQuantity().add(allotQuantity));
        wmsDeliveryNoticeDetailService.updateWmsDeliveryNoticeDetail(wmsDeliveryNoticeDetail);

        return AjaxResult.success();
    }


    /**
     * 根据出库通知详情取消分配库存
     *
     * @param noticeDetail 注意到细节
     * @throws Exception 异常
     */
    public void cancelAllotByDeliveryNoticeDetail(WmsDeliveryNoticeDetail noticeDetail) throws Exception {
        // 查询出库通知详情分配的库存
        WmsItemStorageDetail itemStorageDetail = new WmsItemStorageDetail();
        itemStorageDetail.setRelateId(noticeDetail.getDeliveryNoticeId());
        itemStorageDetail.setItemCode(noticeDetail.getItemCode());
        itemStorageDetail.setRelateType(RelateType.ALLOCATION.getCode());
        itemStorageDetail.setWorkStatus(WorkStatus.WORKING.getCode());
        List<WmsItemStorageDetail> itemStorageDetailList = wmsItemStorageDetailService.selectWmsItemStorageDetailList(itemStorageDetail);
        if (!itemStorageDetailList.isEmpty()) {
            for (WmsItemStorageDetail storageDetail : itemStorageDetailList) {
                // 取消分配库存
                cancelAllot(new ItemStorageDetailDto(storageDetail));
            }
        }
    }


    /**
     * 取消分配
     *
     * @param itemStorageDetailDto 项目存储细节dto
     * @throws Exception 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelAllot(ItemStorageDetailDto itemStorageDetailDto) throws Exception {

        // 查询当前需要取消分配的库存
        WmsItemStorageDetail currentStorage = new WmsItemStorageDetail();
        currentStorage.setItemCode(itemStorageDetailDto.getItemCode());
        currentStorage.setBatchNo(itemStorageDetailDto.getBatchNo());
        currentStorage.setRelateType(itemStorageDetailDto.getRelateType());
        currentStorage.setRelateId(itemStorageDetailDto.getRelateId());
        currentStorage.setLocationId(itemStorageDetailDto.getLocationId());
        currentStorage.setWorkStatus(WorkStatus.WORKING.getCode());
        WmsItemStorageDetail currentItemStorageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(currentStorage);
        if (null == currentItemStorageDetail) {
            throw new BusinessException("当前库存信息不存在");
        }

        // 查询是否存在相同批次的库存
        WmsItemStorageDetail sourceStorage = new WmsItemStorageDetail();
        sourceStorage.setItemCode(itemStorageDetailDto.getItemCode());
        sourceStorage.setBatchNo(itemStorageDetailDto.getBatchNo());
        sourceStorage.setRelateType(RelateType.NONE.getCode());
        sourceStorage.setWorkStatus(WorkStatus.NORMAL.getCode());
        sourceStorage.setLocationId(currentStorage.getLocationId());
        WmsItemStorageDetail sourceItemStorageDetailInfo = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(sourceStorage);

        if (null != sourceItemStorageDetailInfo) {
            // 存在相同批次的库存 更新库存数量
            sourceItemStorageDetailInfo.setQuantity(sourceItemStorageDetailInfo.getQuantity().add(currentItemStorageDetail.getQuantity()));
            sourceItemStorageDetailInfo.setUpdateBy(SecurityUtils.getUserId());
            sourceItemStorageDetailInfo.setUpdateTime(DateUtils.getNowDate());
            wmsItemStorageDetailService.updateWmsItemStorageDetail(sourceItemStorageDetailInfo);

            // 删除分配的库存
            wmsItemStorageDetailService.deleteWmsItemStorageDetailById(currentItemStorageDetail.getId());
        } else {
            // 不存在未分配的库存 更新库存数量 作业状态为正常 关联类型为无
            WmsItemStorageDetail itemStorageDetail = new WmsItemStorageDetail();
            itemStorageDetail.setId(currentItemStorageDetail.getId());
            itemStorageDetail.setWorkStatus(WorkStatus.NORMAL.getCode());
            itemStorageDetail.setRelateType(RelateType.NONE.getCode());
            itemStorageDetail.setRelateId("");
            itemStorageDetail.setUpdateBy(SecurityUtils.getUserId());
            itemStorageDetail.setUpdateTime(DateUtils.getNowDate());
            wmsItemStorageDetailService.updateWmsItemStorageDetail(itemStorageDetail);
        }
    }


    /**
     * 退回出库通知中的 分配中 拣货完的物料
     *
     * @param list 列表
     */
    public void batchCancelAllotByDeliveryNotice(List<WmsDeliveryNoticeDetail> list) throws Exception {
        if (!list.isEmpty()) {
            for (WmsDeliveryNoticeDetail noticeDetail : list) {
                // 查询出库通知详情分配的库存
                WmsItemStorageDetail itemStorageDetail = new WmsItemStorageDetail();
                itemStorageDetail.setRelateId(noticeDetail.getDeliveryNoticeId());
                itemStorageDetail.setItemCode(noticeDetail.getItemCode());
                // 分配中 待发运的库存
                itemStorageDetail.setRelateTypeList(new Integer[]{RelateType.ALLOCATION.getCode(), RelateType.DELIVERY.getCode()});
                itemStorageDetail.setWorkStatus(WorkStatus.WORKING.getCode());
                List<WmsItemStorageDetail> itemStorageDetailList = wmsItemStorageDetailService.selectWmsItemStorageDetailList(itemStorageDetail);
                if (!itemStorageDetailList.isEmpty()) {
                    for (WmsItemStorageDetail storageDetail : itemStorageDetailList) {
                        // 取消分配库存
                        cancelAllot(new ItemStorageDetailDto(storageDetail));
                    }
                }
            }
        }
    }

    /**
     * 退回拣货作业中的物料
     *
     * @param list 列表
     */
    public void batchCancelAllotByWorkNotice(List<WmsPickingWorkNoticeDetail> list) throws Exception {
        if (!list.isEmpty()) {
            for (WmsPickingWorkNoticeDetail noticeDetail : list) {
                if (noticeDetail.getAllotQuantity().compareTo(BigDecimal.ZERO) > 0) {
                    // 查询出库通知详情分配的库存
                    WmsItemStorageDetail itemStorageDetail = new WmsItemStorageDetail();
                    itemStorageDetail.setRelateId(noticeDetail.getWorkNoticeId());
                    itemStorageDetail.setItemCode(noticeDetail.getItemCode());
                    itemStorageDetail.setRelateType(RelateType.PICKING.getCode());
                    itemStorageDetail.setWorkStatus(WorkStatus.WORKING.getCode());
                    List<WmsItemStorageDetail> itemStorageDetailList = wmsItemStorageDetailService.selectWmsItemStorageDetailList(itemStorageDetail);
                    if (!itemStorageDetailList.isEmpty()) {
                        for (WmsItemStorageDetail storageDetail : itemStorageDetailList) {
                            // 取消分配库存
                            cancelAllot(new ItemStorageDetailDto(storageDetail));
                        }
                    }
                }
            }
        }
    }


    /**
     * 移位
     *
     * @param wmsItemStorageDetails wms项存储细节
     * @return {@link AjaxResult}
     */
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult displacement(List<WmsItemStorageDetail> wmsItemStorageDetails) {

        for (WmsItemStorageDetail itemStorageDetail : wmsItemStorageDetails) {
            WmsItemStorageDetail storageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailById(itemStorageDetail.getId());
            String targetLocationId = itemStorageDetail.getTargetLocationId();
            String sourceLocationId = itemStorageDetail.getLocationId();
            // 原库位库存数量
            BigDecimal storageDetailQuantity = storageDetail.getQuantity();
            // 移位数量
            BigDecimal allotQuantity = itemStorageDetail.getAllotQuantity();
            if (allotQuantity.compareTo(storageDetailQuantity) > 0) {
                return AjaxResult.error("移位数量不能大于库存数量");
            }

            if (!targetLocationId.equals(sourceLocationId)) {
                // 移位数量等于库存数量 查询目标库位是否存在相同批次的物料
                WmsItemStorageDetail targetItemStorageDetail = new WmsItemStorageDetail();
                targetItemStorageDetail.setLocationId(targetLocationId);
                targetItemStorageDetail.setItemCode(itemStorageDetail.getItemCode());
                targetItemStorageDetail.setBatchNo(itemStorageDetail.getBatchNo());
                targetItemStorageDetail.setWorkStatus(storageDetail.getWorkStatus());
                targetItemStorageDetail.setRelateType(storageDetail.getRelateType());
                targetItemStorageDetail.setStorageStatus(storageDetail.getStorageStatus());
                WmsItemStorageDetail targetItemStorage = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(targetItemStorageDetail);

                // 是否全部移位
                boolean isAll = allotQuantity.compareTo(storageDetail.getQuantity()) == 0;
                // 目标库位是否存在相同批次的物料
                boolean isHaveTarget = null != targetItemStorage;

                // 全部移位 且 目标库位存在相同批次的物料
                if (isAll && isHaveTarget) {
                    // 更新目标库位货物的数量
                    targetItemStorage.setQuantity(targetItemStorage.getQuantity().add(allotQuantity));
                    targetItemStorage.setUpdateBy(SecurityUtils.getUserId());
                    targetItemStorage.setUpdateTime(DateUtils.getNowDate());
                    wmsItemStorageDetailService.updateWmsItemStorageDetail(targetItemStorage);

                    // 删除原库存
                    wmsItemStorageDetailService.deleteWmsItemStorageDetailById(storageDetail.getId());
                }

                // 全部移位 且 目标库位不存在相同批次的物料
                if (isAll && !isHaveTarget) {
                    // 更新原库位到目标库位
                    storageDetail.setLocationId(targetLocationId);
                    storageDetail.setUpdateBy(SecurityUtils.getUserId());
                    storageDetail.setUpdateTime(DateUtils.getNowDate());
                    wmsItemStorageDetailService.updateWmsItemStorageDetail(storageDetail);
                }

                // 部分移位 且 目标库位存在相同批次的物料
                if (!isAll && isHaveTarget) {
                    // 更新目标库位货物的数量
                    targetItemStorage.setQuantity(targetItemStorage.getQuantity().add(allotQuantity));
                    targetItemStorage.setUpdateBy(SecurityUtils.getUserId());
                    targetItemStorage.setUpdateTime(DateUtils.getNowDate());
                    wmsItemStorageDetailService.updateWmsItemStorageDetail(targetItemStorage);

                    // 更新原库存数量
                    storageDetail.setQuantity(storageDetail.getQuantity().subtract(allotQuantity));
                    storageDetail.setUpdateBy(SecurityUtils.getUserId());
                    storageDetail.setUpdateTime(DateUtils.getNowDate());
                    wmsItemStorageDetailService.updateWmsItemStorageDetail(storageDetail);
                }

                // 部分移位 且 目标库位不存在相同批次的物料
                if (!isAll && !isHaveTarget) {
                    // 更新原库存数量
                    storageDetail.setQuantity(storageDetailQuantity.subtract(allotQuantity));
                    storageDetail.setUpdateBy(SecurityUtils.getUserId());
                    storageDetail.setUpdateTime(DateUtils.getNowDate());
                    wmsItemStorageDetailService.updateWmsItemStorageDetail(storageDetail);

                    // 新增库存
                    WmsItemStorageDetail newStorageDetail = new WmsItemStorageDetail();
                    newStorageDetail.setId(IdUtils.fastSimpleUUID());
                    newStorageDetail.setStorageId(storageDetail.getStorageId());
                    newStorageDetail.setLocationId(targetLocationId);
                    newStorageDetail.setItemCode(storageDetail.getItemCode());
                    newStorageDetail.setBatchNo(storageDetail.getBatchNo());
                    newStorageDetail.setQuantity(allotQuantity);
                    newStorageDetail.setProductDate(storageDetail.getProductDate());
                    newStorageDetail.setExpireDate(storageDetail.getExpireDate());
                    newStorageDetail.setRelateType(RelateType.NONE.getCode());
                    newStorageDetail.setWorkStatus(WorkStatus.NORMAL.getCode());
                    newStorageDetail.setStorageStatus(StorageStatus.QUALIFIED.getCode());
                    newStorageDetail.setCreateBy(SecurityUtils.getUserId());
                    newStorageDetail.setCreateTime(DateUtils.getNowDate());
                    wmsItemStorageDetailService.insertWmsItemStorageDetail(newStorageDetail);
                }


                // 记录日志
                WmsItemStorage wmsItemStorage = wmsItemStorageService.selectWmsItemStorageById(storageDetail.getStorageId());
                WmsDisplacementLog wmsDisplacementLog = new WmsDisplacementLog();
                wmsDisplacementLog.setBatchNo(itemStorageDetail.getBatchNo());
                wmsDisplacementLog.setCompanyId(wmsItemStorage.getCompanyId());
                wmsDisplacementLog.setQuantity(allotQuantity);
                wmsDisplacementLog.setOriginalLocation(sourceLocationId);
                wmsDisplacementLog.setTargetLocation(targetLocationId);
                wmsDisplacementLog.setItemName(itemStorageDetail.getItemName());
                wmsDisplacementLog.setItemCode(itemStorageDetail.getItemCode());
                wmsDisplacementLog.setUnit(itemStorageDetail.getUnit());
                wmsDisplacementLog.setSpec(itemStorageDetail.getSpec());
                wmsDisplacementLog.setWarehouseId(wmsItemStorage.getWarehouseId());
                wmsDisplacementLogService.insertWmsDisplacementLog(wmsDisplacementLog);
            }
        }
        return AjaxResult.success();
    }

    /**
     * 调拨
     *
     * @param allocateDto 调拨对象
     * @return {@link AjaxResult}
     */
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult allocate(AllocateDto allocateDto) {

        AllocateDto dto = new AllocateDto();
        String deliveryType = allocateDto.getDeliveryType();
        String receiveType = allocateDto.getReceiveType();
        List<WmsItemStorageDetail> wmsItemStorageDetails = allocateDto.getItemStorageDetailList();
        if (CollectionUtils.isEmpty(wmsItemStorageDetails)) {
            return AjaxResult.error("调拨失败，调拨物料不能为空");
        }

        String companyId = ServletUtils.getCompanyId();
        String receiveNoticeId = codeService.generateReceiveNoticeCode();
        String deliveryNoticeId = codeService.generateDeliveryNoticeCode();
        dto.setReceiveNoticeId(receiveNoticeId);
        dto.setDeliveryNoticeId(deliveryNoticeId);
        dto.setOccTime(allocateDto.getOccTime());
        dto.setItemStorageDetailList(wmsItemStorageDetails);
        dto.setDeliveryType(deliveryType);
        dto.setReceiveType(receiveType);
        // 生成入库通知
        WmsReceiveNotice wmsReceiveNotice = new WmsReceiveNotice();
        wmsReceiveNotice.setId(receiveNoticeId);
        wmsReceiveNotice.setCompanyId(companyId);
        wmsReceiveNotice.setReceiveSendType(receiveType);
        wmsReceiveNotice.setStatus(ReceiveNoticeStatus.COMPLETE.getCode());
        wmsReceiveNotice.setBusinessType(WmsReceiveTypeEnum.TRANSFER_TO_STORAGE.getCode());
        wmsReceiveNotice.setCreateBy(SecurityUtils.getUserId());
        wmsReceiveNotice.setNoticeUser(SecurityUtils.getNickName());
        wmsReceiveNotice.setNoticeTime(DateUtils.getNowDate());
        wmsReceiveNotice.setCreateTime(DateUtils.getNowDate());
        wmsReceiveNotice.setStartTime(allocateDto.getOccTime());
        wmsReceiveNotice.setEndTime(allocateDto.getOccTime());
        wmsReceiveNoticeService.insertWmsReceiveNotice(wmsReceiveNotice);

        for (WmsItemStorageDetail itemStorageDetail : wmsItemStorageDetails) {
            // 分配前库存明细
            WmsItemStorageDetail storageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailById(itemStorageDetail.getId());

            // 查询 原库存汇总
            WmsItemStorage itemStorage = new WmsItemStorage();
            itemStorage.setWarehouseId(itemStorageDetail.getWarehouseId());
            itemStorage.setItemCode(itemStorageDetail.getItemCode());
            WmsItemStorage oldItemStorage = wmsItemStorageService.selectWmsItemStorage(itemStorage);
            // 判断是否将当前汇总的全部数量调拨
            if (oldItemStorage.getQuantity().compareTo(itemStorageDetail.getAllotQuantity()) == 0) {
                // 删除库存汇总
                wmsItemStorageService.deleteWmsItemStorageById(oldItemStorage.getId());
            } else {
                // 更新原库存汇总
                WmsItemStorage updateOldItemStorage = new WmsItemStorage();
                updateOldItemStorage.setId(oldItemStorage.getId());
                updateOldItemStorage.setQuantity(oldItemStorage.getQuantity().subtract(itemStorageDetail.getAllotQuantity()));
                updateOldItemStorage.setUpdateBy(SecurityUtils.getUserId());
                updateOldItemStorage.setUpdateTime(DateUtils.getNowDate());
                wmsItemStorageService.updateWmsItemStorage(updateOldItemStorage);
            }

            // 查询目标库存汇总
            WmsItemStorage searchInfo = new WmsItemStorage();
            searchInfo.setWarehouseId(itemStorageDetail.getTargetWarehouseId());
            searchInfo.setItemCode(itemStorageDetail.getItemCode());
            WmsItemStorage wmsItemStorage = wmsItemStorageService.selectWmsItemStorage(searchInfo);
            String wmsItemStorageId;
            if (null != wmsItemStorage) {
                wmsItemStorageId = wmsItemStorage.getId();
                // 更新目标库存汇总
                WmsItemStorage targetItemStorage = new WmsItemStorage();
                targetItemStorage.setId(wmsItemStorageId);
                targetItemStorage.setQuantity(wmsItemStorage.getQuantity().add(itemStorageDetail.getAllotQuantity()));
                targetItemStorage.setUpdateBy(SecurityUtils.getUserId());
                targetItemStorage.setUpdateTime(DateUtils.getNowDate());
                wmsItemStorageService.updateWmsItemStorage(targetItemStorage);

            } else {
                // 新增目标库存汇总
                wmsItemStorageId = IdUtils.fastSimpleUUID();
                WmsItemStorage newWmsItemStorage = new WmsItemStorage();
                newWmsItemStorage.setId(wmsItemStorageId);
                newWmsItemStorage.setCompanyId(companyId);
                newWmsItemStorage.setWarehouseId(itemStorageDetail.getTargetWarehouseId());
                newWmsItemStorage.setItemCode(itemStorageDetail.getItemCode());
                newWmsItemStorage.setItemName(itemStorageDetail.getItemName());
                newWmsItemStorage.setUnit(oldItemStorage.getUnit());
                newWmsItemStorage.setSpec(oldItemStorage.getSpec());
                newWmsItemStorage.setQuantity(itemStorageDetail.getAllotQuantity());
                newWmsItemStorage.setActualPrice(itemStorageDetail.getActualPrice());
                newWmsItemStorage.setStoragePrice(storageDetail.getStoragePrice());
                newWmsItemStorage.setCreateBy(SecurityUtils.getUserId());
                newWmsItemStorage.setCreateTime(DateUtils.getNowDate());
                wmsItemStorageService.insertWmsItemStorage(newWmsItemStorage);
            }


            // 移位数量等于库存数量
            if (itemStorageDetail.getAllotQuantity().compareTo(storageDetail.getQuantity()) == 0) {
                // 全部调拨 直接更新库存明细
                storageDetail.setStorageId(wmsItemStorageId);
                storageDetail.setLocationId(itemStorageDetail.getTargetLocationId());
                storageDetail.setUpdateBy(SecurityUtils.getUserId());
                storageDetail.setUpdateTime(DateUtils.getNowDate());
                wmsItemStorageDetailService.updateWmsItemStorageDetail(storageDetail);

            } else {
                // 更新原库存数量
                storageDetail.setQuantity(storageDetail.getQuantity().subtract(itemStorageDetail.getAllotQuantity()));
                storageDetail.setUpdateBy(SecurityUtils.getUserId());
                storageDetail.setUpdateTime(DateUtils.getNowDate());
                wmsItemStorageDetailService.updateWmsItemStorageDetail(storageDetail);

                // 新增库存

                WmsItemStorageDetail newStorageDetail = new WmsItemStorageDetail();
                newStorageDetail.setId(IdUtils.fastSimpleUUID());
                newStorageDetail.setStorageId(wmsItemStorageId);
                newStorageDetail.setLocationId(itemStorageDetail.getTargetLocationId());
                newStorageDetail.setItemCode(itemStorageDetail.getItemCode());
                newStorageDetail.setBatchNo(itemStorageDetail.getBatchNo());
                newStorageDetail.setQuantity(itemStorageDetail.getAllotQuantity());
                newStorageDetail.setProductDate(itemStorageDetail.getProductDate());
                newStorageDetail.setExpireDate(itemStorageDetail.getExpireDate());
                newStorageDetail.setStorageStatus(itemStorageDetail.getStorageStatus());
                newStorageDetail.setShelfLifeStatus(itemStorageDetail.getShelfLifeStatus());
                newStorageDetail.setRelateType(RelateType.NONE.getCode());
                newStorageDetail.setWorkStatus(WorkStatus.NORMAL.getCode());
                newStorageDetail.setCreateBy(SecurityUtils.getUserId());
                newStorageDetail.setCreateTime(DateUtils.getNowDate());
                wmsItemStorageDetailService.insertWmsItemStorageDetail(newStorageDetail);
            }

            // 生成入库通知明细
            WmsReceiveNoticeDetail wmsReceiveNoticeDetail = new WmsReceiveNoticeDetail();
            wmsReceiveNoticeDetail.setId(IdUtils.fastSimpleUUID());
            wmsReceiveNoticeDetail.setReceiveNoticeId(receiveNoticeId);
            wmsReceiveNoticeDetail.setItemCode(itemStorageDetail.getItemCode());
            wmsReceiveNoticeDetail.setItemName(itemStorageDetail.getItemName());
            wmsReceiveNoticeDetail.setBatchNo(itemStorageDetail.getBatchNo());
            wmsReceiveNoticeDetail.setProductDate(itemStorageDetail.getProductDate());
            wmsReceiveNoticeDetail.setQuantity(itemStorageDetail.getAllotQuantity());
            wmsReceiveNoticeDetail.setReceiveQuantity(itemStorageDetail.getAllotQuantity());
            wmsReceiveNoticeDetail.setUnit(itemStorageDetail.getUnit());
            wmsReceiveNoticeDetail.setSpec(itemStorageDetail.getSpec());
            wmsReceiveNoticeDetail.setStatus(ReceiptStatus.RECEIVED.getCode());
            wmsReceiveNoticeDetail.setCreateBy(SecurityUtils.getUserId());
            wmsReceiveNoticeDetail.setCreateTime(DateUtils.getNowDate());
            wmsReceiveNoticeDetailService.insertWmsReceiveNoticeDetail(wmsReceiveNoticeDetail);
        }

        // 保存出库通知
        WmsDeliveryNotice wmsDeliveryNotice = new WmsDeliveryNotice();
        wmsDeliveryNotice.setId(deliveryNoticeId);
        wmsDeliveryNotice.setBusinessType(WmsDeliveryTypeEnum.TRANSFER_OUT_OF_THE_WAREHOUSE.getCode());
        wmsDeliveryNotice.setCompanyId(companyId);
        wmsDeliveryNotice.setReceiveSendType(deliveryType);
        wmsDeliveryNotice.setDeliveryStartTime(allocateDto.getOccTime());
        wmsDeliveryNotice.setDeliveryEndTime(allocateDto.getOccTime());
        wmsDeliveryNotice.setStatus(DeliveryNoticeStatus.COMPLETE.getCode());
        wmsDeliveryNotice.setCreateBy(SecurityUtils.getUserId());
        wmsDeliveryNotice.setCreateTime(DateUtils.getNowDate());
        wmsDeliveryNoticeService.insertWmsDeliveryNotice(wmsDeliveryNotice);
        // 根据仓库ID 分组
        Map<String, List<WmsItemStorageDetail>> map = wmsItemStorageDetails.stream().collect(Collectors.groupingBy(WmsItemStorageDetail::getWarehouseId));

        // 根据分组后的数据生成出库通知
        for (Map.Entry<String, List<WmsItemStorageDetail>> entry : map.entrySet()) {
            List<WmsItemStorageDetail> storageDetails = entry.getValue();
            for (WmsItemStorageDetail storageDetail : storageDetails) {
                // 生成出库通知明细
                WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail = new WmsDeliveryNoticeDetail();
                wmsDeliveryNoticeDetail.setId(IdUtils.fastSimpleUUID());
                wmsDeliveryNoticeDetail.setDeliveryNoticeId(deliveryNoticeId);
                wmsDeliveryNoticeDetail.setQuantity(storageDetail.getAllotQuantity());
                wmsDeliveryNoticeDetail.setWorkFinishQuantity(BigDecimal.ZERO);
                wmsDeliveryNoticeDetail.setAllotQuantity(BigDecimal.ZERO);
                wmsDeliveryNoticeDetail.setItemCode(storageDetail.getItemCode());
                wmsDeliveryNoticeDetail.setItemName(storageDetail.getItemName());
                wmsDeliveryNoticeDetail.setItemUnit(storageDetail.getUnit());
                wmsDeliveryNoticeDetail.setDeliveryQuantity(storageDetail.getAllotQuantity());
                wmsDeliveryNoticeDetail.setItemSpec(storageDetail.getSpec());
                wmsDeliveryNoticeDetail.setCreateBy(SecurityUtils.getUserId());
                wmsDeliveryNoticeDetail.setCreateTime(DateUtils.getNowDate());
                wmsDeliveryNoticeDetailService.insertWmsDeliveryNoticeDetail(wmsDeliveryNoticeDetail);
            }

        }

        // 保存库存出入库记录
        storageRecordManager.saveStorageInOutRecord(dto);

        return AjaxResult.success();

    }


    public Boolean checkUnit(WmsReceiveNoticeDetail wmsReceiveNoticeDetail) {
        // 校验库存单位和通知单位是否一致
        WmsItemStorageDetail wmsItemStorageDetail = new WmsItemStorageDetail();
        wmsItemStorageDetail.setItemCode(wmsReceiveNoticeDetail.getItemCode());
        WmsItemStorageDetail itemStorageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(wmsItemStorageDetail);
        if (itemStorageDetail == null) {
            return false;
        }
        return wmsReceiveNoticeDetail.getUnit().equals(itemStorageDetail.getUnit());
    }

    public Boolean checkUnit(WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail) {
        // 校验库存单位和通知单位是否一致
        WmsItemStorageDetail wmsItemStorageDetail = new WmsItemStorageDetail();
        wmsItemStorageDetail.setItemCode(wmsDeliveryNoticeDetail.getItemCode());
        WmsItemStorageDetail itemStorageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(wmsItemStorageDetail);
        if (itemStorageDetail == null) {
            return false;
        }
        return wmsDeliveryNoticeDetail.getItemUnit().equals(itemStorageDetail.getUnit());
    }

    public void getProductDate(WmsReceiveNoticeDetail receiveNoticeDetail) {
        // 根据批号 物料编码 从库存中查询相同批次的生产日期
        WmsItemStorageDetail wmsItemStorageDetail = new WmsItemStorageDetail();
        wmsItemStorageDetail.setItemCode(receiveNoticeDetail.getItemCode());
        wmsItemStorageDetail.setBatchNo(receiveNoticeDetail.getBatchNo());
        WmsItemStorageDetail itemStorageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(wmsItemStorageDetail);
        if (null != itemStorageDetail && null != itemStorageDetail.getProductDate()) {
            receiveNoticeDetail.setProductDate(itemStorageDetail.getProductDate());
        }
    }

    /**
     * 获取物料的过期时间
     */
    public Date getExpireDate(Date productDate, String batchNo, String itemCode) {

        // 根据批次 物料编码查询库存中是否存在同批次物料
        WmsItemStorageDetail wmsItemStorageDetail = new WmsItemStorageDetail();
        wmsItemStorageDetail.setBatchNo(batchNo);
        wmsItemStorageDetail.setItemCode(itemCode);
        WmsItemStorageDetail itemStorageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(wmsItemStorageDetail);

        if (null != itemStorageDetail && null != itemStorageDetail.getExpireDate()) {
            return itemStorageDetail.getExpireDate();
        }

        // 查询物料主档中的保质期
        ProductInfo productInfo = productInfoService.findByItemCode(wmsItemStorageDetail.getItemCode());
        // 保质期
        String effectiveEndDate = productInfo.getEffectiveEndDate();

        if (StringUtils.isEmpty(effectiveEndDate) || null == productDate) {
            return null;
        }

        // 计算过期时间
        return DateUtils.addDays(productDate, Integer.parseInt(effectiveEndDate));
    }

}
