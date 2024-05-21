package com.ruoyi.project.system.service.impl;

import com.ruoyi.project.system.domain.WmsDeliverySureDetail;
import com.ruoyi.project.system.mapper.WmsDeliverySureDetailMapper;
import com.ruoyi.project.system.service.IWmsDeliverySureDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出库确认记录详情Service业务层处理
 *
 * @author Rem
 * @date 2023-04-17
 */
@Service
public class WmsDeliverySureDetailServiceImpl implements IWmsDeliverySureDetailService {
    @Autowired
    private WmsDeliverySureDetailMapper wmsDeliverySureDetailMapper;

    /**
     * 查询出库确认记录详情
     *
     * @param id 出库确认记录详情主键
     * @return 出库确认记录详情
     */
    @Override
    public WmsDeliverySureDetail selectWmsDeliverySureDetailById(String id) {
        return wmsDeliverySureDetailMapper.selectWmsDeliverySureDetailById(id);
    }

    /**
     * 查询出库确认记录详情列表
     *
     * @param wmsDeliverySureDetail 出库确认记录详情
     * @return 出库确认记录详情
     */
    @Override
    public List<WmsDeliverySureDetail> selectWmsDeliverySureDetailList(WmsDeliverySureDetail wmsDeliverySureDetail) {
        return wmsDeliverySureDetailMapper.selectWmsDeliverySureDetailList(wmsDeliverySureDetail);
    }

    /**
     * 新增出库确认记录详情
     *
     * @param wmsDeliverySureDetail 出库确认记录详情
     * @return 结果
     */
    @Override
    public int insertWmsDeliverySureDetail(WmsDeliverySureDetail wmsDeliverySureDetail) {
        return wmsDeliverySureDetailMapper.insertWmsDeliverySureDetail(wmsDeliverySureDetail);
    }

    /**
     * 修改出库确认记录详情
     *
     * @param wmsDeliverySureDetail 出库确认记录详情
     * @return 结果
     */
    @Override
    public int updateWmsDeliverySureDetail(WmsDeliverySureDetail wmsDeliverySureDetail) {
        return wmsDeliverySureDetailMapper.updateWmsDeliverySureDetail(wmsDeliverySureDetail);
    }

    /**
     * 批量删除出库确认记录详情
     *
     * @param ids 需要删除的出库确认记录详情主键
     * @return 结果
     */
    @Override
    public int deleteWmsDeliverySureDetailByIds(String[] ids) {
        return wmsDeliverySureDetailMapper.deleteWmsDeliverySureDetailByIds(ids);
    }

    /**
     * 删除出库确认记录详情信息
     *
     * @param id 出库确认记录详情主键
     * @return 结果
     */
    @Override
    public int deleteWmsDeliverySureDetailById(String id) {
        return wmsDeliverySureDetailMapper.deleteWmsDeliverySureDetailById(id);
    }
}
