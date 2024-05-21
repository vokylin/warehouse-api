package com.ruoyi.project.warehouse.service.impl;


import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.BillOfLoadingStatus;
import com.ruoyi.common.enums.DeliveryNoticeStatus;
import com.ruoyi.common.enums.RelateType;
import com.ruoyi.common.enums.WorkStatus;
import com.ruoyi.common.exception.business.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.component.DeliveryNoticeManager;
import com.ruoyi.project.common.component.StorageManager;
import com.ruoyi.project.common.entity.BaseDetailDTO;
import com.ruoyi.project.warehouse.domain.*;
import com.ruoyi.project.warehouse.mapper.WmsBillOfLoadingMapper;
import com.ruoyi.project.warehouse.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 发运单Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-16
 */
@Service
public class WmsBillOfLoadingServiceImpl implements IWmsBillOfLoadingService {
    @Autowired
    private WmsBillOfLoadingMapper wmsBillOfLoadingMapper;

    @Autowired
    private IWmsDeliveryNoticeDetailService wmsDeliveryNoticeDetailService;

    @Autowired
    private IWmsDeliveryNoticeService wmsDeliveryNoticeService;

    @Autowired
    private StorageManager storageManager;

    @Autowired
    private DeliveryNoticeManager deliveryNoticeManager;

    @Autowired
    private IWmsPickingWorkNoticeDetailService wmsPickingWorkNoticeDetailService;

    @Autowired
    private IWmsItemStorageDetailService wmsItemStorageDetailService;

    /**
     * 查询发运单
     *
     * @param id 发运单主键
     * @return 发运单
     */
    @Override
    public WmsBillOfLoading selectWmsBillOfLoadingById(String id) {
        return wmsBillOfLoadingMapper.selectWmsBillOfLoadingById(id);
    }

    @Override
    public WmsBillOfLoading selectByNoticeId(String noticeId) {
        return wmsBillOfLoadingMapper.selectByNoticeId(noticeId);
    }

    /**
     * 查询发运单列表
     *
     * @param wmsBillOfLoading 发运单
     * @return 发运单
     */
    @Override
    public List<WmsBillOfLoading> selectWmsBillOfLoadingList(WmsBillOfLoading wmsBillOfLoading) {
        return wmsBillOfLoadingMapper.selectWmsBillOfLoadingList(wmsBillOfLoading);
    }

    /**
     * 新增发运单
     *
     * @param wmsBillOfLoading 发运单
     * @return 结果
     */
    @Override
    public int insertWmsBillOfLoading(WmsBillOfLoading wmsBillOfLoading) {
        return wmsBillOfLoadingMapper.insertWmsBillOfLoading(wmsBillOfLoading);
    }

    /**
     * 修改发运单
     *
     * @param wmsBillOfLoading 发运单
     * @return 结果
     */
    @Override
    public int updateWmsBillOfLoading(WmsBillOfLoading wmsBillOfLoading) {
        return wmsBillOfLoadingMapper.updateWmsBillOfLoading(wmsBillOfLoading);
    }

    /**
     * 批量删除发运单
     *
     * @param ids 需要删除的发运单主键
     * @return 结果
     */
    @Override
    public int deleteWmsBillOfLoadingByIds(String[] ids) {
        return wmsBillOfLoadingMapper.deleteWmsBillOfLoadingByIds(ids);
    }

    /**
     * 删除发运单信息
     *
     * @param id 发运单主键
     * @return 结果
     */
    @Override
    public int deleteWmsBillOfLoadingById(String id) {
        return wmsBillOfLoadingMapper.deleteWmsBillOfLoadingById(id);
    }

    /**
     * 查询当天发运单数量
     *
     * @return int
     */
    @Override
    public int selectWmsBillOfLoadingCountByToday() {
        return wmsBillOfLoadingMapper.selectWmsBillOfLoadingCountByToday();
    }

    /**
     * 发运完成
     *
     * @param wmsBillOfLoading wms提单
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int finishWmsBillOfLoading(WmsBillOfLoading wmsBillOfLoading) throws Exception {

        // 更新发运单
        wmsBillOfLoading.setUpdateBy(SecurityUtils.getUserId());
        wmsBillOfLoading.setUpdateDate(DateUtils.getNowDate());
        wmsBillOfLoading.setStatus(BillOfLoadingStatus.FINISHED.getCode());
        wmsBillOfLoadingMapper.updateWmsBillOfLoading(wmsBillOfLoading);

        // 更新发货通知单明细
        List<WmsDeliveryNoticeDetail> wmsDeliveryNoticeDetails = wmsDeliveryNoticeDetailService.selectByNoticeId(wmsBillOfLoading.getDeliveryNoticeId());
        List<BaseDetailDTO> detailDTOList = new ArrayList<>();
        wmsDeliveryNoticeDetails.forEach(wmsDeliveryNoticeDetail -> {
            wmsDeliveryNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
            wmsDeliveryNoticeDetail.setUpdateTime(DateUtils.getNowDate());
            wmsDeliveryNoticeDetail.setDeliveryQuantity(wmsDeliveryNoticeDetail.getWorkFinishQuantity());
            wmsDeliveryNoticeDetailService.updateWmsDeliveryNoticeDetail(wmsDeliveryNoticeDetail);
            // 提前构造通知企通链的内容，在仓库执行完之后，再通知企通链
            buildDeliveryBaseDetail(wmsDeliveryNoticeDetail, detailDTOList, wmsBillOfLoading.getDeliveryNoticeId());
        });

        // 更新发货通知单
        WmsDeliveryNotice wmsDeliveryNotice = new WmsDeliveryNotice();
        wmsDeliveryNotice.setId(wmsBillOfLoading.getDeliveryNoticeId());
        wmsDeliveryNotice.setStatus(DeliveryNoticeStatus.COMPLETE.getCode());
        wmsDeliveryNotice.setUpdateBy(SecurityUtils.getUserId());
        wmsDeliveryNotice.setUpdateTime(DateUtils.getNowDate());
        wmsDeliveryNotice.setDeliveryEndTime(DateUtils.getNowDate());
        wmsDeliveryNoticeService.updateWmsDeliveryNotice(wmsDeliveryNotice);

        // 更新库存
        storageManager.outBound(wmsBillOfLoading.getDeliveryNoticeId());
        // 收货完成
        deliveryNoticeManager.deliveryNoticeFinish(wmsBillOfLoading.getDeliveryNoticeId(), detailDTOList);
        return Constants.SUCCESS_CODE;

    }

    private void buildDeliveryBaseDetail(WmsDeliveryNoticeDetail noticeDetail, List<BaseDetailDTO> detailDTOList, String deliveryNoticeId) {
        // 查询拣货作业明细
        List<WmsPickingWorkNoticeDetail> wmsPickingWorkNoticeDetails = wmsPickingWorkNoticeDetailService.selectDetailByDeliveryNoticeDetailId(noticeDetail.getId());
        if (wmsPickingWorkNoticeDetails.isEmpty()) {
            throw new BusinessException("拣货作业明细不存在");
        }
        Map<String, List<WmsPickingWorkNoticeDetail>> collect = wmsPickingWorkNoticeDetails.stream().collect(Collectors.groupingBy(WmsPickingWorkNoticeDetail::getBatchNo));
        collect.forEach((batchNo, lists) -> {
            WmsPickingWorkNoticeDetail pickingWorkNoticeDetail = lists.get(0);
            WmsItemStorageDetail selectDetail = new WmsItemStorageDetail();
            selectDetail.setBatchNo(pickingWorkNoticeDetail.getBatchNo());
            selectDetail.setItemCode(pickingWorkNoticeDetail.getItemCode());
            selectDetail.setLocationId(pickingWorkNoticeDetail.getSourceLocationId());
            selectDetail.setRelateType(RelateType.DELIVERY.getCode());
            selectDetail.setWorkStatus(WorkStatus.WORKING.getCode());
            selectDetail.setRelateId(deliveryNoticeId);
            WmsItemStorageDetail wmsItemStorageDetailInfo = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(selectDetail);

            BaseDetailDTO baseDetailDTO = new BaseDetailDTO();
            baseDetailDTO.setItemCode(noticeDetail.getItemCode());
            baseDetailDTO.setQuantity(lists.stream().map(WmsPickingWorkNoticeDetail::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add));
            baseDetailDTO.setItemName(noticeDetail.getItemName());
            baseDetailDTO.setBatchNo(pickingWorkNoticeDetail.getBatchNo());
            baseDetailDTO.setItemUnit(noticeDetail.getItemUnit());
            baseDetailDTO.setSpec(noticeDetail.getItemSpec());
            baseDetailDTO.setPrice(wmsItemStorageDetailInfo.getActualPrice());
            baseDetailDTO.setExpireTime(wmsItemStorageDetailInfo.getExpireDate());
            baseDetailDTO.setProduceTime(wmsItemStorageDetailInfo.getProductDate());
            detailDTOList.add(baseDetailDTO);
        });
    }
}
