package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.RelateType;
import com.ruoyi.common.enums.WorkStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.ProductInfo;
import com.ruoyi.project.system.service.IProductInfoService;
import com.ruoyi.project.warehouse.domain.WmsInventoryDetail;
import com.ruoyi.project.warehouse.domain.WmsInventoryPlan;
import com.ruoyi.project.warehouse.domain.WmsItemStorageDetail;
import com.ruoyi.project.warehouse.mapper.WmsInventoryDetailMapper;
import com.ruoyi.project.warehouse.service.IWmsInventoryDetailService;
import com.ruoyi.project.warehouse.service.IWmsItemStorageDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 盘点详情Service业务层处理
 *
 * @author Rem
 * @date 2023-05-21
 */
@Service
public class WmsInventoryDetailServiceImpl implements IWmsInventoryDetailService {
    @Autowired
    private WmsInventoryDetailMapper wmsInventoryDetailMapper;

    @Autowired
    private IProductInfoService productInfoService;

    @Autowired
    private IWmsItemStorageDetailService wmsItemStorageDetailService;

    /**
     * 查询盘点详情
     *
     * @param id 盘点详情主键
     * @return 盘点详情
     */
    @Override
    public WmsInventoryDetail selectWmsInventoryDetailById(String id) {
        return wmsInventoryDetailMapper.selectWmsInventoryDetailById(id);
    }

    /**
     * 查询盘点详情列表
     *
     * @param wmsInventoryDetail 盘点详情
     * @return 盘点详情
     */
    @Override
    public List<WmsInventoryDetail> selectWmsInventoryDetailList(WmsInventoryDetail wmsInventoryDetail) {
        return wmsInventoryDetailMapper.selectWmsInventoryDetailList(wmsInventoryDetail);
    }

    /**
     * 新增盘点详情
     *
     * @param wmsInventoryDetail 盘点详情
     * @return 结果
     */
    @Override
    public AjaxResult insertWmsInventoryDetail(WmsInventoryDetail wmsInventoryDetail) {

        ProductInfo productInfo = productInfoService.findByItemCode(wmsInventoryDetail.getItemCode());
        if (null == productInfo) {
            return AjaxResult.error("物料编码不存在");
        }
        if (null == wmsInventoryDetail.getProductDate()) {
            return AjaxResult.error("生产日期不能为空");
        }
        // 查询库存明细
        WmsItemStorageDetail selectDetail = new WmsItemStorageDetail();
        selectDetail.setBatchNo(wmsInventoryDetail.getBatchNo());
        selectDetail.setItemCode(wmsInventoryDetail.getItemCode());
        selectDetail.setRelateType(RelateType.INVENTORY.getCode());
        selectDetail.setWarehouseId(wmsInventoryDetail.getWarehouseId());
        selectDetail.setLocationId(wmsInventoryDetail.getLocationId());
        selectDetail.setRelateId(wmsInventoryDetail.getInventoryId());
        WmsItemStorageDetail wmsItemStorageDetailInfo = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(selectDetail);
        if (null != wmsItemStorageDetailInfo) {
            return AjaxResult.error("库存中存在相同批次的物料!");
        }
        wmsInventoryDetail.setId(IdUtils.fastSimpleUUID());
        wmsInventoryDetail.setItemName(productInfo.getProductName());
        wmsInventoryDetail.setUnit(productInfo.getMasterUnit());
        wmsInventoryDetail.setPrice(BigDecimal.ZERO);
        wmsInventoryDetail.setSpec(productInfo.getSpec());
        wmsInventoryDetail.setQuantity(BigDecimal.ZERO);
        wmsInventoryDetail.setDiffQuantity(wmsInventoryDetail.getInventoryQuantity());
        wmsInventoryDetail.setCreateBy(SecurityUtils.getUserId());
        wmsInventoryDetail.setCreateTime(DateUtils.getNowDate());
        wmsInventoryDetailMapper.insertWmsInventoryDetail(wmsInventoryDetail);
        return AjaxResult.success();
    }

    /**
     * 修改盘点详情
     *
     * @param wmsInventoryDetail 盘点详情
     * @return 结果
     */
    @Override
    public int updateWmsInventoryDetail(WmsInventoryDetail wmsInventoryDetail) {
        wmsInventoryDetail.setUpdateTime(DateUtils.getNowDate());
        wmsInventoryDetail.setDiffQuantity(wmsInventoryDetail.getInventoryQuantity().subtract(wmsInventoryDetail.getQuantity()));
        return wmsInventoryDetailMapper.updateWmsInventoryDetail(wmsInventoryDetail);
    }

    /**
     * 批量删除盘点详情
     *
     * @param ids 需要删除的盘点详情主键
     * @return 结果
     */
    @Override
    public int deleteWmsInventoryDetailByIds(String[] ids) {
        return wmsInventoryDetailMapper.deleteWmsInventoryDetailByIds(ids);
    }

    /**
     * 删除盘点详情信息
     *
     * @param id 盘点详情主键
     * @return 结果
     */
    @Override
    public int deleteWmsInventoryDetailById(String id) {
        return wmsInventoryDetailMapper.deleteWmsInventoryDetailById(id);
    }

    /**
     * 批量新增盘点详情
     *
     * @param wmsItemStorageDetails wms项存储细节
     * @param wmsInventoryPlan      wms库存计划
     */
    @Override
    public void generateInventoryTask(List<WmsItemStorageDetail> wmsItemStorageDetails, WmsInventoryPlan wmsInventoryPlan) {
        wmsItemStorageDetails.forEach(
                wmsItemStorageDetail -> addDetail(wmsItemStorageDetail, wmsInventoryPlan)
        );
    }

    /**
     * 查询盘点详情列表
     *
     * @param id id
     * @return {@link List}<{@link WmsInventoryDetail}>
     */
    @Override
    public List<WmsInventoryDetail> selectDetailByPlanId(String id) {
        WmsInventoryDetail wmsInventoryDetail = new WmsInventoryDetail();
        wmsInventoryDetail.setInventoryId(id);
        return this.selectWmsInventoryDetailList(wmsInventoryDetail);
    }

    @Override
    public List<WmsInventoryDetail> selectNoCountedPlanId(String id) {
        return wmsInventoryDetailMapper.selectByInventoryPlan(id);
    }

    @Override
    public AjaxResult batchUpdateWmsInventoryDetail(List<WmsInventoryDetail> wmsInventoryDetailList) {
        wmsInventoryDetailList.forEach(
                (item) -> {
                    item.setIsSave(Constants.YES);
                    item.setDiffQuantity(item.getInventoryQuantity().subtract(item.getQuantity()));
                    item.setUpdateTime(DateUtils.getNowDate());
                    item.setUpdateBy(SecurityUtils.getUserId());
                    wmsInventoryDetailMapper.updateWmsInventoryDetail(item);
                }
        );
        return AjaxResult.success();
    }

    private void addDetail(WmsItemStorageDetail wmsItemStorageDetail, WmsInventoryPlan wmsInventoryPlan) {
        WmsInventoryDetail wmsInventoryDetail = new WmsInventoryDetail();
        wmsInventoryDetail.setId(IdUtils.fastSimpleUUID());
        wmsInventoryDetail.setInventoryId(wmsInventoryPlan.getId());
        wmsInventoryDetail.setStorageDetailId(wmsItemStorageDetail.getId());
        wmsInventoryDetail.setWarehouseId(wmsItemStorageDetail.getWarehouseId());
        wmsInventoryDetail.setLocationId(wmsItemStorageDetail.getLocationId());
        wmsInventoryDetail.setItemCode(wmsItemStorageDetail.getItemCode());
        wmsInventoryDetail.setItemName(wmsItemStorageDetail.getItemName());
        wmsInventoryDetail.setUnit(wmsItemStorageDetail.getUnit());
        wmsInventoryDetail.setSpec(wmsItemStorageDetail.getSpec());
        wmsInventoryDetail.setPrice(wmsItemStorageDetail.getActualPrice());
        wmsInventoryDetail.setBatchNo(wmsItemStorageDetail.getBatchNo());
        wmsInventoryDetail.setQuantity(wmsItemStorageDetail.getQuantity());
        wmsInventoryDetail.setStorageStatus(wmsItemStorageDetail.getStorageStatus());
        wmsInventoryDetail.setProductDate(wmsItemStorageDetail.getProductDate());
        wmsInventoryDetail.setCreateTime(DateUtils.getNowDate());
        wmsInventoryDetail.setCreateBy(SecurityUtils.getUserId());
        wmsInventoryDetailMapper.insertWmsInventoryDetail(wmsInventoryDetail);

        // 更新库存明细 关联ID为盘点计划ID 关联类型为盘点中 作业状态为作业中
        wmsItemStorageDetail.setRelateId(wmsInventoryPlan.getId());
        wmsItemStorageDetail.setRelateType(RelateType.INVENTORY.getCode());
        wmsItemStorageDetail.setWorkStatus(WorkStatus.WORKING.getCode());
        wmsItemStorageDetailService.updateWmsItemStorageDetail(wmsItemStorageDetail);
    }
}
