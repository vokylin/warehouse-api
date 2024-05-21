package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.enums.PickWorkNoticeStatus;
import com.ruoyi.common.enums.RelateType;
import com.ruoyi.common.enums.WorkStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.WmsItemStorageDetail;
import com.ruoyi.project.warehouse.domain.WmsPickingWorkNoticeDetail;
import com.ruoyi.project.warehouse.domain.WmsWorkNotice;
import com.ruoyi.project.warehouse.mapper.WmsItemStorageDetailMapper;
import com.ruoyi.project.warehouse.mapper.WmsPickingWorkNoticeDetailMapper;
import com.ruoyi.project.warehouse.mapper.WmsWorkNoticeMapper;
import com.ruoyi.project.warehouse.service.IWmsPickingWorkNoticeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 拣货作业通知明细Service业务层处理
 *
 * @author Rem
 * @date 2023-04-15
 */
@Service
public class WmsPickingWorkNoticeDetailServiceImpl implements IWmsPickingWorkNoticeDetailService {
    @Autowired
    private WmsPickingWorkNoticeDetailMapper wmsPickingWorkNoticeDetailMapper;

    @Autowired
    private WmsItemStorageDetailMapper wmsItemStorageDetailMapper;

    @Autowired
    private WmsWorkNoticeMapper wmsWorkNoticeMapper;


    /**
     * 查询拣货作业通知明细
     *
     * @param id 拣货作业通知明细主键
     * @return 拣货作业通知明细
     */
    @Override
    public WmsPickingWorkNoticeDetail selectWmsPickingWorkNoticeDetailById(String id) {
        return wmsPickingWorkNoticeDetailMapper.selectWmsPickingWorkNoticeDetailById(id);
    }

    /**
     * 查询拣货作业通知明细列表
     *
     * @param wmsPickingWorkNoticeDetail 拣货作业通知明细
     * @return 拣货作业通知明细
     */
    @Override
    public List<WmsPickingWorkNoticeDetail> selectWmsPickingWorkNoticeDetailList(WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail) {
        return wmsPickingWorkNoticeDetailMapper.selectWmsPickingWorkNoticeDetailList(wmsPickingWorkNoticeDetail);
    }

    /**
     * 新增拣货作业通知明细
     *
     * @param wmsPickingWorkNoticeDetail 拣货作业通知明细
     * @return 结果
     */
    @Override
    public int insertWmsPickingWorkNoticeDetail(WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail) {
        wmsPickingWorkNoticeDetail.setCreateTime(DateUtils.getNowDate());
        return wmsPickingWorkNoticeDetailMapper.insertWmsPickingWorkNoticeDetail(wmsPickingWorkNoticeDetail);
    }

    /**
     * 修改拣货作业通知明细
     *
     * @param wmsPickingWorkNoticeDetail 拣货作业通知明细
     * @return 结果
     */
    @Override
    public int updateWmsPickingWorkNoticeDetail(WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail) {
        wmsPickingWorkNoticeDetail.setUpdateTime(DateUtils.getNowDate());
        return wmsPickingWorkNoticeDetailMapper.updateWmsPickingWorkNoticeDetail(wmsPickingWorkNoticeDetail);
    }

    /**
     * 更新拣货作业状态
     *
     * @param wmsPickingWorkNoticeDetail wms挑选工作注意细节
     * @return int
     */
    @Override
    public int updateWorkNoticeDetailStatus(WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail) {
        return wmsPickingWorkNoticeDetailMapper.updateWorkNoticeDetailStatus(wmsPickingWorkNoticeDetail);
    }

    /**
     * 批量删除拣货作业通知明细
     *
     * @param ids 需要删除的拣货作业通知明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsPickingWorkNoticeDetailByIds(String[] ids) {
        return wmsPickingWorkNoticeDetailMapper.deleteWmsPickingWorkNoticeDetailByIds(ids);
    }

    /**
     * 删除拣货作业通知明细信息
     *
     * @param id 拣货作业通知明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsPickingWorkNoticeDetailById(String id) {
        return wmsPickingWorkNoticeDetailMapper.deleteWmsPickingWorkNoticeDetailById(id);
    }

    /**
     * 根据出库通知id查询明细
     *
     * @param id id
     * @return {@link List}<{@link WmsPickingWorkNoticeDetail}>
     */
    @Override
    public List<WmsPickingWorkNoticeDetail> selectDetailByDeliveryNoticeId(String id) {
        return wmsPickingWorkNoticeDetailMapper.selectDetailByDeliveryNoticeId(id);
    }

    /**
     * 根据出库通知id查询明细
     *
     * @param id id
     * @return {@link List}<{@link WmsPickingWorkNoticeDetail}>
     */
    @Override
    public List<WmsPickingWorkNoticeDetail> selectDetailByDeliveryNoticeDetailId(String id) {
        return wmsPickingWorkNoticeDetailMapper.selectDetailByDeliveryNoticeDetailId(id);
    }


    /**
     * 缺货登记
     *
     * @param wmsPickingWorkNoticeDetail wms项存储细节
     * @return {@link AjaxResult}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult shortageCheckIn(WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail) {

        if (null == wmsPickingWorkNoticeDetail.getCurrentLackQuantity()) {
            return AjaxResult.error("缺货数量不能为空");
        }
        if (wmsPickingWorkNoticeDetail.getQuantity().compareTo(wmsPickingWorkNoticeDetail.getCurrentLackQuantity().add(wmsPickingWorkNoticeDetail.getLackQuantity())) < 0) {
            return AjaxResult.error("缺货数量不能大于拣货数量");
        }

        // 更新拣货作业通知明细表
        WmsPickingWorkNoticeDetail updateDetail = new WmsPickingWorkNoticeDetail();
        updateDetail.setId(wmsPickingWorkNoticeDetail.getId());
        updateDetail.setLackQuantity(wmsPickingWorkNoticeDetail.getLackQuantity().add(wmsPickingWorkNoticeDetail.getCurrentLackQuantity()));
        updateDetail.setUpdateBy(SecurityUtils.getUserId());
        updateDetail.setUpdateTime(DateUtils.getNowDate());
        wmsPickingWorkNoticeDetailMapper.updateWmsPickingWorkNoticeDetail(updateDetail);

        // 查询库存明细
        WmsItemStorageDetail selectDetail = new WmsItemStorageDetail();
        selectDetail.setBatchNo(wmsPickingWorkNoticeDetail.getBatchNo());
        selectDetail.setItemCode(wmsPickingWorkNoticeDetail.getItemCode());
        selectDetail.setRelateType(RelateType.PICKING.getCode());
        selectDetail.setWorkStatus(WorkStatus.WORKING.getCode());
        selectDetail.setRelateId(wmsPickingWorkNoticeDetail.getWorkNoticeId());
        WmsItemStorageDetail wmsItemStorageDetailInfo = wmsItemStorageDetailMapper.selectWmsItemStorageDetailByBatchNo(selectDetail);
        if (wmsItemStorageDetailInfo.getQuantity().compareTo(wmsPickingWorkNoticeDetail.getCurrentLackQuantity()) < 0) {
            return AjaxResult.error("缺货数量不能大于库存数量");
        }

        // 如果全部缺货 删除库存明细
        if (wmsItemStorageDetailInfo.getQuantity().compareTo(wmsPickingWorkNoticeDetail.getCurrentLackQuantity()) == 0) {
            wmsItemStorageDetailMapper.deleteWmsItemStorageDetailById(wmsItemStorageDetailInfo.getId());
        } else {
            // 部分缺货 更新库存明细  减去缺货数量
            wmsItemStorageDetailInfo.setQuantity(wmsItemStorageDetailInfo.getQuantity().subtract(wmsPickingWorkNoticeDetail.getCurrentLackQuantity()));
            wmsItemStorageDetailMapper.updateWmsItemStorageDetail(wmsItemStorageDetailInfo);
        }


        // 查询是否存在已经缺货登记的库存明细
        WmsItemStorageDetail selectLackDetail = new WmsItemStorageDetail();
        selectLackDetail.setBatchNo(wmsPickingWorkNoticeDetail.getBatchNo());
        selectLackDetail.setItemCode(wmsPickingWorkNoticeDetail.getItemCode());
        selectLackDetail.setRelateType(RelateType.PICKING.getCode());
        selectLackDetail.setWorkStatus(WorkStatus.ABNORMAL.getCode());
        selectLackDetail.setRelateId(wmsPickingWorkNoticeDetail.getWorkNoticeId());
        WmsItemStorageDetail lackStorageDetail = wmsItemStorageDetailMapper.selectWmsItemStorageDetailByBatchNo(selectLackDetail);
        if (null == lackStorageDetail) {
            // 不存在 新增库存明细
            WmsItemStorageDetail wmsItemStorageDetail = new WmsItemStorageDetail();
            wmsItemStorageDetail.setId(IdUtils.fastSimpleUUID());
            wmsItemStorageDetail.setStorageId(wmsItemStorageDetailInfo.getStorageId());
            wmsItemStorageDetail.setBatchNo(wmsItemStorageDetailInfo.getBatchNo());
            wmsItemStorageDetail.setQuantity(wmsPickingWorkNoticeDetail.getCurrentLackQuantity());
            wmsItemStorageDetail.setLocationId(wmsItemStorageDetailInfo.getLocationId());
            wmsItemStorageDetail.setStorageStatus(wmsItemStorageDetailInfo.getStorageStatus());
            wmsItemStorageDetail.setWorkStatus(WorkStatus.ABNORMAL.getCode());
            wmsItemStorageDetail.setProductDate(wmsItemStorageDetailInfo.getProductDate());
            wmsItemStorageDetail.setExpireDate(wmsItemStorageDetailInfo.getExpireDate());
            wmsItemStorageDetail.setThermosphere(wmsItemStorageDetailInfo.getThermosphere());
            wmsItemStorageDetail.setRelateType(wmsItemStorageDetailInfo.getRelateType());
            wmsItemStorageDetail.setRelateId(wmsPickingWorkNoticeDetail.getWorkNoticeId());
            wmsItemStorageDetail.setActualPrice(wmsItemStorageDetailInfo.getActualPrice());
            wmsItemStorageDetail.setCreateBy(SecurityUtils.getUserId());
            wmsItemStorageDetail.setCreateTime(DateUtils.getNowDate());
            wmsItemStorageDetailMapper.insertWmsItemStorageDetail(wmsItemStorageDetail);
        } else {
            // 存在 更新库存明细
            lackStorageDetail.setQuantity(lackStorageDetail.getQuantity().add(wmsPickingWorkNoticeDetail.getCurrentLackQuantity()));
            lackStorageDetail.setUpdateBy(SecurityUtils.getUserId());
            lackStorageDetail.setUpdateTime(DateUtils.getNowDate());
            wmsItemStorageDetailMapper.updateWmsItemStorageDetail(lackStorageDetail);
        }

        // 更新拣货作业通知状态为缺货登记中
        WmsWorkNotice wmsWorkNotice = new WmsWorkNotice();
        wmsWorkNotice.setId(wmsPickingWorkNoticeDetail.getWorkNoticeId());
        wmsWorkNotice.setStatus(PickWorkNoticeStatus.REGISTRATION.getCode());
        wmsWorkNotice.setUpdateBy(SecurityUtils.getUserId());
        wmsWorkNotice.setUpdateTime(DateUtils.getNowDate());
        wmsWorkNoticeMapper.updateWmsWorkNotice(wmsWorkNotice);

        return AjaxResult.success();
    }


}
