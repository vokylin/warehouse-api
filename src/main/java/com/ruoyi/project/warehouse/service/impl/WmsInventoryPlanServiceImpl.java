package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.InventoryPlanStatus;
import com.ruoyi.common.enums.RelateType;
import com.ruoyi.common.exception.business.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.component.StorageManager;
import com.ruoyi.project.common.entity.InventoryAuditDto;
import com.ruoyi.project.common.entity.InventoryPlanDto;
import com.ruoyi.project.warehouse.domain.WmsInventoryDetail;
import com.ruoyi.project.warehouse.domain.WmsInventoryPlan;
import com.ruoyi.project.warehouse.domain.WmsItemStorageDetail;
import com.ruoyi.project.warehouse.mapper.WmsInventoryPlanMapper;
import com.ruoyi.project.warehouse.service.IWmsInventoryDetailService;
import com.ruoyi.project.warehouse.service.IWmsInventoryPlanService;
import com.ruoyi.project.warehouse.service.IWmsItemStorageDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 盘点计划Service业务层处理
 *
 * @author Rem
 * @date 2023-05-21
 */
@Service
public class WmsInventoryPlanServiceImpl implements IWmsInventoryPlanService {
    @Autowired
    private WmsInventoryPlanMapper wmsInventoryPlanMapper;

    @Autowired
    private IWmsItemStorageDetailService wmsItemStorageDetailService;

    @Autowired
    private IWmsInventoryDetailService wmsInventoryDetailService;

    @Autowired
    private StorageManager storageManager;

    /**
     * 查询盘点计划
     *
     * @param id 盘点计划主键
     * @return 盘点计划
     */
    @Override
    public WmsInventoryPlan selectWmsInventoryPlanById(String id) {
        return wmsInventoryPlanMapper.selectWmsInventoryPlanById(id);
    }

    /**
     * 查询盘点计划列表
     *
     * @param wmsInventoryPlan 盘点计划
     * @return 盘点计划
     */
    @Override
    public List<WmsInventoryPlan> selectWmsInventoryPlanList(WmsInventoryPlan wmsInventoryPlan) {
        return wmsInventoryPlanMapper.selectWmsInventoryPlanList(wmsInventoryPlan);
    }

    /**
     * 新增盘点计划
     *
     * @param wmsInventoryPlan 盘点计划
     * @return 结果
     */
    @Override
    public AjaxResult insertWmsInventoryPlan(WmsInventoryPlan wmsInventoryPlan) {
        String warehouseIds = wmsInventoryPlan.getWarehouseIds();
        String storageStatus = wmsInventoryPlan.getStorageStatus();
        List<String> warehouseList = Arrays.stream(warehouseIds.split(",")).collect(Collectors.toList());
        List<String> storageStatusList = Arrays.stream(storageStatus.split(",")).collect(Collectors.toList());
        AjaxResult ajaxResult = checkStorageStatus(warehouseList, storageStatusList);
        if (!ajaxResult.isSuccess()) {
            return ajaxResult;
        }
        wmsInventoryPlan.setCreateTime(DateUtils.getNowDate());
        wmsInventoryPlanMapper.insertWmsInventoryPlan(wmsInventoryPlan);
        return AjaxResult.success();
    }

    /**
     * 修改盘点计划
     *
     * @param wmsInventoryPlan 盘点计划
     * @return 结果
     */
    @Override
    public int updateWmsInventoryPlan(WmsInventoryPlan wmsInventoryPlan) {
        wmsInventoryPlan.setUpdateTime(DateUtils.getNowDate());
        return wmsInventoryPlanMapper.updateWmsInventoryPlan(wmsInventoryPlan);
    }

    /**
     * 批量删除盘点计划
     *
     * @param ids 需要删除的盘点计划主键
     * @return 结果
     */
    @Override
    public int deleteWmsInventoryPlanByIds(String[] ids) {
        return wmsInventoryPlanMapper.deleteWmsInventoryPlanByIds(ids);
    }

    /**
     * 删除盘点计划信息
     *
     * @param id 盘点计划主键
     * @return 结果
     */
    @Override
    public int deleteWmsInventoryPlanById(String id) {
        return wmsInventoryPlanMapper.deleteWmsInventoryPlanById(id);
    }

    /**
     * 查询当天盘点计划数量
     *
     * @return int
     */
    @Override
    public int selectCountByToday() {
        return wmsInventoryPlanMapper.selectCountByToday();
    }

    /**
     * 盘点作业
     *
     * @param id id
     * @return {@link AjaxResult}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult doWork(String id) {
        WmsInventoryPlan wmsInventoryPlan = wmsInventoryPlanMapper.selectWmsInventoryPlanById(id);
        if (null == wmsInventoryPlan) {
            return AjaxResult.error("盘点计划不存在");
        }

        String warehouseIds = wmsInventoryPlan.getWarehouseIds();
        String storageStatus = wmsInventoryPlan.getStorageStatus();
        List<String> warehouseList = Arrays.stream(warehouseIds.split(",")).collect(Collectors.toList());
        List<String> storageStatusList = Arrays.stream(storageStatus.split(",")).collect(Collectors.toList());
        AjaxResult ajaxResult = checkStorageStatus(warehouseList, storageStatusList);
        if (!ajaxResult.isSuccess()) {
            return ajaxResult;
        }

        // 查询需要盘点的库存
        List<WmsItemStorageDetail> wmsItemStorageDetails = wmsItemStorageDetailService.selectByInventoryPlan(warehouseList, storageStatusList);
        if (wmsItemStorageDetails.size() == 0) {
            return AjaxResult.error("没有需要盘点的库存");
        }

        // 生成盘点任务
        wmsInventoryDetailService.generateInventoryTask(wmsItemStorageDetails, wmsInventoryPlan);

        // 更新库存状态为盘点中
        wmsInventoryPlan.setStatus(InventoryPlanStatus.INVENTORYING.getCode());
        wmsInventoryPlan.setUpdateBy(SecurityUtils.getUserId());
        wmsInventoryPlan.setUpdateTime(DateUtils.getNowDate());
        wmsInventoryPlan.setStartTime(DateUtils.getNowDate());
        wmsInventoryPlanMapper.updateWmsInventoryPlan(wmsInventoryPlan);

        return AjaxResult.success();
    }

    /**
     * 作业完成
     *
     * @param id id
     * @return {@link AjaxResult}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult workFinish(String id) {

        WmsInventoryPlan wmsInventoryPlan = wmsInventoryPlanMapper.selectWmsInventoryPlanById(id);
        if (null == wmsInventoryPlan) {
            return AjaxResult.error("盘点计划不存在");
        }
        if (!wmsInventoryPlan.getStatus().equals(InventoryPlanStatus.INVENTORYING.getCode())) {
            return AjaxResult.error("盘点计划状态不是盘点中, 不能完成");
        }
        // 查询没有盘点的库存
        List<WmsInventoryDetail> details = wmsInventoryDetailService.selectNoCountedPlanId(wmsInventoryPlan.getId());
        if (details.size() > 0) {
            return AjaxResult.error("存在未盘点的库存, 不能完成");
        }
        // 更新盘点计划状态
        wmsInventoryPlan.setStatus(InventoryPlanStatus.APPROVING.getCode());
        wmsInventoryPlan.setUpdateBy(SecurityUtils.getUserId());
        wmsInventoryPlan.setUpdateTime(DateUtils.getNowDate());
        wmsInventoryPlan.setEndTime(DateUtils.getNowDate());
        wmsInventoryPlanMapper.updateWmsInventoryPlan(wmsInventoryPlan);

        return AjaxResult.success();
    }

    /**
     * 管理中心审核完成
     *
     * @param inventoryAuditDto 存货审计dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reviewComplete(InventoryAuditDto inventoryAuditDto) {

        // 查询盘点计划
        WmsInventoryPlan wmsInventoryPlan = this.selectWmsInventoryPlanById(inventoryAuditDto.getInventoryId());
        if (null == wmsInventoryPlan) {
            throw new BusinessException("盘点计划不存在");
        }

        List<WmsInventoryDetail> wmsInventoryDetails = wmsInventoryDetailService.selectDetailByPlanId(wmsInventoryPlan.getId());
        if (null == wmsInventoryDetails || wmsInventoryDetails.size() == 0) {
            throw new BusinessException("未查询到盘点计划库存明细");
        }

        // 判断审核状态
        if (inventoryAuditDto.getStatus().equals(InventoryPlanStatus.PASSED.getCode())) {
            // 审核通过
            this.auditPassed(wmsInventoryPlan, wmsInventoryDetails);
        } else if (inventoryAuditDto.getStatus().equals(InventoryPlanStatus.NOT_PASSED.getCode())) {
            // 审核不通过
            this.auditFailed(wmsInventoryPlan);
        } else {
            throw new BusinessException("审核状态不正确");
        }

        // 更新盘点计划状态
        wmsInventoryPlan.setStatus(inventoryAuditDto.getStatus());
        wmsInventoryPlan.setReviewerId(inventoryAuditDto.getReviewerId());
        wmsInventoryPlan.setReviewedBy(inventoryAuditDto.getReviewedBy());
        this.updateWmsInventoryPlan(wmsInventoryPlan);

    }


    /**
     * 检查库存状态
     *
     * @param warehouseList     仓库列表
     * @param storageStatusList 存储状态列表
     * @return {@link AjaxResult}
     */
    public AjaxResult checkStorageStatus(List<String> warehouseList, List<String> storageStatusList) {

        List<WmsItemStorageDetail> wmsItemStorageDetails = wmsItemStorageDetailService.checkStorageStatus(warehouseList, storageStatusList);

        if (wmsItemStorageDetails.size() > 0) {
            String warehouseNames = wmsItemStorageDetails.stream().map(WmsItemStorageDetail::getWarehouseName).collect(Collectors.joining(","));
            String msg = "{} 存在作业中的物料请先处理";
            return AjaxResult.error(StringUtils.format(msg, warehouseNames));
        }
        return AjaxResult.success();
    }


    /**
     * 审核通过
     *
     * @param wmsInventoryPlan    wms库存计划
     * @param wmsInventoryDetails wms库存详细信息
     */
    private void auditPassed(WmsInventoryPlan wmsInventoryPlan, List<WmsInventoryDetail> wmsInventoryDetails) {

        // 更新异常状态的库存的作业状态为正常
        wmsItemStorageDetailService.updateAbnormalItem(wmsInventoryPlan.getId());

        // 过滤出有差异数, 或者是增补的库存
        List<WmsInventoryDetail> details = wmsInventoryDetails.stream().filter(wmsInventoryDetail -> wmsInventoryDetail.getIsAdd().equals(Constants.YES) || wmsInventoryDetail.getDiffQuantity().compareTo(BigDecimal.ZERO) != 0).collect(Collectors.toList());
        if (details.size() > 0) {
            details.forEach(detail -> storageManager.inventoryCount(new InventoryPlanDto(wmsInventoryPlan, detail)));
        }
        // 回退正常库存关联类型, 关联ID
        WmsItemStorageDetail storageDetail = new WmsItemStorageDetail();
        storageDetail.setRelateId(wmsInventoryPlan.getId());
        storageDetail.setRelateType(RelateType.INVENTORY.getCode());
        wmsItemStorageDetailService.updateRelateType(storageDetail);
    }

    /**
     * 审核不通过
     *
     * @param wmsInventoryPlan    wms库存计划
     * @param wmsInventoryDetails wms库存详细信息
     */
    private void auditFailed(WmsInventoryPlan wmsInventoryPlan) {
        // 退回库存关联类型, 关联ID
        WmsItemStorageDetail storageDetail = new WmsItemStorageDetail();
        storageDetail.setRelateId(wmsInventoryPlan.getId());
        storageDetail.setRelateType(RelateType.INVENTORY.getCode());
        wmsItemStorageDetailService.updateRelateType(storageDetail);
    }

}
