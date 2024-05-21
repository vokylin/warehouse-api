package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.enums.*;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.StorageUnit;
import com.ruoyi.project.system.mapper.StorageUnitMapper;
import com.ruoyi.project.warehouse.domain.WmsWorkNotice;
import com.ruoyi.project.warehouse.domain.WmsWorkNoticeAllot;
import com.ruoyi.project.warehouse.domain.WmsWorkNoticeDetail;
import com.ruoyi.project.warehouse.mapper.WmsWorkNoticeDetailMapper;
import com.ruoyi.project.warehouse.mapper.WmsWorkNoticeMapper;
import com.ruoyi.project.warehouse.service.IWmsWorkNoticeAllotService;
import com.ruoyi.project.warehouse.service.IWmsWorkNoticeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 作业通知明细Service业务层处理
 *
 * @author Rem
 * @date 2023-04-07
 */
@Service
public class WmsWorkNoticeDetailServiceImpl implements IWmsWorkNoticeDetailService {
    @Autowired
    private WmsWorkNoticeDetailMapper wmsWorkNoticeDetailMapper;

    @Autowired
    private WmsWorkNoticeMapper wmsWorkNoticeMapper;

    @Autowired
    private StorageUnitMapper storageUnitMapper;

    @Autowired
    private IWmsWorkNoticeAllotService wmsWorkNoticeAllotService;


    /**
     * 查询作业通知明细
     *
     * @param id 作业通知明细主键
     * @return 作业通知明细
     */
    @Override
    public WmsWorkNoticeDetail selectWmsWorkNoticeDetailById(String id) {
        return wmsWorkNoticeDetailMapper.selectWmsWorkNoticeDetailById(id);
    }

    /**
     * 查询作业通知明细列表
     *
     * @param wmsWorkNoticeDetail 作业通知明细
     * @return 作业通知明细
     */
    @Override
    public List<WmsWorkNoticeDetail> selectWmsWorkNoticeDetailList(WmsWorkNoticeDetail wmsWorkNoticeDetail) {
        return wmsWorkNoticeDetailMapper.selectWmsWorkNoticeDetailList(wmsWorkNoticeDetail);
    }

    /**
     * 新增作业通知明细
     *
     * @param wmsWorkNoticeDetail 作业通知明细
     * @return 结果
     */
    @Override
    public int insertWmsWorkNoticeDetail(WmsWorkNoticeDetail wmsWorkNoticeDetail) {
        wmsWorkNoticeDetail.setCreateTime(DateUtils.getNowDate());
        return wmsWorkNoticeDetailMapper.insertWmsWorkNoticeDetail(wmsWorkNoticeDetail);
    }

    /**
     * 修改作业通知明细
     *
     * @param wmsWorkNoticeDetail 作业通知明细
     * @return 结果
     */
    @Override
    public int updateWmsWorkNoticeDetail(WmsWorkNoticeDetail wmsWorkNoticeDetail) {
        wmsWorkNoticeDetail.setUpdateTime(DateUtils.getNowDate());
        return wmsWorkNoticeDetailMapper.updateWmsWorkNoticeDetail(wmsWorkNoticeDetail);
    }

    /**
     * 批量删除作业通知明细
     *
     * @param ids 需要删除的作业通知明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsWorkNoticeDetailByIds(String[] ids) {
        return wmsWorkNoticeDetailMapper.deleteWmsWorkNoticeDetailByIds(ids);
    }

    /**
     * 删除作业通知明细信息
     *
     * @param id 作业通知明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsWorkNoticeDetailById(String id) {
        return wmsWorkNoticeDetailMapper.deleteWmsWorkNoticeDetailById(id);
    }

    @Override
    public List<WmsWorkNoticeDetail> selectByWorkNoticeId(String id) {
        return wmsWorkNoticeDetailMapper.selectByWorkNoticeId(id);
    }


    /**
     * 分配
     *
     * @param wmsWorkNoticeDetail 世界媒体峰会工作注意细节
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult allot(WmsWorkNoticeDetail wmsWorkNoticeDetail) {


        WmsWorkNoticeDetail noticeDetailInfo = wmsWorkNoticeDetailMapper.selectWmsWorkNoticeDetailById(wmsWorkNoticeDetail.getId());
        if (noticeDetailInfo == null) {
            return AjaxResult.error("作业通知明细不存在");
        }
        BigDecimal subtract = noticeDetailInfo.getQuantity().subtract(noticeDetailInfo.getAllotQuantity());
        if (subtract.compareTo(wmsWorkNoticeDetail.getCurrentAllocatedQuantity()) < 0) {
            return AjaxResult.error("分配数量不能大于可分配数量");
        }

        // 分配数量
        BigDecimal currentAllocatedQuantity = wmsWorkNoticeDetail.getCurrentAllocatedQuantity();
        // 通知数量
        BigDecimal quantity = wmsWorkNoticeDetail.getQuantity();

        // 分配数量不能大于通知数量
        if (currentAllocatedQuantity.compareTo(quantity) > 0) {
            return AjaxResult.error("分配数量不能大于通知数量");
        }

        // 更新作业通知明细
        WmsWorkNoticeDetail workNoticeDetail = new WmsWorkNoticeDetail();
        workNoticeDetail.setId(wmsWorkNoticeDetail.getId());
        workNoticeDetail.setAllotQuantity(wmsWorkNoticeDetail.getAllotQuantity().add(currentAllocatedQuantity));
        workNoticeDetail.setLocationId(wmsWorkNoticeDetail.getLocationId());
        workNoticeDetail.setUpdateTime(DateUtils.getNowDate());
        workNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
        wmsWorkNoticeDetailMapper.updateWmsWorkNoticeDetail(workNoticeDetail);

        // 查询库存单位
        StorageUnit storageUnit = storageUnitMapper.selectStorageUnitById(wmsWorkNoticeDetail.getLocationId());

        // 查询作业通知
        WmsWorkNotice wmsWorkNotice = wmsWorkNoticeMapper.selectWmsWorkNoticeById(wmsWorkNoticeDetail.getWorkNoticeId());

        // 查询是否存在分配明细
        WmsWorkNoticeAllot wmsWorkNoticeAllotQuery = new WmsWorkNoticeAllot();
        wmsWorkNoticeAllotQuery.setWorkNoticeDetailId(wmsWorkNoticeDetail.getId());
        wmsWorkNoticeAllotQuery.setItemCode(wmsWorkNoticeDetail.getItemCode());
        wmsWorkNoticeAllotQuery.setBatchNo(wmsWorkNoticeDetail.getBatchNo());
        wmsWorkNoticeAllotQuery.setTargetLocationId(wmsWorkNoticeDetail.getLocationId());
        wmsWorkNoticeAllotQuery.setStatus(ShelfWorkStatus.WORKING.getCode());
        WmsWorkNoticeAllot sourceWorkNoticeAllot = wmsWorkNoticeAllotService.selectItemDetail(wmsWorkNoticeAllotQuery);
        if (null != sourceWorkNoticeAllot) {
            // 更新分配明细
            WmsWorkNoticeAllot wmsWorkNoticeAllot = new WmsWorkNoticeAllot();
            wmsWorkNoticeAllot.setId(sourceWorkNoticeAllot.getId());
            wmsWorkNoticeAllot.setQuantity(sourceWorkNoticeAllot.getQuantity().add(currentAllocatedQuantity));
            wmsWorkNoticeAllot.setUpdateTime(DateUtils.getNowDate());
            wmsWorkNoticeAllot.setUpdateBy(SecurityUtils.getUserId());
            wmsWorkNoticeAllotService.updateWmsWorkNoticeAllot(wmsWorkNoticeAllot);
        } else {
            // 新增分配明细
            WmsWorkNoticeAllot wmsWorkNoticeAllot = new WmsWorkNoticeAllot();
            wmsWorkNoticeAllot.setId(IdUtils.fastSimpleUUID());
            wmsWorkNoticeAllot.setWorkNoticeId(wmsWorkNoticeDetail.getWorkNoticeId());
            wmsWorkNoticeAllot.setWorkNoticeDetailId(wmsWorkNoticeDetail.getId());
            wmsWorkNoticeAllot.setBatchNo(wmsWorkNoticeDetail.getBatchNo());
            wmsWorkNoticeAllot.setItemCode(wmsWorkNoticeDetail.getItemCode());
            wmsWorkNoticeAllot.setQuantity(currentAllocatedQuantity);

            if (wmsWorkNotice.getType().equals(WorkType.SHELF.getCode())) {
                wmsWorkNoticeAllot.setStatus(ShelfWorkStatus.WORKING.getCode());
            } else if (wmsWorkNotice.getType().equals(WorkType.REFUND.getCode())) {
                wmsWorkNoticeAllot.setStatus(RefundWorkStatus.WAIT_CONFIRM.getCode());
            }

            wmsWorkNoticeAllot.setSourceLocationId(wmsWorkNoticeDetail.getSourceLocationId());
            wmsWorkNoticeAllot.setTargetLocationId(wmsWorkNoticeDetail.getLocationId());
            wmsWorkNoticeAllot.setWarehouseId(storageUnit.getWarehouseId());
            wmsWorkNoticeAllot.setCreateTime(DateUtils.getNowDate());
            wmsWorkNoticeAllot.setCreateBy(SecurityUtils.getUserId());
            wmsWorkNoticeAllotService.insertWmsWorkNoticeAllot(wmsWorkNoticeAllot);
        }

        if (null == wmsWorkNotice.getWorkStartTime()) {
            wmsWorkNotice.setWorkStartTime(DateUtils.getNowDate());
            if (wmsWorkNotice.getType().equals(WorkType.SHELF.getCode())) {
                wmsWorkNotice.setStatus(ShelfWorkNoticeStatus.WORKING.getCode());
            } else if (wmsWorkNotice.getType().equals(WorkType.REFUND.getCode())) {
                wmsWorkNotice.setStatus(RefundWorkNoticeStatus.REFUNDING.getCode());
            }
            wmsWorkNotice.setUpdateTime(DateUtils.getNowDate());
            wmsWorkNotice.setUpdateBy(SecurityUtils.getUserId());
            wmsWorkNoticeMapper.updateWmsWorkNotice(wmsWorkNotice);
        }
        return AjaxResult.success();
    }

    /**
     * 退货完成
     *
     * @param workNoticeDetail 世界媒体峰会工作注意细节
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult returnComplete(WmsWorkNoticeDetail workNoticeDetail) {
        // 判断通知明细是否已经分配完成
        if (workNoticeDetail.getQuantity().compareTo(workNoticeDetail.getAllotQuantity()) > 0) {
            // 没有分配完成 新增分配明细  将剩余未分配的数量生成分配明细
            BigDecimal quantity = workNoticeDetail.getQuantity().subtract(workNoticeDetail.getAllotQuantity());
            StorageUnit storageUnit = storageUnitMapper.selectStorageUnitById(workNoticeDetail.getSourceLocationId());
            WmsWorkNoticeAllot wmsWorkNoticeAllot = new WmsWorkNoticeAllot();
            wmsWorkNoticeAllot.setId(IdUtils.fastSimpleUUID());
            wmsWorkNoticeAllot.setWorkNoticeId(workNoticeDetail.getWorkNoticeId());
            wmsWorkNoticeAllot.setWorkNoticeDetailId(workNoticeDetail.getId());
            wmsWorkNoticeAllot.setBatchNo(workNoticeDetail.getBatchNo());
            wmsWorkNoticeAllot.setItemCode(workNoticeDetail.getItemCode());
            wmsWorkNoticeAllot.setStatus(RefundWorkStatus.REFUNDED.getCode());
            wmsWorkNoticeAllot.setSourceLocationId(workNoticeDetail.getSourceLocationId());
            // 目标仓库就是来源仓库
            wmsWorkNoticeAllot.setTargetLocationId(workNoticeDetail.getSourceLocationId());
            wmsWorkNoticeAllot.setWarehouseId(storageUnit.getWarehouseId());
            wmsWorkNoticeAllot.setCreateTime(DateUtils.getNowDate());
            wmsWorkNoticeAllot.setCreateBy(SecurityUtils.getUserId());
            wmsWorkNoticeAllot.setQuantity(quantity);

            // 新增分配明细
            wmsWorkNoticeAllotService.insertWmsWorkNoticeAllot(wmsWorkNoticeAllot);

            // 分配明细 退货确认
            wmsWorkNoticeAllotService.refundConfirm(wmsWorkNoticeAllot);

            // 更新作业通知明细的分配数量
            workNoticeDetail.setAllotQuantity(workNoticeDetail.getQuantity());
            workNoticeDetail.setUpdateTime(DateUtils.getNowDate());
            workNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
            wmsWorkNoticeDetailMapper.updateWmsWorkNoticeDetail(workNoticeDetail);
        }
        // 分配完成的 查询还有没有没有确认的分配明细
        List<WmsWorkNoticeAllot> wmsWorkNoticeAllots = wmsWorkNoticeAllotService.selectAllotListByDetailId(workNoticeDetail.getId());
        List<WmsWorkNoticeAllot> collect = wmsWorkNoticeAllots.stream().filter(item -> item.getStatus().equals(RefundWorkStatus.WAIT_CONFIRM.getCode())).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            // 待确认的进行退货确认
            collect.forEach(wmsWorkNoticeAllotService::refundConfirm);
        }
        return AjaxResult.success();
    }

    /**
     * 取消分配
     *
     * @param ids@return {@link AjaxResult}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult cancelAllot(String[] ids) throws Exception {

        for (String id : ids) {
            // 查询分配明细
            WmsWorkNoticeAllot wmsWorkNoticeAllot = wmsWorkNoticeAllotService.selectWmsWorkNoticeAllotById(id);

            // 查询作业通知明细
            WmsWorkNoticeDetail workNoticeDetail = wmsWorkNoticeDetailMapper.selectWmsWorkNoticeDetailById(wmsWorkNoticeAllot.getWorkNoticeDetailId());

            // 更新作业通知明细
            workNoticeDetail.setAllotQuantity(workNoticeDetail.getAllotQuantity().subtract(wmsWorkNoticeAllot.getQuantity()));
            workNoticeDetail.setUpdateTime(DateUtils.getNowDate());
            workNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
            wmsWorkNoticeDetailMapper.updateWmsWorkNoticeDetail(workNoticeDetail);

            // 删除分配明细
            int deleteResult = wmsWorkNoticeAllotService.deleteWmsWorkNoticeAllotById(id);
            if (deleteResult != 1) {
                throw new Exception("取消分配失败");
            }
        }

        return AjaxResult.success();
    }

    /**
     * 提交分配
     *
     * @param ids id
     * @return {@link AjaxResult}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult submitAllot(String[] ids) {

        Arrays.stream(ids).forEach(id -> {
            // 查询分配明细
            WmsWorkNoticeAllot wmsWorkNoticeAllot = wmsWorkNoticeAllotService.selectWmsWorkNoticeAllotById(id);

            // 查询作业通知
            WmsWorkNotice workNotice = wmsWorkNoticeMapper.selectWmsWorkNoticeById(wmsWorkNoticeAllot.getWorkNoticeId());
            if (null == workNotice.getWorkStartTime()) {
                // 更新作业通知
                workNotice.setWorkStartTime(DateUtils.getNowDate());
                workNotice.setUpdateTime(DateUtils.getNowDate());
                workNotice.setUpdateBy(SecurityUtils.getUserId());
                wmsWorkNoticeMapper.updateWmsWorkNotice(workNotice);
            }

            // 更新分配明细
            wmsWorkNoticeAllot.setStatus(ShelfWorkStatus.FINISH.getCode());
            wmsWorkNoticeAllot.setUpdateTime(DateUtils.getNowDate());
            wmsWorkNoticeAllot.setUpdateBy(SecurityUtils.getUserId());
            wmsWorkNoticeAllotService.updateWmsWorkNoticeAllot(wmsWorkNoticeAllot);
        });

        return AjaxResult.success();
    }


}
