package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.enums.BaseWorkStatus;
import com.ruoyi.common.enums.ShelfWorkNoticeStatus;
import com.ruoyi.common.enums.ShelfWorkStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.component.StorageManager;
import com.ruoyi.project.common.component.StorageRecordManager;
import com.ruoyi.project.warehouse.domain.*;
import com.ruoyi.project.warehouse.mapper.WmsWorkNoticeAllotMapper;
import com.ruoyi.project.warehouse.mapper.WmsWorkNoticeDetailMapper;
import com.ruoyi.project.warehouse.mapper.WmsWorkNoticeMapper;
import com.ruoyi.project.warehouse.service.IWmsReceiveItemDetailService;
import com.ruoyi.project.warehouse.service.IWmsReceiveNoticeDetailService;
import com.ruoyi.project.warehouse.service.IWmsWorkNoticeAllotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 作业通知明细分配Service业务层处理
 *
 * @author Rem
 * @date 2023-04-07
 */
@Service
public class WmsWorkNoticeAllotServiceImpl implements IWmsWorkNoticeAllotService {
    @Autowired
    private WmsWorkNoticeAllotMapper wmsWorkNoticeAllotMapper;

    @Autowired
    private WmsWorkNoticeMapper wmsWorkNoticeMapper;

    @Autowired
    private StorageManager storageManager;

    @Autowired
    private StorageRecordManager storageRecordManager;

    @Autowired
    private WmsWorkNoticeDetailMapper wmsWorkNoticeDetailMapper;

    @Autowired
    private IWmsReceiveItemDetailService wmsReceiveItemDetailService;

    @Autowired
    private IWmsReceiveNoticeDetailService wmsReceiveNoticeDetailService;


    /**
     * 查询作业通知明细分配
     *
     * @param id 作业通知明细分配主键
     * @return 作业通知明细分配
     */
    @Override
    public WmsWorkNoticeAllot selectWmsWorkNoticeAllotById(String id) {
        return wmsWorkNoticeAllotMapper.selectWmsWorkNoticeAllotById(id);
    }

    /**
     * 查询作业通知明细分配列表
     *
     * @param wmsWorkNoticeAllot 作业通知明细分配
     * @return 作业通知明细分配
     */
    @Override
    public List<WmsWorkNoticeAllot> selectWmsWorkNoticeAllotList(WmsWorkNoticeAllot wmsWorkNoticeAllot) {
        return wmsWorkNoticeAllotMapper.selectWmsWorkNoticeAllotList(wmsWorkNoticeAllot);
    }

    /**
     * 新增作业通知明细分配
     *
     * @param wmsWorkNoticeAllot 作业通知明细分配
     * @return 结果
     */
    @Override
    public int insertWmsWorkNoticeAllot(WmsWorkNoticeAllot wmsWorkNoticeAllot) {
        wmsWorkNoticeAllot.setCreateTime(DateUtils.getNowDate());
        return wmsWorkNoticeAllotMapper.insertWmsWorkNoticeAllot(wmsWorkNoticeAllot);
    }

    /**
     * 修改作业通知明细分配
     *
     * @param wmsWorkNoticeAllot 作业通知明细分配
     * @return 结果
     */
    @Override
    public int updateWmsWorkNoticeAllot(WmsWorkNoticeAllot wmsWorkNoticeAllot) {
        wmsWorkNoticeAllot.setUpdateTime(DateUtils.getNowDate());
        return wmsWorkNoticeAllotMapper.updateWmsWorkNoticeAllot(wmsWorkNoticeAllot);
    }

    /**
     * 批量删除作业通知明细分配
     *
     * @param ids 需要删除的作业通知明细分配主键
     * @return 结果
     */
    @Override
    public int deleteWmsWorkNoticeAllotByIds(String[] ids) {
        return wmsWorkNoticeAllotMapper.deleteWmsWorkNoticeAllotByIds(ids);
    }

    /**
     * 删除作业通知明细分配信息
     *
     * @param id 作业通知明细分配主键
     * @return 结果
     */
    @Override
    public int deleteWmsWorkNoticeAllotById(String id) {
        return wmsWorkNoticeAllotMapper.deleteWmsWorkNoticeAllotById(id);
    }


    @Override
    public WmsWorkNoticeAllot selectItemDetail(WmsWorkNoticeAllot wmsWorkNoticeAllotQuery) {
        return wmsWorkNoticeAllotMapper.selectItemDetail(wmsWorkNoticeAllotQuery);
    }

    @Override
    public List<WmsWorkNoticeAllot> selectAllotListByDetailId(String id) {
        return wmsWorkNoticeAllotMapper.selectAllotListByDetailId(id);
    }


    /**
     * 取消分配
     *
     * @param ids id
     * @return int
     */
    @Override
    public AjaxResult cancelWmsWorkNoticeAllotByIds(String[] ids) {
        for (String id : ids) {
            WmsWorkNoticeAllot wmsWorkNoticeAllot = wmsWorkNoticeAllotMapper.selectWmsWorkNoticeAllotById(id);
            if (wmsWorkNoticeAllot.getStatus().equals(BaseWorkStatus.FINISH.getCode())) {
                return AjaxResult.error("已确认的不能取消分配");
            }
            // 更新作业通知明细的分配数量
            WmsWorkNoticeDetail searchDetail = wmsWorkNoticeDetailMapper.selectWmsWorkNoticeDetailById(wmsWorkNoticeAllot.getWorkNoticeDetailId());
            WmsWorkNoticeDetail workNoticeDetail = new WmsWorkNoticeDetail();
            workNoticeDetail.setId(wmsWorkNoticeAllot.getWorkNoticeDetailId());
            workNoticeDetail.setAllotQuantity(searchDetail.getAllotQuantity().subtract(wmsWorkNoticeAllot.getQuantity()));
            workNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
            workNoticeDetail.setUpdateTime(DateUtils.getNowDate());
            wmsWorkNoticeDetailMapper.updateWmsWorkNoticeDetail(workNoticeDetail);

            // 删除分配记录
            wmsWorkNoticeAllotMapper.deleteWmsWorkNoticeAllotById(id);
        }
        return AjaxResult.success();
    }

    /**
     * 提交入库
     *
     * @param ids id
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult submitWmsWorkNoticeAllotByIds(WorkNoticeBatchSubmitAllotDto workNoticeBatchSubmitAllotDto) {
        WmsWorkNotice workNotice = wmsWorkNoticeMapper.selectWmsWorkNoticeById(workNoticeBatchSubmitAllotDto.getWorkNoticeId());
        if (null == workNotice) {
            return AjaxResult.error("作业通知不存在");
        }

        for (String id : workNoticeBatchSubmitAllotDto.getIds()) {
            WmsWorkNoticeAllot wmsWorkNoticeAllot = wmsWorkNoticeAllotMapper.selectWmsWorkNoticeAllotById(id);

            // 判断是否已经入库
            if (!wmsWorkNoticeAllot.getStatus().equals(ShelfWorkStatus.FINISH.getCode())) {
                wmsWorkNoticeAllot.setStatus(ShelfWorkStatus.FINISH.getCode());
                wmsWorkNoticeAllot.setUpdateBy(SecurityUtils.getUserId());
                wmsWorkNoticeAllot.setUpdateTime(DateUtils.getNowDate());
                wmsWorkNoticeAllotMapper.updateWmsWorkNoticeAllot(wmsWorkNoticeAllot);

                // 查询作业通知明细
                WmsWorkNoticeDetail workNoticeDetail = wmsWorkNoticeDetailMapper.selectWmsWorkNoticeDetailById(wmsWorkNoticeAllot.getWorkNoticeDetailId());

                // 更新入库通知分配明细的上架数量
                WmsReceiveItemDetail wmsReceiveItemDetail = wmsReceiveItemDetailService.selectWmsReceiveItemDetailById(workNoticeDetail.getNoticeItemDetailId());
                WmsReceiveItemDetail receiveItemDetail = new WmsReceiveItemDetail();
                receiveItemDetail.setDepositQuantity(wmsReceiveItemDetail.getDepositQuantity().add(wmsWorkNoticeAllot.getQuantity()));
                receiveItemDetail.setId(wmsReceiveItemDetail.getId());
                wmsReceiveItemDetailService.updateWmsReceiveItemDetail(receiveItemDetail);

                // 上架作业 更新库存信息
                try {
                    storageManager.updateStorageInfo(workNoticeDetail, wmsWorkNoticeAllot);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                // 保存出入库记录明细
                storageRecordManager.saveInRecordDetail(wmsWorkNoticeAllot);
            }
        }

        // 判断作业是否完成
        if (wmsWorkNoticeMapper.selectIsCompleteById(workNoticeBatchSubmitAllotDto.getWorkNoticeId()) == 0) {
            // 更新作业通知状态
            WmsWorkNotice wmsWorkNotice = new WmsWorkNotice();
            wmsWorkNotice.setId(workNoticeBatchSubmitAllotDto.getWorkNoticeId());
            wmsWorkNotice.setStatus(ShelfWorkNoticeStatus.WORK_COMPLETE.getCode());
            wmsWorkNotice.setWorkEndTime(DateUtils.getNowDate());
            wmsWorkNotice.setUpdateBy(SecurityUtils.getUserId());
            wmsWorkNotice.setUpdateTime(DateUtils.getNowDate());
            wmsWorkNoticeMapper.updateWmsWorkNotice(wmsWorkNotice);
        }
        return AjaxResult.success();
    }

    /**
     * 退货确认
     *
     * @param id id
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult refundConfirm(String id) {
        // 查询分配明细
        WmsWorkNoticeAllot workNoticeAllot = wmsWorkNoticeAllotMapper.selectWmsWorkNoticeAllotById(id);
        if (null == workNoticeAllot) {
            return AjaxResult.error("分配明细不存在");
        }

        // 查询作业通知明细
        WmsWorkNoticeDetail workNoticeDetail = wmsWorkNoticeDetailMapper.selectWmsWorkNoticeDetailById(workNoticeAllot.getWorkNoticeDetailId());
        if (null == workNoticeDetail) {
            return AjaxResult.error("作业通知明细不存在");
        }

        if (workNoticeAllot.getStatus().equals(ShelfWorkStatus.FINISH.getCode())) {
            return AjaxResult.error("已确认的不能再次确认");
        }

        // 更新退货数量
        String noticeDetailId = workNoticeDetail.getNoticeItemDetailId();

        WmsReceiveItemDetail receiveItemDetail = wmsReceiveItemDetailService.selectWmsReceiveItemDetailById(noticeDetailId);

        // 更新退货数量  退货数量 = 退货数量 + 分配数量
        WmsReceiveItemDetail updateReceiveItemDetail = new WmsReceiveItemDetail();
        updateReceiveItemDetail.setId(noticeDetailId);
        updateReceiveItemDetail.setRefundQuantity(receiveItemDetail.getRefundQuantity().add(workNoticeAllot.getQuantity()));
        updateReceiveItemDetail.setUpdateBy(SecurityUtils.getUserId());
        updateReceiveItemDetail.setUpdateTime(DateUtils.getNowDate());
        wmsReceiveItemDetailService.updateWmsReceiveItemDetail(updateReceiveItemDetail);

        // 更新分配明细状态
        WmsWorkNoticeAllot wmsWorkNoticeAllot = new WmsWorkNoticeAllot();
        wmsWorkNoticeAllot.setId(id);
        wmsWorkNoticeAllot.setStatus(ShelfWorkStatus.FINISH.getCode());
        wmsWorkNoticeAllot.setUpdateBy(SecurityUtils.getUserId());
        wmsWorkNoticeAllot.setUpdateTime(DateUtils.getNowDate());
        wmsWorkNoticeAllotMapper.updateWmsWorkNoticeAllot(wmsWorkNoticeAllot);

        if (wmsWorkNoticeMapper.selectIsCompleteById(workNoticeAllot.getWorkNoticeId()) == 0) {
            // 更新作业通知状态
            WmsWorkNotice updateWorkNotice = new WmsWorkNotice();
            updateWorkNotice.setId(workNoticeAllot.getWorkNoticeId());
            updateWorkNotice.setStatus(ShelfWorkStatus.FINISH.getCode());
            updateWorkNotice.setUpdateBy(SecurityUtils.getUserId());
            updateWorkNotice.setUpdateTime(DateUtils.getNowDate());
            wmsWorkNoticeMapper.updateWmsWorkNotice(updateWorkNotice);
        }
        return AjaxResult.success();

    }


    @Override
    public void refundConfirm(WmsWorkNoticeAllot workNoticeAllot) {

        // 查询作业通知明细
        WmsWorkNoticeDetail workNoticeDetail = wmsWorkNoticeDetailMapper.selectWmsWorkNoticeDetailById(workNoticeAllot.getWorkNoticeDetailId());

        // 更新退货数量
        String noticeDetailId = workNoticeDetail.getNoticeItemDetailId();

        WmsReceiveItemDetail receiveItemDetail = wmsReceiveItemDetailService.selectWmsReceiveItemDetailById(noticeDetailId);

        // 更新退货数量  退货数量 = 退货数量 + 分配数量
        WmsReceiveItemDetail updateReceiveItemDetail = new WmsReceiveItemDetail();
        updateReceiveItemDetail.setId(noticeDetailId);
        updateReceiveItemDetail.setRefundQuantity(receiveItemDetail.getRefundQuantity().add(workNoticeAllot.getQuantity()));
        updateReceiveItemDetail.setUpdateBy(SecurityUtils.getUserId());
        updateReceiveItemDetail.setUpdateTime(DateUtils.getNowDate());
        wmsReceiveItemDetailService.updateWmsReceiveItemDetail(updateReceiveItemDetail);

        if (wmsWorkNoticeMapper.selectIsCompleteById(workNoticeAllot.getWorkNoticeId()) == 0) {
            // 更新作业通知状态
            WmsWorkNotice updateWorkNotice = new WmsWorkNotice();
            updateWorkNotice.setId(workNoticeAllot.getWorkNoticeId());
            updateWorkNotice.setStatus(ShelfWorkStatus.FINISH.getCode());
            updateWorkNotice.setUpdateBy(SecurityUtils.getUserId());
            updateWorkNotice.setUpdateTime(DateUtils.getNowDate());
            wmsWorkNoticeMapper.updateWmsWorkNotice(updateWorkNotice);
        }
    }

    @Override
    public void batchUpdateStatus(String id, Integer status) {
        wmsWorkNoticeAllotMapper.batchUpdateStatus(id, status);
    }


}
