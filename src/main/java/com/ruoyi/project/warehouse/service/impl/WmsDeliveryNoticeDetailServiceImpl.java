package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.component.StorageManager;
import com.ruoyi.project.warehouse.domain.WmsDeliveryNoticeDetail;
import com.ruoyi.project.warehouse.mapper.WmsDeliveryNoticeDetailMapper;
import com.ruoyi.project.warehouse.service.IWmsDeliveryNoticeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 出库通知明细Service业务层处理
 *
 * @author Rem
 * @date 2023-04-12
 */
@Service
public class WmsDeliveryNoticeDetailServiceImpl implements IWmsDeliveryNoticeDetailService {
    @Autowired
    private WmsDeliveryNoticeDetailMapper wmsDeliveryNoticeDetailMapper;

    @Autowired
    private StorageManager storageManager;

    /**
     * 查询出库通知明细
     *
     * @param id 出库通知明细主键
     * @return 出库通知明细
     */
    @Override
    public WmsDeliveryNoticeDetail selectWmsDeliveryNoticeDetailById(String id) {
        return wmsDeliveryNoticeDetailMapper.selectWmsDeliveryNoticeDetailById(id);
    }

    /**
     * 查询出库通知明细列表
     *
     * @param wmsDeliveryNoticeDetail 出库通知明细
     * @return 出库通知明细
     */
    @Override
    public List<WmsDeliveryNoticeDetail> selectWmsDeliveryNoticeDetailList(WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail) {
        return wmsDeliveryNoticeDetailMapper.selectWmsDeliveryNoticeDetailList(wmsDeliveryNoticeDetail);
    }

    /**
     * 新增出库通知明细
     *
     * @param wmsDeliveryNoticeDetail 出库通知明细
     * @return 结果
     */
    @Override
    public int insertWmsDeliveryNoticeDetail(WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail) {
        wmsDeliveryNoticeDetail.setCreateTime(DateUtils.getNowDate());
        return wmsDeliveryNoticeDetailMapper.insertWmsDeliveryNoticeDetail(wmsDeliveryNoticeDetail);
    }

    /**
     * 修改出库通知明细
     *
     * @param wmsDeliveryNoticeDetail 出库通知明细
     * @return 结果
     */
    @Override
    public int updateWmsDeliveryNoticeDetail(WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail) {
        wmsDeliveryNoticeDetail.setUpdateTime(DateUtils.getNowDate());
        return wmsDeliveryNoticeDetailMapper.updateWmsDeliveryNoticeDetail(wmsDeliveryNoticeDetail);
    }

    @Override
    public int updateByNotice(WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail) {
        return wmsDeliveryNoticeDetailMapper.updateByNotice(wmsDeliveryNoticeDetail);
    }

    /**
     * 批量删除出库通知明细
     *
     * @param ids 需要删除的出库通知明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsDeliveryNoticeDetailByIds(String[] ids) {
        return wmsDeliveryNoticeDetailMapper.deleteWmsDeliveryNoticeDetailByIds(ids);
    }

    /**
     * 删除出库通知明细信息
     *
     * @param id 出库通知明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsDeliveryNoticeDetailById(String id) {
        return wmsDeliveryNoticeDetailMapper.deleteWmsDeliveryNoticeDetailById(id);
    }

    /**
     * 根据出库通知id查询出库通知明细
     *
     * @param id id
     * @return {@link List}<{@link WmsDeliveryNoticeDetail}>
     */
    @Override
    public List<WmsDeliveryNoticeDetail> selectByNoticeId(String id) {
        return wmsDeliveryNoticeDetailMapper.selectByNoticeId(id);
    }

    /**
     * 取消分配
     *
     * @param ids id
     * @return {@link AjaxResult}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult cancelAllot(String[] ids) throws Exception {
        for (String id : ids) {
            // 查询出库通知明细
            WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail = wmsDeliveryNoticeDetailMapper.selectWmsDeliveryNoticeDetailById(id);
            if (null == wmsDeliveryNoticeDetail) {
                return AjaxResult.error("出库通知明细不存在");
            }

            // 如果分配数量大于0 则取消分配
            if (wmsDeliveryNoticeDetail.getAllotQuantity().compareTo(BigDecimal.ZERO) > 0) {
                // 取消分配的库存
                storageManager.cancelAllotByDeliveryNoticeDetail(wmsDeliveryNoticeDetail);
                // 更新出库通知明细数量
                WmsDeliveryNoticeDetail updateNoticeDetail = new WmsDeliveryNoticeDetail();
                updateNoticeDetail.setId(id);
                updateNoticeDetail.setAllotQuantity(BigDecimal.ZERO);
                updateNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
                updateNoticeDetail.setUpdateTime(DateUtils.getNowDate());
                wmsDeliveryNoticeDetailMapper.updateWmsDeliveryNoticeDetail(updateNoticeDetail);
            }

        }
        return AjaxResult.success();
    }
}
