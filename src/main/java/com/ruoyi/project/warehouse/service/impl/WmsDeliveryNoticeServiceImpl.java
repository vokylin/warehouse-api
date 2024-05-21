package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.enums.*;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.component.CodeService;
import com.ruoyi.project.common.component.DeliveryNoticeManager;
import com.ruoyi.project.warehouse.domain.*;
import com.ruoyi.project.warehouse.mapper.*;
import com.ruoyi.project.warehouse.service.IWmsDeliveryNoticeService;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 出库通知Service业务层处理
 *
 * @author Rem
 * @date 2023-04-12
 */
@Service
@Slf4j
public class WmsDeliveryNoticeServiceImpl implements IWmsDeliveryNoticeService {
    @Autowired
    private WmsDeliveryNoticeMapper wmsDeliveryNoticeMapper;

    @Autowired
    private WmsItemStorageDetailMapper wmsItemStorageDetailMapper;

    @Autowired
    private WmsWorkNoticeMapper wmsWorkNoticeMapper;

    @Autowired
    private WmsPickingWorkNoticeDetailMapper wmsPickingWorkNoticeDetailMapper;

    @Autowired
    private CodeService codeService;

    @Autowired
    private WmsDeliveryNoticeDetailMapper wmsDeliveryNoticeDetailMapper;

    @Autowired
    private DeliveryNoticeManager deliveryNoticeManager;


    /**
     * 查询出库通知
     *
     * @param id 出库通知主键
     * @return 出库通知
     */
    @Override
    public WmsDeliveryNotice selectWmsDeliveryNoticeById(String id) {
        return wmsDeliveryNoticeMapper.selectWmsDeliveryNoticeById(id);
    }

    /**
     * 查询出库通知列表
     *
     * @param wmsDeliveryNotice 出库通知
     * @return 出库通知
     */
    @Override
    public List<WmsDeliveryNotice> selectWmsDeliveryNoticeList(WmsDeliveryNotice wmsDeliveryNotice) {
        return wmsDeliveryNoticeMapper.selectWmsDeliveryNoticeList(wmsDeliveryNotice);
    }

    /**
     * 新增出库通知
     *
     * @param wmsDeliveryNotice 出库通知
     * @return 结果
     */
    @Override
    public int insertWmsDeliveryNotice(WmsDeliveryNotice wmsDeliveryNotice) {
        wmsDeliveryNotice.setCreateTime(DateUtils.getNowDate());
        return wmsDeliveryNoticeMapper.insertWmsDeliveryNotice(wmsDeliveryNotice);
    }

    /**
     * 修改出库通知
     *
     * @param wmsDeliveryNotice 出库通知
     * @return 结果
     */
    @Override
    public int updateWmsDeliveryNotice(WmsDeliveryNotice wmsDeliveryNotice) {
        wmsDeliveryNotice.setUpdateTime(DateUtils.getNowDate());
        return wmsDeliveryNoticeMapper.updateWmsDeliveryNotice(wmsDeliveryNotice);
    }

    /**
     * 批量删除出库通知
     *
     * @param ids 需要删除的出库通知主键
     * @return 结果
     */
    @Override
    public int deleteWmsDeliveryNoticeByIds(String[] ids) {
        return wmsDeliveryNoticeMapper.deleteWmsDeliveryNoticeByIds(ids);
    }

    /**
     * 删除出库通知信息
     *
     * @param id 出库通知主键
     * @return 结果
     */
    @Override
    public int deleteWmsDeliveryNoticeById(String id) {
        return wmsDeliveryNoticeMapper.deleteWmsDeliveryNoticeById(id);
    }

    /**
     * 激活出库通知
     *
     * @param wmsDeliveryNotice wms交货通知
     * @return int
     */
    @Override
    public AjaxResult active(WmsDeliveryNotice wmsDeliveryNotice) {
        WmsDeliveryNotice deliveryNotice = wmsDeliveryNoticeMapper.selectWmsDeliveryNoticeById(wmsDeliveryNotice.getId());
        if (deliveryNotice == null) {
            return AjaxResult.error("出库通知不存在");
        }
        if (!deliveryNotice.getStatus().equals(DeliveryNoticeStatus.WAIT_ACTIVE.getCode())) {
            return AjaxResult.error("只有待激活的出库通知才能激活");
        }
        wmsDeliveryNotice.setUpdateTime(DateUtils.getNowDate());
        wmsDeliveryNotice.setUpdateBy(SecurityUtils.getUserId());
        wmsDeliveryNotice.setStatus(DeliveryNoticeStatus.WORKING.getCode());
        wmsDeliveryNoticeMapper.updateWmsDeliveryNotice(wmsDeliveryNotice);
        return AjaxResult.success();
    }

    /**
     * 取消激活出库通知
     *
     * @param wmsDeliveryNotice wms交货通知
     * @return int
     */
    @Override
    public AjaxResult inactive(WmsDeliveryNotice wmsDeliveryNotice) {

        List<WmsDeliveryNoticeDetail> wmsDeliveryNoticeDetails = wmsDeliveryNoticeDetailMapper.selectByNoticeId(wmsDeliveryNotice.getId());
        for (WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail : wmsDeliveryNoticeDetails) {
            if (wmsDeliveryNoticeDetail.getAllotQuantity() != null && wmsDeliveryNoticeDetail.getAllotQuantity().compareTo(BigDecimal.ZERO) > 0) {
                return AjaxResult.error("该出库通知已分配库存，无法取消激活");
            }
            if (wmsDeliveryNoticeDetail.getWorkQuantity() != null && wmsDeliveryNoticeDetail.getWorkQuantity().compareTo(BigDecimal.ZERO) > 0) {
                return AjaxResult.error("该出库通知已生成拣货作业，无法取消激活");
            }

            if (wmsDeliveryNoticeDetail.getWorkFinishQuantity() != null && wmsDeliveryNoticeDetail.getWorkFinishQuantity().compareTo(BigDecimal.ZERO) > 0) {
                return AjaxResult.error("该出库通知已完成拣货作业，无法取消激活");
            }
        }
        wmsDeliveryNotice.setUpdateTime(DateUtils.getNowDate());
        wmsDeliveryNotice.setUpdateBy(SecurityUtils.getUserId());
        wmsDeliveryNoticeMapper.inactive(wmsDeliveryNotice);
        return AjaxResult.success();
    }

    /**
     * 生成拣货作业
     *
     * @param wmsDeliveryNotice wms交货通知
     * @return int
     */
    @Override
    @Synchronized
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult pickWork(WmsDeliveryNotice wmsDeliveryNotice) {

        WmsDeliveryNotice deliveryNotice = wmsDeliveryNoticeMapper.selectWmsDeliveryNoticeById(wmsDeliveryNotice.getId());
        if (deliveryNotice == null) {
            return AjaxResult.error("出库通知不存在");
        }
        if (!deliveryNotice.getStatus().equals(DeliveryNoticeStatus.WORKING.getCode())) {
            return AjaxResult.error("只有作业中的出库通知才能生成拣货作业");
        }

        WmsItemStorageDetail searchInfo = new WmsItemStorageDetail();
        searchInfo.setRelateId(wmsDeliveryNotice.getId());
        searchInfo.setWorkStatus(WorkStatus.WORKING.getCode());
        searchInfo.setRelateType(RelateType.ALLOCATION.getCode());
        // 查询已分配的库存明细
        List<WmsItemStorageDetail> wmsItemStorageDetails = wmsItemStorageDetailMapper.selectByDeliveryNoticeId(searchInfo);

        if (wmsItemStorageDetails.isEmpty()) {
            return AjaxResult.error("请先分配物料");
        }

        // 拣货作业通知
        WmsWorkNotice wmsWorkNotice = new WmsWorkNotice();
        wmsWorkNotice.setId(codeService.generatePickWorkCode());
        wmsWorkNotice.setNoticeId(wmsDeliveryNotice.getId());
        wmsWorkNotice.setCompanyId(wmsDeliveryNotice.getCompanyId());
        wmsWorkNotice.setType(WorkType.PICK.getCode());
        wmsWorkNotice.setReceiveSendType(wmsDeliveryNotice.getReceiveSendType());
        wmsWorkNotice.setStatus(PickWorkNoticeStatus.NORMAL.getCode());
        wmsWorkNotice.setCreateBy(SecurityUtils.getUserId());
        wmsWorkNotice.setCreateTime(DateUtils.getNowDate());
        wmsWorkNoticeMapper.insertWmsWorkNotice(wmsWorkNotice);

        WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail1 = new WmsDeliveryNoticeDetail();
        wmsDeliveryNoticeDetail1.setDeliveryNoticeId(wmsDeliveryNotice.getId());
        List<WmsDeliveryNoticeDetail> wmsDeliveryNoticeDetails = wmsDeliveryNoticeDetailMapper.selectWmsDeliveryNoticeDetailList(wmsDeliveryNoticeDetail1);
        log.info("生成作业通知-> 出库通知明细 wmsDeliveryNoticeDetails:{}", wmsDeliveryNoticeDetails);

        Map<String, String> collect = wmsDeliveryNoticeDetails.stream().collect(Collectors.toMap(WmsDeliveryNoticeDetail::getItemCode, WmsDeliveryNoticeDetail::getId, (k1, k2) -> k1));
        // 拣货作业通知明细
        for (WmsItemStorageDetail wmsItemStorageDetail : wmsItemStorageDetails) {
            String deliveryDetailId = collect.get(wmsItemStorageDetail.getItemCode());
            WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail = new WmsPickingWorkNoticeDetail();
            wmsPickingWorkNoticeDetail.setId(IdUtils.fastSimpleUUID());
            wmsPickingWorkNoticeDetail.setWorkNoticeId(wmsWorkNotice.getId());
            wmsPickingWorkNoticeDetail.setNoticeDetailId(deliveryDetailId);
            wmsPickingWorkNoticeDetail.setItemCode(wmsItemStorageDetail.getItemCode());
            wmsPickingWorkNoticeDetail.setBatchNo(wmsItemStorageDetail.getBatchNo());
            wmsPickingWorkNoticeDetail.setSourceLocationId(wmsItemStorageDetail.getLocationId());
            wmsPickingWorkNoticeDetail.setSourceWarehouseId(wmsItemStorageDetail.getWarehouseId());
            wmsPickingWorkNoticeDetail.setQuantity(wmsItemStorageDetail.getQuantity());
            wmsPickingWorkNoticeDetail.setAllotQuantity(BigDecimal.ZERO);
            wmsPickingWorkNoticeDetail.setLackQuantity(BigDecimal.ZERO);
            wmsPickingWorkNoticeDetail.setCreateBy(SecurityUtils.getUserId());
            wmsPickingWorkNoticeDetail.setCreateTime(DateUtils.getNowDate());
            wmsPickingWorkNoticeDetailMapper.insertWmsPickingWorkNoticeDetail(wmsPickingWorkNoticeDetail);

            // 更新库存明细状态
            wmsItemStorageDetail.setWorkStatus(WorkStatus.WORKING.getCode());
            wmsItemStorageDetail.setRelateId(wmsWorkNotice.getId());
            wmsItemStorageDetail.setRelateType(RelateType.PICKING.getCode());
            wmsItemStorageDetailMapper.updateWmsItemStorageDetail(wmsItemStorageDetail);

            // 更新出库通知详情数量
            WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail = wmsDeliveryNoticeDetailMapper.selectWmsDeliveryNoticeDetailById(deliveryDetailId);
            wmsDeliveryNoticeDetail.setWorkQuantity(wmsDeliveryNoticeDetail.getWorkQuantity().add(wmsItemStorageDetail.getQuantity()));
            wmsDeliveryNoticeDetail.setAllotQuantity(wmsDeliveryNoticeDetail.getAllotQuantity().subtract(wmsItemStorageDetail.getQuantity()));
            wmsDeliveryNoticeDetailMapper.updateWmsDeliveryNoticeDetail(wmsDeliveryNoticeDetail);
        }

        return AjaxResult.success();
    }


    /**
     * 出库确认信息
     *
     * @param id id
     * @return {@link DeliveryNoticeInfo}
     */
    @Override
    public DeliveryNoticeInfo deliveryConfirmInfo(String id) {
        return wmsDeliveryNoticeMapper.deliveryConfirmInfo(id);
    }

    @Override
    public int selectWmsDeliveryNoticeCountByToday() {
        return wmsDeliveryNoticeMapper.selectWmsDeliveryNoticeCountByToday();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult invalid(WmsDeliveryNotice wmsDeliveryNotice) {
        WmsDeliveryNotice deliveryNotice = wmsDeliveryNoticeMapper.selectWmsDeliveryNoticeById(wmsDeliveryNotice.getId());
        if (deliveryNotice == null) {
            return AjaxResult.error("出库通知不存在");
        }
        if (!deliveryNotice.getStatus().equals(DeliveryNoticeStatus.WAIT_ACTIVE.getCode())) {
            return AjaxResult.error("只有待激活的出库通知才能作废");
        }
        wmsDeliveryNotice.setUpdateTime(DateUtils.getNowDate());
        wmsDeliveryNotice.setUpdateBy(SecurityUtils.getUserId());
        wmsDeliveryNotice.setStatus(DeliveryNoticeStatus.CANCEL.getCode());
        wmsDeliveryNoticeMapper.updateWmsDeliveryNotice(wmsDeliveryNotice);
        try {
            deliveryNoticeManager.invalidNotice(wmsDeliveryNotice);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success();
    }

}
