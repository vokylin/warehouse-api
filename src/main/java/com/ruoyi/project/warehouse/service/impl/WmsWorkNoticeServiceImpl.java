package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.PickWorkNoticeStatus;
import com.ruoyi.common.enums.RefundWorkStatus;
import com.ruoyi.common.enums.RelateType;
import com.ruoyi.common.enums.WorkStatus;
import com.ruoyi.common.exception.business.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.component.StorageManager;
import com.ruoyi.project.warehouse.domain.*;
import com.ruoyi.project.warehouse.mapper.WmsItemStorageDetailMapper;
import com.ruoyi.project.warehouse.mapper.WmsPickingWorkNoticeDetailMapper;
import com.ruoyi.project.warehouse.mapper.WmsWorkNoticeMapper;
import com.ruoyi.project.warehouse.service.IWmsDeliveryNoticeDetailService;
import com.ruoyi.project.warehouse.service.IWmsWorkNoticeAllotService;
import com.ruoyi.project.warehouse.service.IWmsWorkNoticeDetailService;
import com.ruoyi.project.warehouse.service.IWmsWorkNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 作业通知Service业务层处理
 *
 * @author Rem
 * @date 2023-04-07
 */
@Service
public class WmsWorkNoticeServiceImpl implements IWmsWorkNoticeService {

    @Autowired
    private WmsWorkNoticeMapper wmsWorkNoticeMapper;

    @Autowired
    private IWmsWorkNoticeDetailService wmsWorkNoticeDetailService;

    @Autowired
    private IWmsWorkNoticeAllotService wmsWorkNoticeAllotService;

    @Autowired
    private WmsPickingWorkNoticeDetailMapper wmsPickingWorkNoticeDetailMapper;

    @Autowired
    private WmsItemStorageDetailMapper wmsItemStorageDetailMapper;

    @Autowired
    private IWmsDeliveryNoticeDetailService wmsDeliveryNoticeDetailService;

    @Autowired
    private StorageManager storageManager;

    /**
     * 查询作业通知
     *
     * @param id 作业通知主键
     * @return 作业通知
     */
    @Override
    public WmsWorkNotice selectWmsWorkNoticeById(String id) {
        return wmsWorkNoticeMapper.selectWmsWorkNoticeById(id);
    }

    /**
     * 查询作业通知列表
     *
     * @param wmsWorkNotice 作业通知
     * @return 作业通知
     */
    @Override
    public List<WmsWorkNotice> selectWmsWorkNoticeList(WmsWorkNotice wmsWorkNotice) {
        return wmsWorkNoticeMapper.selectWmsWorkNoticeList(wmsWorkNotice);
    }

    /**
     * 新增作业通知
     *
     * @param wmsWorkNotice 作业通知
     * @return 结果
     */
    @Override
    public int insertWmsWorkNotice(WmsWorkNotice wmsWorkNotice) {
        wmsWorkNotice.setCreateTime(DateUtils.getNowDate());
        return wmsWorkNoticeMapper.insertWmsWorkNotice(wmsWorkNotice);
    }

    /**
     * 修改作业通知
     *
     * @param wmsWorkNotice 作业通知
     * @return 结果
     */
    @Override
    public int updateWmsWorkNotice(WmsWorkNotice wmsWorkNotice) {
        wmsWorkNotice.setUpdateTime(DateUtils.getNowDate());
        return wmsWorkNoticeMapper.updateWmsWorkNotice(wmsWorkNotice);
    }

    /**
     * 批量删除作业通知
     *
     * @param ids 需要删除的作业通知主键
     * @return 结果
     */
    @Override
    public int deleteWmsWorkNoticeByIds(String[] ids) {
        return wmsWorkNoticeMapper.deleteWmsWorkNoticeByIds(ids);
    }

    /**
     * 删除作业通知信息
     *
     * @param id 作业通知主键
     * @return 结果
     */
    @Override
    public int deleteWmsWorkNoticeById(String id) {
        return wmsWorkNoticeMapper.deleteWmsWorkNoticeById(id);
    }

    /**
     * 查询今日作业通知数量
     *
     * @return int
     */
    @Override
    public int selectWmsWorkNoticeCountByToday(Integer workType) {
        return wmsWorkNoticeMapper.selectWmsWorkNoticeCountByToday(workType);
    }


    /**
     * 退货
     *
     * @param ids id
     * @return int
     */
    @Override
    public int returns(String[] ids) {
        return Constants.SUCCESS_CODE;
    }


    /**
     * 作业完成
     *
     * @param wmsWorkNotice wms交货通知
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult completeWork(WmsWorkNotice wmsWorkNotice) {

        // 查询作业通知
        WmsWorkNotice workNotice = wmsWorkNoticeMapper.selectWmsWorkNoticeById(wmsWorkNotice.getId());
        if (workNotice == null) {
            return AjaxResult.error("作业通知不存在");
        }
        if (!workNotice.getStatus().equals(PickWorkNoticeStatus.NORMAL.getCode())) {
            return AjaxResult.error("作业通知状态异常");
        }

        // 查询作业通知明细
        List<WmsPickingWorkNoticeDetail> wmsPickingWorkNoticeDetails = wmsPickingWorkNoticeDetailMapper.selectByWorkNoticeId(wmsWorkNotice.getId());
        for (WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail : wmsPickingWorkNoticeDetails) {
            // 更新作业通知明细
            wmsPickingWorkNoticeDetail.setAllotQuantity(wmsPickingWorkNoticeDetail.getQuantity());
            wmsPickingWorkNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
            wmsPickingWorkNoticeDetail.setUpdateTime(DateUtils.getNowDate());
            wmsPickingWorkNoticeDetailMapper.updateWmsPickingWorkNoticeDetail(wmsPickingWorkNoticeDetail);

            // 来源库存信息
            WmsItemStorageDetail selectDetail = new WmsItemStorageDetail();
            selectDetail.setBatchNo(wmsPickingWorkNoticeDetail.getBatchNo());
            selectDetail.setItemCode(wmsPickingWorkNoticeDetail.getItemCode());
            selectDetail.setRelateType(RelateType.PICKING.getCode());
            selectDetail.setWorkStatus(WorkStatus.WORKING.getCode());
            selectDetail.setLocationId(wmsPickingWorkNoticeDetail.getSourceLocationId());
            selectDetail.setRelateId(wmsPickingWorkNoticeDetail.getWorkNoticeId());
            WmsItemStorageDetail sourceItemStorageDetail = wmsItemStorageDetailMapper.selectWmsItemStorageDetailByBatchNo(selectDetail);
            if (null == sourceItemStorageDetail) {
                throw new BusinessException("来源库存不存在");
            }

            // 查询同一货位的库存中是否有相同批次的待发货物料
            selectDetail.setRelateId(wmsPickingWorkNoticeDetail.getDeliveryNoticeId());
            selectDetail.setRelateType(RelateType.DELIVERY.getCode());
            WmsItemStorageDetail wmsItemStorageDetailInfo = wmsItemStorageDetailMapper.selectWmsItemStorageDetailByBatchNo(selectDetail);
            if (null != wmsItemStorageDetailInfo) {
                // 如果存在 更新数量
                WmsItemStorageDetail updateItemStorageDetail = new WmsItemStorageDetail();
                updateItemStorageDetail.setId(wmsItemStorageDetailInfo.getId());
                updateItemStorageDetail.setQuantity(wmsItemStorageDetailInfo.getQuantity().add(wmsPickingWorkNoticeDetail.getQuantity()));
                updateItemStorageDetail.setUpdateBy(SecurityUtils.getUserId());
                updateItemStorageDetail.setUpdateTime(DateUtils.getNowDate());
                wmsItemStorageDetailMapper.updateWmsItemStorageDetail(updateItemStorageDetail);

                // 删除来源库存明细
                wmsItemStorageDetailMapper.deleteWmsItemStorageDetailById(sourceItemStorageDetail.getId());
            } else {
                WmsItemStorageDetail itemStorageDetail = new WmsItemStorageDetail();
                itemStorageDetail.setId(sourceItemStorageDetail.getId());
                itemStorageDetail.setRelateId(wmsWorkNotice.getNoticeId());
                itemStorageDetail.setRelateType(RelateType.DELIVERY.getCode());
                itemStorageDetail.setUpdateBy(SecurityUtils.getUserId());
                itemStorageDetail.setUpdateTime(DateUtils.getNowDate());
                wmsItemStorageDetailMapper.updateWmsItemStorageDetail(itemStorageDetail);
            }


            // 更新出库通知明细
            WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail = wmsDeliveryNoticeDetailService.selectWmsDeliveryNoticeDetailById(wmsPickingWorkNoticeDetail.getNoticeDetailId());
            wmsDeliveryNoticeDetail.setWorkFinishQuantity(wmsDeliveryNoticeDetail.getWorkFinishQuantity().add(wmsPickingWorkNoticeDetail.getQuantity()));
            wmsDeliveryNoticeDetail.setWorkQuantity(wmsDeliveryNoticeDetail.getWorkQuantity().subtract(wmsPickingWorkNoticeDetail.getQuantity()));
            wmsDeliveryNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
            wmsDeliveryNoticeDetail.setUpdateTime(DateUtils.getNowDate());
            wmsDeliveryNoticeDetailService.updateWmsDeliveryNoticeDetail(wmsDeliveryNoticeDetail);

        }

        // 更新作业通知状态
        wmsWorkNotice.setStatus(PickWorkNoticeStatus.WORK_COMPLETE.getCode());
        wmsWorkNotice.setUpdateBy(SecurityUtils.getUserId());
        wmsWorkNotice.setUpdateTime(DateUtils.getNowDate());
        wmsWorkNotice.setWorkStartTime(DateUtils.getNowDate());
        wmsWorkNotice.setWorkEndTime(DateUtils.getNowDate());
        wmsWorkNoticeMapper.updateWmsWorkNotice(wmsWorkNotice);

        return AjaxResult.success();
    }

    /**
     * 取消作业
     *
     * @param wmsWorkNotice wms交货通知
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult cancelWork(WmsWorkNotice wmsWorkNotice) throws Exception {

        // 查询作业通知
        WmsWorkNotice workNotice = wmsWorkNoticeMapper.selectWmsWorkNoticeById(wmsWorkNotice.getId());
        if (workNotice == null) {
            return AjaxResult.error("作业通知不存在");
        }
        if (!workNotice.getStatus().equals(PickWorkNoticeStatus.NORMAL.getCode())) {
            return AjaxResult.error("只有待分配状态的作业通知才能取消");
        }

        // 查询作业通知明细
        List<WmsPickingWorkNoticeDetail> wmsPickingWorkNoticeDetails = wmsPickingWorkNoticeDetailMapper.selectByWorkNoticeId(wmsWorkNotice.getId());
        if (wmsPickingWorkNoticeDetails.isEmpty()) {
            return AjaxResult.error("拣货作业通知明细不存在");
        }
        for (WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail : wmsPickingWorkNoticeDetails) {
            // 取消分配的库存
            storageManager.cancelAllot(new ItemStorageDetailDto(wmsPickingWorkNoticeDetail, wmsPickingWorkNoticeDetail.getSourceLocationId()));

            // 更新出库通知明细数量
            WmsDeliveryNoticeDetail updateNoticeDetail = new WmsDeliveryNoticeDetail();
            updateNoticeDetail.setId(wmsPickingWorkNoticeDetail.getNoticeDetailId());
            updateNoticeDetail.setAllotQuantity(BigDecimal.ZERO);
            updateNoticeDetail.setWorkQuantity(BigDecimal.ZERO);
            updateNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
            updateNoticeDetail.setUpdateTime(DateUtils.getNowDate());
            wmsDeliveryNoticeDetailService.updateWmsDeliveryNoticeDetail(updateNoticeDetail);
        }

        // 更新作业通知状态
        wmsWorkNotice.setStatus(PickWorkNoticeStatus.CANCEL.getCode());
        wmsWorkNotice.setUpdateBy(SecurityUtils.getUserId());
        wmsWorkNotice.setUpdateTime(DateUtils.getNowDate());
        wmsWorkNotice.setWorkStartTime(DateUtils.getNowDate());
        wmsWorkNotice.setWorkEndTime(DateUtils.getNowDate());
        wmsWorkNoticeMapper.updateWmsWorkNotice(wmsWorkNotice);
        return AjaxResult.success();
    }

    /**
     * 登记完成
     *
     * @param wmsWorkNotice wms交货通知
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult registerComplete(WmsWorkNotice wmsWorkNotice) throws Exception {

        // 查询作业通知
        WmsWorkNotice workNotice = wmsWorkNoticeMapper.selectWmsWorkNoticeById(wmsWorkNotice.getId());
        if (workNotice == null) {
            return AjaxResult.error("作业通知不存在");
        }
        if (!workNotice.getStatus().equals(PickWorkNoticeStatus.REGISTRATION.getCode())) {
            return AjaxResult.error("只有缺货登记中的作业通知才能登记完成");
        }

        // 查询作业通知明细
        List<WmsPickingWorkNoticeDetail> wmsPickingWorkNoticeDetails = wmsPickingWorkNoticeDetailMapper.selectByWorkNoticeId(wmsWorkNotice.getId());
        for (WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail : wmsPickingWorkNoticeDetails) {

            // 如果拣货数量大于缺货数量,则回退拣货的库存
            if (wmsPickingWorkNoticeDetail.getQuantity().compareTo(wmsPickingWorkNoticeDetail.getLackQuantity()) > 0) {
                // 取消分配的库存
                storageManager.cancelAllot(new ItemStorageDetailDto(wmsPickingWorkNoticeDetail, wmsPickingWorkNoticeDetail.getSourceLocationId()));
            }

            // 更新出库通知明细数量
            WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail = wmsDeliveryNoticeDetailService.selectWmsDeliveryNoticeDetailById(wmsPickingWorkNoticeDetail.getNoticeDetailId());
            wmsDeliveryNoticeDetail.setWorkQuantity(wmsDeliveryNoticeDetail.getWorkQuantity().subtract(wmsPickingWorkNoticeDetail.getQuantity()));
            wmsDeliveryNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
            wmsDeliveryNoticeDetail.setUpdateTime(DateUtils.getNowDate());
            wmsDeliveryNoticeDetailService.updateWmsDeliveryNoticeDetail(wmsDeliveryNoticeDetail);
        }

        // 更新作业通知状态
        wmsWorkNotice.setStatus(PickWorkNoticeStatus.CANCEL.getCode());
        wmsWorkNotice.setUpdateBy(SecurityUtils.getUserId());
        wmsWorkNotice.setUpdateTime(DateUtils.getNowDate());
        wmsWorkNotice.setWorkStartTime(DateUtils.getNowDate());
        wmsWorkNotice.setWorkEndTime(DateUtils.getNowDate());
        wmsWorkNoticeMapper.updateWmsWorkNotice(wmsWorkNotice);
        return AjaxResult.success();
    }

    /**
     * 根据关联id查询作业通知
     *
     * @param id id
     * @return {@link WmsWorkNotice}
     */
    @Override
    public List<WmsWorkNotice> selectWmsWorkNoticeByRelateId(String id) {
        return wmsWorkNoticeMapper.selectWmsWorkNoticeByRelateId(id);
    }

    /**
     * 退货完成
     *
     * @param wmsWorkNotice 世界媒体峰会工作通知
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult returnComplete(WmsWorkNotice wmsWorkNotice) {

        // 查询作业通知
        WmsWorkNotice workNotice = wmsWorkNoticeMapper.selectWmsWorkNoticeById(wmsWorkNotice.getId());
        if (workNotice == null) {
            return AjaxResult.error("作业通知不存在");
        }
        if (workNotice.getStatus().equals(PickWorkNoticeStatus.WORK_COMPLETE.getCode())) {
            return AjaxResult.error("作业通知已完成");
        }

        // 查询作业通知明细
        List<WmsWorkNoticeDetail> workNoticeDetails = wmsWorkNoticeDetailService.selectByWorkNoticeId(wmsWorkNotice.getId());

        if (workNoticeDetails.isEmpty()) {
            return AjaxResult.error("作业通知明细为空");
        }
        workNoticeDetails.forEach(wmsWorkNoticeDetailService::returnComplete);
        // 更新分配明细状态
        wmsWorkNoticeAllotService.batchUpdateStatus(wmsWorkNotice.getId(), RefundWorkStatus.REFUNDED.getCode());

        // 更新作业通知状态
        wmsWorkNotice.setStatus(PickWorkNoticeStatus.WORK_COMPLETE.getCode());
        wmsWorkNotice.setWorkEndTime(DateUtils.getNowDate());
        wmsWorkNotice.setUpdateBy(SecurityUtils.getUserId());
        wmsWorkNotice.setUpdateTime(DateUtils.getNowDate());
        wmsWorkNoticeMapper.updateWmsWorkNotice(wmsWorkNotice);

        return AjaxResult.success();
    }

    /**
     * 整单作业
     *
     * @param wmsWorkNotice 世界媒体峰会工作通知
     * @return int
     */
    @Override
    public AjaxResult wholeWork(WmsWorkNotice wmsWorkNotice) {

        // 查询作业通知
        WmsWorkNotice workNotice = wmsWorkNoticeMapper.selectWmsWorkNoticeById(wmsWorkNotice.getId());
        if (workNotice == null) {
            return AjaxResult.error("作业通知不存在");
        }
        if (!workNotice.getStatus().equals(PickWorkNoticeStatus.NORMAL.getCode())) {
            return AjaxResult.error("只有待分配状态的作业通知才能整单作业");
        }

        // 查询作业通知明细
        List<WmsWorkNoticeDetail> wmsWorkNoticeDetails = wmsWorkNoticeDetailService.selectByWorkNoticeId(wmsWorkNotice.getId());
        for (WmsWorkNoticeDetail workNoticeDetail : wmsWorkNoticeDetails) {
            if (workNoticeDetail.getQuantity().compareTo(workNoticeDetail.getAllotQuantity()) != 0) {
                return AjaxResult.error("作业未完成");
            }
        }
        // 更新作业通知状态
        wmsWorkNotice.setStatus(PickWorkNoticeStatus.WORK_COMPLETE.getCode());
        wmsWorkNotice.setUpdateBy(SecurityUtils.getUserId());
        wmsWorkNotice.setUpdateTime(DateUtils.getNowDate());
        wmsWorkNotice.setWorkEndTime(DateUtils.getNowDate());
        wmsWorkNoticeMapper.updateWmsWorkNotice(wmsWorkNotice);
        return AjaxResult.success();
    }

    @Override
    public int updatePickWorkNoticeStatus(WmsWorkNotice wmsWorkNotice) {
        return wmsWorkNoticeMapper.updatePickWorkNoticeStatus(wmsWorkNotice);
    }


}
