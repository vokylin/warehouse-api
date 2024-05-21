package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.enums.RelateType;
import com.ruoyi.common.enums.StorageStatus;
import com.ruoyi.common.enums.WorkStatus;
import com.ruoyi.common.exception.business.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.project.warehouse.domain.BreakageDocPrintInfo;
import com.ruoyi.project.warehouse.domain.WmsBreakageDoc;
import com.ruoyi.project.warehouse.domain.WmsBreakageDocDetail;
import com.ruoyi.project.warehouse.domain.WmsItemStorageDetail;
import com.ruoyi.project.warehouse.mapper.WmsBreakageDocDetailMapper;
import com.ruoyi.project.warehouse.service.IWmsBreakageDocDetailService;
import com.ruoyi.project.warehouse.service.IWmsBreakageDocService;
import com.ruoyi.project.warehouse.service.IWmsItemStorageDetailService;
import com.ruoyi.project.warehouse.service.IWmsItemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 报损物料明细Service业务层处理
 *
 * @author Rem
 * @date 2023-05-20
 */
@Service
public class WmsBreakageDocDetailServiceImpl implements IWmsBreakageDocDetailService {
    @Autowired
    private WmsBreakageDocDetailMapper wmsBreakageDocDetailMapper;

    @Autowired
    private IWmsItemStorageDetailService wmsItemStorageDetailService;

    @Autowired
    private IWmsItemStorageService wmsItemStorageService;

    @Autowired
    private IWmsBreakageDocService wmsBreakageDocService;

    /**
     * 查询报损物料明细
     *
     * @param id 报损物料明细主键
     * @return 报损物料明细
     */
    @Override
    public WmsBreakageDocDetail selectWmsBreakageDocDetailById(String id) {
        return wmsBreakageDocDetailMapper.selectWmsBreakageDocDetailById(id);
    }

    /**
     * 查询报损物料明细列表
     *
     * @param wmsBreakageDocDetail 报损物料明细
     * @return 报损物料明细
     */
    @Override
    public List<WmsBreakageDocDetail> selectWmsBreakageDocDetailList(WmsBreakageDocDetail wmsBreakageDocDetail) {
        return wmsBreakageDocDetailMapper.selectWmsBreakageDocDetailList(wmsBreakageDocDetail);
    }

    /**
     * 新增报损物料明细
     *
     * @param wmsBreakageDocDetail 报损物料明细
     * @return 结果
     */
    @Override
    public int insertWmsBreakageDocDetail(WmsBreakageDocDetail wmsBreakageDocDetail) {
        wmsBreakageDocDetail.setCreateTime(DateUtils.getNowDate());
        return wmsBreakageDocDetailMapper.insertWmsBreakageDocDetail(wmsBreakageDocDetail);
    }

    /**
     * 修改报损物料明细
     *
     * @param wmsBreakageDocDetail 报损物料明细
     * @return 结果
     */
    @Override
    public int updateWmsBreakageDocDetail(WmsBreakageDocDetail wmsBreakageDocDetail) {
        wmsBreakageDocDetail.setUpdateTime(DateUtils.getNowDate());
        return wmsBreakageDocDetailMapper.updateWmsBreakageDocDetail(wmsBreakageDocDetail);
    }

    /**
     * 批量删除报损物料明细
     *
     * @param ids 需要删除的报损物料明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsBreakageDocDetailByIds(String[] ids) {
        return wmsBreakageDocDetailMapper.deleteWmsBreakageDocDetailByIds(ids);
    }

    /**
     * 删除报损物料明细信息
     *
     * @param id 报损物料明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsBreakageDocDetailById(String id) {
        return wmsBreakageDocDetailMapper.deleteWmsBreakageDocDetailById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void allotItemStorage(WmsItemStorageDetail itemStorageDetail) {
        String breakageDocId = itemStorageDetail.getRelateId();
        BigDecimal quantity = itemStorageDetail.getQuantity();
        BigDecimal allotQuantity = itemStorageDetail.getAllotQuantity();
        String badCauses = itemStorageDetail.getBadCauses();
        if (allotQuantity.compareTo(quantity) > 0) {
            throw new BusinessException("分配数量不能大于库存数量");
        }
        WmsBreakageDoc wmsBreakageDoc = wmsBreakageDocService.selectWmsBreakageDocById(breakageDocId);
        if (null == wmsBreakageDoc) {
            throw new BusinessException("报损单不存在");
        }
        // 查询是否分配过
        WmsBreakageDocDetail selectDetail = new WmsBreakageDocDetail();
        selectDetail.setBreakageDocId(breakageDocId);
        selectDetail.setItemCode(itemStorageDetail.getItemCode());
        selectDetail.setBatchNo(itemStorageDetail.getBatchNo());
        WmsBreakageDocDetail wmsBreakageDocDetail = wmsBreakageDocDetailMapper.selectDetail(selectDetail);

        // 查询当前分配库存详情
        WmsItemStorageDetail storageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailById(itemStorageDetail.getId());
        if (null == storageDetail) {
            throw new BusinessException("库存详情不存在");
        }

        if (allotQuantity.compareTo(quantity) == 0) {
            // 分配过 更新报损单详情
            if (null != wmsBreakageDocDetail) {
                wmsBreakageDocDetail.setQuantity(allotQuantity.add(wmsBreakageDocDetail.getQuantity()));
                wmsBreakageDocDetail.setBadCauses(badCauses);
                wmsBreakageDocDetailMapper.updateWmsBreakageDocDetail(wmsBreakageDocDetail);

                // 更新库存数量
                WmsItemStorageDetail selectItemStorageDetail = new WmsItemStorageDetail();
                selectItemStorageDetail.setItemCode(storageDetail.getItemCode());
                selectItemStorageDetail.setBatchNo(storageDetail.getBatchNo());
                selectItemStorageDetail.setRelateId(breakageDocId);
                selectItemStorageDetail.setRelateType(RelateType.LOSS_IN_PROGRESS.getCode());
                selectItemStorageDetail.setWarehouseId(storageDetail.getWarehouseId());
                selectItemStorageDetail.setLocationId(storageDetail.getLocationId());
                WmsItemStorageDetail wmsItemStorageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(selectItemStorageDetail);
                if (null == wmsItemStorageDetail) {
                    throw new BusinessException("库存详情不存在");
                }
                WmsItemStorageDetail updateItemStorageDetail = new WmsItemStorageDetail();
                updateItemStorageDetail.setId(wmsItemStorageDetail.getId());
                updateItemStorageDetail.setQuantity(wmsItemStorageDetail.getQuantity().add(allotQuantity));
                wmsItemStorageDetailService.updateWmsItemStorageDetail(updateItemStorageDetail);

                // 删除当前分配的库存
                wmsItemStorageDetailService.deleteWmsItemStorageDetailById(storageDetail.getId());
            } else {
                // 没有分配过 新增一条报损单详情
                addDetail(itemStorageDetail, breakageDocId, allotQuantity, quantity, badCauses);

                // 更新原有库存详情状态
                itemStorageDetail.setWorkStatus(WorkStatus.WORKING.getCode());
                itemStorageDetail.setRelateType(RelateType.LOSS_IN_PROGRESS.getCode());
                itemStorageDetail.setRelateId(breakageDocId);
                wmsItemStorageDetailService.updateWmsItemStorageDetail(itemStorageDetail);
            }
        }
        // 分配数量小于库存数量
        if (allotQuantity.compareTo(quantity) < 0) {
            //  没有分配过 新增一条报损单详情
            if (null == wmsBreakageDocDetail) {
                // 新增库存
                WmsItemStorageDetail newStorageDetail = new WmsItemStorageDetail();
                newStorageDetail.setId(IdUtils.fastSimpleUUID());
                newStorageDetail.setStorageId(storageDetail.getStorageId());
                newStorageDetail.setLocationId(storageDetail.getLocationId());
                newStorageDetail.setItemCode(storageDetail.getItemCode());
                newStorageDetail.setBatchNo(storageDetail.getBatchNo());
                newStorageDetail.setQuantity(allotQuantity);
                newStorageDetail.setProductDate(storageDetail.getProductDate());
                newStorageDetail.setExpireDate(storageDetail.getExpireDate());
                newStorageDetail.setRelateType(RelateType.LOSS_IN_PROGRESS.getCode());
                newStorageDetail.setRelateId(breakageDocId);
                newStorageDetail.setWorkStatus(WorkStatus.WORKING.getCode());
                newStorageDetail.setStorageStatus(StorageStatus.DEFECTIVE.getCode());
                newStorageDetail.setCreateBy(SecurityUtils.getUserId());
                newStorageDetail.setCreateTime(DateUtils.getNowDate());
                wmsItemStorageDetailService.insertWmsItemStorageDetail(newStorageDetail);

                addDetail(itemStorageDetail, breakageDocId, allotQuantity, quantity, badCauses);
            } else {
                // 更新报损明细数量
                wmsBreakageDocDetail.setBadCauses(badCauses);
                wmsBreakageDocDetail.setQuantity(wmsBreakageDocDetail.getQuantity().add(allotQuantity));
                wmsBreakageDocDetailMapper.updateWmsBreakageDocDetail(wmsBreakageDocDetail);

                // 更新库存数量
                WmsItemStorageDetail selectItemStorageDetail = new WmsItemStorageDetail();
                selectItemStorageDetail.setItemCode(storageDetail.getItemCode());
                selectItemStorageDetail.setBatchNo(storageDetail.getBatchNo());
                selectItemStorageDetail.setRelateId(breakageDocId);
                selectItemStorageDetail.setRelateType(RelateType.LOSS_IN_PROGRESS.getCode());
                selectItemStorageDetail.setWarehouseId(storageDetail.getWarehouseId());
                selectItemStorageDetail.setLocationId(storageDetail.getLocationId());
                WmsItemStorageDetail wmsItemStorageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(selectItemStorageDetail);
                if (null == wmsItemStorageDetail) {
                    throw new BusinessException("库存详情不存在");
                }
                WmsItemStorageDetail updateItemStorageDetail = new WmsItemStorageDetail();
                updateItemStorageDetail.setId(wmsItemStorageDetail.getId());
                updateItemStorageDetail.setQuantity(wmsItemStorageDetail.getQuantity().add(allotQuantity));
                wmsItemStorageDetailService.updateWmsItemStorageDetail(updateItemStorageDetail);

            }
            // 扣减原库存数量
            WmsItemStorageDetail updateItemDetail = new WmsItemStorageDetail();
            updateItemDetail.setId(itemStorageDetail.getId());
            updateItemDetail.setQuantity(quantity.subtract(allotQuantity));
            wmsItemStorageDetailService.updateWmsItemStorageDetail(updateItemDetail);
        }
    }

    private void addDetail(WmsItemStorageDetail wmsItemStorageDetail, String breakageDocId, BigDecimal allotQuantity,
                           BigDecimal quantity, String badCauses) {
        WmsBreakageDocDetail addDetail = new WmsBreakageDocDetail();
        addDetail.setId(IdUtils.fastSimpleUUID());
        addDetail.setBreakageDocId(breakageDocId);
        addDetail.setItemCode(wmsItemStorageDetail.getItemCode());
        addDetail.setItemName(wmsItemStorageDetail.getItemName());
        addDetail.setBatchNo(wmsItemStorageDetail.getBatchNo());
        addDetail.setExpireDate(wmsItemStorageDetail.getExpireDate());
        addDetail.setProductDate(wmsItemStorageDetail.getProductDate());
        addDetail.setPrice(wmsItemStorageDetail.getActualPrice());
        addDetail.setUnit(wmsItemStorageDetail.getUnit());
        addDetail.setSpec(wmsItemStorageDetail.getSpec());
        // 物料总数为该批次物料的库存数量
        addDetail.setTotalQuantity(quantity);
        addDetail.setBadCauses(badCauses);
        addDetail.setWarehouseId(wmsItemStorageDetail.getWarehouseId());
        addDetail.setLocationId(wmsItemStorageDetail.getLocationId());
        addDetail.setQuantity(allotQuantity);
        addDetail.setCreateTime(DateUtils.getNowDate());
        wmsBreakageDocDetailMapper.insertWmsBreakageDocDetail(addDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelAllotItemStorage(WmsBreakageDocDetail wmsBreakageDocDetail) throws Exception {
        // 查询当前需要取消分配的库存
        WmsItemStorageDetail currentStorage = new WmsItemStorageDetail();
        currentStorage.setItemCode(wmsBreakageDocDetail.getItemCode());
        currentStorage.setBatchNo(wmsBreakageDocDetail.getBatchNo());
        currentStorage.setRelateType(RelateType.LOSS_IN_PROGRESS.getCode());
        currentStorage.setRelateId(wmsBreakageDocDetail.getBreakageDocId());
        currentStorage.setLocationId(wmsBreakageDocDetail.getLocationId());
        currentStorage.setWorkStatus(WorkStatus.WORKING.getCode());
        WmsItemStorageDetail currentItemStorageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(currentStorage);
        if (null == currentItemStorageDetail) {
            throw new BusinessException("当前库存信息不存在");
        }

        // 查询是否存在相同批次的库存
        WmsItemStorageDetail sourceStorage = new WmsItemStorageDetail();
        sourceStorage.setItemCode(wmsBreakageDocDetail.getItemCode());
        sourceStorage.setBatchNo(wmsBreakageDocDetail.getBatchNo());
        sourceStorage.setRelateType(RelateType.NONE.getCode());
        sourceStorage.setWorkStatus(WorkStatus.NORMAL.getCode());
        sourceStorage.setStorageStatus(StorageStatus.DEFECTIVE.getCode());
        sourceStorage.setLocationId(currentStorage.getLocationId());
        WmsItemStorageDetail sourceItemStorageDetailInfo = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(sourceStorage);

        if (null != sourceItemStorageDetailInfo) {
            // 存在相同批次的库存 更新库存数量
            sourceItemStorageDetailInfo.setQuantity(sourceItemStorageDetailInfo.getQuantity().add(wmsBreakageDocDetail.getQuantity()));
            sourceItemStorageDetailInfo.setUpdateBy(SecurityUtils.getUserId());
            sourceItemStorageDetailInfo.setUpdateTime(DateUtils.getNowDate());
            wmsItemStorageDetailService.updateWmsItemStorageDetail(sourceItemStorageDetailInfo);

            // 删除库存详情
            wmsItemStorageDetailService.deleteWmsItemStorageDetailById(currentItemStorageDetail.getId());
        } else {
            // 不存在 更新库存状态
            currentItemStorageDetail.setRelateType(RelateType.NONE.getCode());
            currentItemStorageDetail.setWorkStatus(WorkStatus.NORMAL.getCode());
            currentItemStorageDetail.setRelateId("");
            currentItemStorageDetail.setUpdateBy(SecurityUtils.getUserId());
            currentItemStorageDetail.setUpdateTime(DateUtils.getNowDate());
            wmsItemStorageDetailService.updateWmsItemStorageDetail(currentItemStorageDetail);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelAllotItemStorageAndDelete(WmsBreakageDocDetail wmsBreakageDocDetail) throws Exception {
        this.cancelAllotItemStorage(wmsBreakageDocDetail);
        int result = wmsBreakageDocDetailMapper.deleteWmsBreakageDocDetailById(wmsBreakageDocDetail.getId());
        if (result <= 0) {
            throw new BusinessException("取消分配失败");
        }
    }

    @Override
    public int generatePrintNumber(BreakageDocPrintInfo item) {
        return wmsBreakageDocDetailMapper.generatePrintNumber(item.getDetailId(), DateUtils.getMonthFirstDay(item.getOccTime()), DateUtils.getMonthLastDay(item.getOccTime()));
    }


}
