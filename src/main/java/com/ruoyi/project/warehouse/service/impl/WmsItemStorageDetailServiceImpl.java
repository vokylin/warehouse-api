package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.enums.ShelfLifeStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.WmsItemAttribute;
import com.ruoyi.project.system.service.IWmsItemAttributeService;
import com.ruoyi.project.warehouse.domain.StorageAlertVO;
import com.ruoyi.project.warehouse.domain.WmsItemStorageDetail;
import com.ruoyi.project.warehouse.mapper.WmsItemStorageDetailMapper;
import com.ruoyi.project.warehouse.service.IWmsItemStorageDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 库存详情Service业务层处理
 *
 * @author Rem
 * @date 2023-04-08
 */
@Service
public class WmsItemStorageDetailServiceImpl implements IWmsItemStorageDetailService {
    @Autowired
    private WmsItemStorageDetailMapper wmsItemStorageDetailMapper;

    @Autowired
    private IWmsItemAttributeService wmsItemAttributeService;

    /**
     * 查询库存详情
     *
     * @param id 库存详情主键
     * @return 库存详情
     */
    @Override
    public WmsItemStorageDetail selectWmsItemStorageDetailById(String id) {
        return wmsItemStorageDetailMapper.selectWmsItemStorageDetailById(id);
    }

    /**
     * 查询库存详情列表
     *
     * @param wmsItemStorageDetail 库存详情
     * @return 库存详情
     */
    @Override
    public List<WmsItemStorageDetail> selectWmsItemStorageDetailList(WmsItemStorageDetail wmsItemStorageDetail) {
        return wmsItemStorageDetailMapper.selectWmsItemStorageDetailList(wmsItemStorageDetail);
    }

    /**
     * 新增库存详情
     *
     * @param wmsItemStorageDetail 库存详情
     * @return 结果
     */
    @Override
    public int insertWmsItemStorageDetail(WmsItemStorageDetail wmsItemStorageDetail) {
        wmsItemStorageDetail.setCreateTime(DateUtils.getNowDate());
        return wmsItemStorageDetailMapper.insertWmsItemStorageDetail(wmsItemStorageDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult update(WmsItemStorageDetail wmsItemStorageDetail) {
        WmsItemStorageDetail itemStorageDetail = wmsItemStorageDetailMapper.selectWmsItemStorageDetailById(wmsItemStorageDetail.getId());
        if (itemStorageDetail == null) {
            return AjaxResult.error("库存详情不存在");
        }
        wmsItemStorageDetail.setItemCode(itemStorageDetail.getItemCode());
        if (!wmsItemStorageDetail.getIsStatusEdit()) {
            if (itemStorageDetail.getQuantity().compareTo(wmsItemStorageDetail.getQuantity()) < 0) {
                return AjaxResult.error("修改数量不能大于库存数量");
            } else if (itemStorageDetail.getQuantity().compareTo(wmsItemStorageDetail.getQuantity()) == 0) {
                if (null != wmsItemStorageDetail.getExpireDate() && null != itemStorageDetail.getExpireDate() && wmsItemStorageDetail.getExpireDate() != itemStorageDetail.getExpireDate()) {
                    this.setShelfLifeStatus(wmsItemStorageDetail);
                }
                wmsItemStorageDetail.setUpdateTime(DateUtils.getNowDate());
                wmsItemStorageDetail.setUpdateBy(SecurityUtils.getUserId());
                wmsItemStorageDetailMapper.updateWmsItemStorageDetail(wmsItemStorageDetail);
                return AjaxResult.success();
            } else {
                // 更新库存数量
                itemStorageDetail.setQuantity(itemStorageDetail.getQuantity().subtract(wmsItemStorageDetail.getQuantity()));
                itemStorageDetail.setUpdateTime(DateUtils.getNowDate());
                itemStorageDetail.setUpdateBy(SecurityUtils.getUserId());
                wmsItemStorageDetailMapper.updateWmsItemStorageDetail(itemStorageDetail);

                // 新增库存详情
                WmsItemStorageDetail newStorageDetail = new WmsItemStorageDetail();
                newStorageDetail.setId(IdUtils.fastSimpleUUID());
                newStorageDetail.setStorageId(itemStorageDetail.getStorageId());
                newStorageDetail.setBatchNo(wmsItemStorageDetail.getBatchNo());
                newStorageDetail.setQuantity(wmsItemStorageDetail.getQuantity());
                newStorageDetail.setLocationId(itemStorageDetail.getLocationId());
                newStorageDetail.setStorageStatus(wmsItemStorageDetail.getStorageStatus());
                newStorageDetail.setWorkStatus(itemStorageDetail.getWorkStatus());
                newStorageDetail.setProductDate(wmsItemStorageDetail.getProductDate());
                newStorageDetail.setExpireDate(wmsItemStorageDetail.getExpireDate());
                newStorageDetail.setThermosphere(itemStorageDetail.getThermosphere());
                newStorageDetail.setRelateType(itemStorageDetail.getRelateType());
                newStorageDetail.setRelateId(itemStorageDetail.getRelateId());
                newStorageDetail.setCreateBy(SecurityUtils.getUserId());
                newStorageDetail.setCreateTime(DateUtils.getNowDate());
                if (null != wmsItemStorageDetail.getExpireDate() && null != itemStorageDetail.getExpireDate() && wmsItemStorageDetail.getExpireDate() != itemStorageDetail.getExpireDate()) {
                    this.setShelfLifeStatus(newStorageDetail);
                }
                wmsItemStorageDetailMapper.insertWmsItemStorageDetail(newStorageDetail);

                return AjaxResult.success();
            }
        } else {
            wmsItemStorageDetail.setUpdateTime(DateUtils.getNowDate());
            wmsItemStorageDetail.setUpdateBy(SecurityUtils.getUserId());
            wmsItemStorageDetailMapper.updateWmsItemStorageDetail(wmsItemStorageDetail);
            return AjaxResult.success();
        }

    }

    /**
     * 修改库存详情
     *
     * @param wmsItemStorageDetail 库存详情
     * @return 结果
     */
    @Override
    public int updateWmsItemStorageDetail(WmsItemStorageDetail wmsItemStorageDetail) {
        wmsItemStorageDetail.setUpdateTime(DateUtils.getNowDate());
        return wmsItemStorageDetailMapper.updateWmsItemStorageDetail(wmsItemStorageDetail);
    }

    /**
     * 批量删除库存详情
     *
     * @param ids 需要删除的库存详情主键
     * @return 结果
     */
    @Override
    public int deleteWmsItemStorageDetailByIds(String[] ids) {
        return wmsItemStorageDetailMapper.deleteWmsItemStorageDetailByIds(ids);
    }

    /**
     * 删除库存详情信息
     *
     * @param id 库存详情主键
     * @return 结果
     */
    @Override
    public int deleteWmsItemStorageDetailById(String id) {
        return wmsItemStorageDetailMapper.deleteWmsItemStorageDetailById(id);
    }

    /**
     * 根据用户拥有的仓库权限查询库存详情
     *
     * @param wmsItemStorageDetail wms项存储细节
     * @return {@link List}<{@link WmsItemStorageDetail}>
     */
    @Override
    public List<WmsItemStorageDetail> selectWmsItemStorageDetailListByUser(WmsItemStorageDetail wmsItemStorageDetail) {
        return wmsItemStorageDetailMapper.selectWmsItemStorageDetailListByUser(wmsItemStorageDetail);
    }


    /**
     * 根据批次号查询库存详情
     *
     * @param selectDetail 选择细节
     * @return {@link WmsItemStorageDetail}
     */
    @Override
    public WmsItemStorageDetail selectWmsItemStorageDetailByBatchNo(WmsItemStorageDetail selectDetail) {
        return wmsItemStorageDetailMapper.selectWmsItemStorageDetailByBatchNo(selectDetail);
    }

    /**
     * 根据出库通知单id查询库存详情
     *
     * @param wmsItemStorageDetail wmsItemStorageDetail
     */
    @Override
    public List<WmsItemStorageDetail> selectByDeliveryNoticeId(WmsItemStorageDetail wmsItemStorageDetail) {
        return wmsItemStorageDetailMapper.selectByDeliveryNoticeId(wmsItemStorageDetail);
    }

    @Override
    public List<WmsItemStorageDetail> selectAdventItem() {
        return wmsItemStorageDetailMapper.selectAdventItem();
    }

    @Override
    public List<StorageAlertVO> selectStorageAlertList() {
        return wmsItemStorageDetailMapper.selectStorageAlertList();
    }

    /**
     * 更新过期货物状态
     *
     * @return
     */
    @Override
    public int updateTheExpiredItemStatus() {
        return wmsItemStorageDetailMapper.updateTheExpiredItemStatus();
    }

    /**
     * 更新临期货物状态
     *
     * @param ids
     * @return int
     */
    @Override
    public int updateAdventStatus(List<String> ids) {
        return wmsItemStorageDetailMapper.updateAdventStatus(ids);
    }

    /**
     * 临期状态检查
     *
     * @param wmsItemStorageDetail wms项存储细节
     */
    @Override
    public void setShelfLifeStatus(WmsItemStorageDetail wmsItemStorageDetail) {
        WmsItemAttribute wmsItemAttribute = wmsItemAttributeService.selectWmsItemAttributeByCode(wmsItemStorageDetail.getItemCode());
        // 过期日期
        Date expireDate = wmsItemStorageDetail.getExpireDate();
        if (null == expireDate) {
            return;
        }
        // 当前日期
        Date nowDate = new Date();
        // 如果当前日期大于过期日期 则为过期
        if (nowDate.compareTo(expireDate) > 0) {
            // 过期
            wmsItemStorageDetail.setShelfLifeStatus(ShelfLifeStatus.EXPIRED.getCode());
        } else {
            // 判断是否需要预警
            if (null != wmsItemAttribute && null != wmsItemAttribute.getRemindDay()) {
                // 预警日期
                Date remindDate = DateUtils.addDays(expireDate, -wmsItemAttribute.getRemindDay());
                // 如果当前日期大于预警日期 则为临期
                if (nowDate.compareTo(remindDate) > 0) {
                    // 预警
                    wmsItemStorageDetail.setShelfLifeStatus(ShelfLifeStatus.EARLY.getCode());
                } else {
                    // 正常
                    wmsItemStorageDetail.setShelfLifeStatus(ShelfLifeStatus.NORMAL.getCode());
                }
            } else {
                // 正常
                wmsItemStorageDetail.setShelfLifeStatus(ShelfLifeStatus.NORMAL.getCode());
            }
        }
    }

    @Override
    public List<WmsItemStorageDetail> checkStorageStatus(List<String> warehouseList, List<String> storageStatusList) {
        return wmsItemStorageDetailMapper.checkStorageStatus(warehouseList, storageStatusList);
    }

    @Override
    public List<WmsItemStorageDetail> selectByInventoryPlan(List<String> warehouseList, List<String> storageStatusList) {
        return wmsItemStorageDetailMapper.selectByInventoryPlan(warehouseList, storageStatusList);
    }

    /**
     * 更新关联状态
     *
     * @param storageDetail 存储细节
     */
    @Override
    public int updateRelateType(WmsItemStorageDetail storageDetail) {
        return wmsItemStorageDetailMapper.updateRelateType(storageDetail);
    }

    /**
     * 查询报损列表
     *
     * @param wmsItemStorageDetail wms项存储细节
     * @return {@link List}<{@link WmsItemStorageDetail}>
     */
    @Override
    public List<WmsItemStorageDetail> selectBreakageList(WmsItemStorageDetail wmsItemStorageDetail) {
        return wmsItemStorageDetailMapper.selectBreakageList(wmsItemStorageDetail);
    }

    @Override
    public void updateAbnormalItem(String relateId) {
        wmsItemStorageDetailMapper.updateAbnormalItem(relateId);
    }
}
