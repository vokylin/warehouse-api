package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.warehouse.domain.StorageRecordVo;
import com.ruoyi.project.warehouse.domain.WmsStorageInOutDetail;
import com.ruoyi.project.warehouse.mapper.WmsStorageInOutDetailMapper;
import com.ruoyi.project.warehouse.service.IWmsStorageInOutDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出入库记录详情Service业务层处理
 *
 * @author Rem
 * @date 2023-04-08
 */
@Service
public class WmsStorageInOutDetailServiceImpl implements IWmsStorageInOutDetailService {
    @Autowired
    private WmsStorageInOutDetailMapper wmsStorageInOutDetailMapper;

    /**
     * 查询出入库记录详情
     *
     * @param id 出入库记录详情主键
     * @return 出入库记录详情
     */
    @Override
    public WmsStorageInOutDetail selectWmsStorageInOutDetailById(String id) {
        return wmsStorageInOutDetailMapper.selectWmsStorageInOutDetailById(id);
    }

    /**
     * 查询出入库记录详情列表
     *
     * @param wmsStorageInOutDetail 出入库记录详情
     * @return 出入库记录详情
     */
    @Override
    public List<WmsStorageInOutDetail> selectWmsStorageInOutDetailList(WmsStorageInOutDetail wmsStorageInOutDetail) {
        return wmsStorageInOutDetailMapper.selectWmsStorageInOutDetailList(wmsStorageInOutDetail);
    }

    /**
     * 新增出入库记录详情
     *
     * @param wmsStorageInOutDetail 出入库记录详情
     * @return 结果
     */
    @Override
    public int insertWmsStorageInOutDetail(WmsStorageInOutDetail wmsStorageInOutDetail) {
        wmsStorageInOutDetail.setCreateTime(DateUtils.getNowDate());
        return wmsStorageInOutDetailMapper.insertWmsStorageInOutDetail(wmsStorageInOutDetail);
    }

    /**
     * 修改出入库记录详情
     *
     * @param wmsStorageInOutDetail 出入库记录详情
     * @return 结果
     */
    @Override
    public int updateWmsStorageInOutDetail(WmsStorageInOutDetail wmsStorageInOutDetail) {
        wmsStorageInOutDetail.setUpdateTime(DateUtils.getNowDate());
        return wmsStorageInOutDetailMapper.updateWmsStorageInOutDetail(wmsStorageInOutDetail);
    }

    /**
     * 批量删除出入库记录详情
     *
     * @param ids 需要删除的出入库记录详情主键
     * @return 结果
     */
    @Override
    public int deleteWmsStorageInOutDetailByIds(String[] ids) {
        return wmsStorageInOutDetailMapper.deleteWmsStorageInOutDetailByIds(ids);
    }

    /**
     * 删除出入库记录详情信息
     *
     * @param id 出入库记录详情主键
     * @return 结果
     */
    @Override
    public int deleteWmsStorageInOutDetailById(String id) {
        return wmsStorageInOutDetailMapper.deleteWmsStorageInOutDetailById(id);
    }

    @Override
    public List<StorageRecordVo> selectByNoticeId(String noticeId, Integer billType) {
        return wmsStorageInOutDetailMapper.selectByNoticeId(noticeId, billType);
    }
}
