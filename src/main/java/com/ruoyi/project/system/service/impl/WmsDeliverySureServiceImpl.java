package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.system.domain.DeliverySureInfo;
import com.ruoyi.project.system.domain.WmsDeliverySure;
import com.ruoyi.project.system.mapper.WmsDeliverySureMapper;
import com.ruoyi.project.system.service.IWmsDeliverySureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出库确认记录Service业务层处理
 *
 * @author Rem
 * @date 2023-04-17
 */
@Service
public class WmsDeliverySureServiceImpl implements IWmsDeliverySureService {
    @Autowired
    private WmsDeliverySureMapper wmsDeliverySureMapper;

    /**
     * 查询出库确认记录
     *
     * @param id 出库确认记录主键
     * @return 出库确认记录
     */
    @Override
    public WmsDeliverySure selectWmsDeliverySureById(String id) {
        return wmsDeliverySureMapper.selectWmsDeliverySureById(id);
    }

    /**
     * 查询出库确认记录列表
     *
     * @param wmsDeliverySure 出库确认记录
     * @return 出库确认记录
     */
    @Override
    public List<WmsDeliverySure> selectWmsDeliverySureList(WmsDeliverySure wmsDeliverySure) {
        return wmsDeliverySureMapper.selectWmsDeliverySureList(wmsDeliverySure);
    }

    /**
     * 新增出库确认记录
     *
     * @param wmsDeliverySure 出库确认记录
     * @return 结果
     */
    @Override
    public int insertWmsDeliverySure(WmsDeliverySure wmsDeliverySure) {
        wmsDeliverySure.setCreateTime(DateUtils.getNowDate());
        return wmsDeliverySureMapper.insertWmsDeliverySure(wmsDeliverySure);
    }

    /**
     * 修改出库确认记录
     *
     * @param wmsDeliverySure 出库确认记录
     * @return 结果
     */
    @Override
    public int updateWmsDeliverySure(WmsDeliverySure wmsDeliverySure) {
        return wmsDeliverySureMapper.updateWmsDeliverySure(wmsDeliverySure);
    }

    /**
     * 批量删除出库确认记录
     *
     * @param ids 需要删除的出库确认记录主键
     * @return 结果
     */
    @Override
    public int deleteWmsDeliverySureByIds(String[] ids) {
        return wmsDeliverySureMapper.deleteWmsDeliverySureByIds(ids);
    }

    /**
     * 删除出库确认记录信息
     *
     * @param id 出库确认记录主键
     * @return 结果
     */
    @Override
    public int deleteWmsDeliverySureById(String id) {
        return wmsDeliverySureMapper.deleteWmsDeliverySureById(id);
    }

    @Override
    public DeliverySureInfo getDeliverySureInfo(String id) {
        return wmsDeliverySureMapper.selectByNoticeId(id);
    }


}
