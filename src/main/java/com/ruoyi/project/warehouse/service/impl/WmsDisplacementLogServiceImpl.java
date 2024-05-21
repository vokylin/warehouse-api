package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.project.warehouse.domain.WmsDisplacementLog;
import com.ruoyi.project.warehouse.mapper.WmsDisplacementLogMapper;
import com.ruoyi.project.warehouse.service.IWmsDisplacementLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 移位日志Service业务层处理
 *
 * @author Rem
 * @date 2023-04-25
 */
@Service
public class WmsDisplacementLogServiceImpl implements IWmsDisplacementLogService {
    @Autowired
    private WmsDisplacementLogMapper wmsDisplacementLogMapper;

    /**
     * 查询移位日志
     *
     * @param id 移位日志主键
     * @return 移位日志
     */
    @Override
    public WmsDisplacementLog selectWmsDisplacementLogById(String id) {
        return wmsDisplacementLogMapper.selectWmsDisplacementLogById(id);
    }

    /**
     * 查询移位日志列表
     *
     * @param wmsDisplacementLog 移位日志
     * @return 移位日志
     */
    @Override
    public List<WmsDisplacementLog> selectWmsDisplacementLogList(WmsDisplacementLog wmsDisplacementLog) {
        return wmsDisplacementLogMapper.selectWmsDisplacementLogList(wmsDisplacementLog);
    }

    /**
     * 新增移位日志
     *
     * @param wmsDisplacementLog 移位日志
     * @return 结果
     */
    @Override
    public int insertWmsDisplacementLog(WmsDisplacementLog wmsDisplacementLog) {
        wmsDisplacementLog.setId(IdUtils.fastSimpleUUID());
        wmsDisplacementLog.setCreateUser(SecurityUtils.getUserId());
        wmsDisplacementLog.setCreateTime(DateUtils.getNowDate());
        return wmsDisplacementLogMapper.insertWmsDisplacementLog(wmsDisplacementLog);
    }

    /**
     * 修改移位日志
     *
     * @param wmsDisplacementLog 移位日志
     * @return 结果
     */
    @Override
    public int updateWmsDisplacementLog(WmsDisplacementLog wmsDisplacementLog) {
        return wmsDisplacementLogMapper.updateWmsDisplacementLog(wmsDisplacementLog);
    }

    /**
     * 批量删除移位日志
     *
     * @param ids 需要删除的移位日志主键
     * @return 结果
     */
    @Override
    public int deleteWmsDisplacementLogByIds(String[] ids) {
        return wmsDisplacementLogMapper.deleteWmsDisplacementLogByIds(ids);
    }

    /**
     * 删除移位日志信息
     *
     * @param id 移位日志主键
     * @return 结果
     */
    @Override
    public int deleteWmsDisplacementLogById(String id) {
        return wmsDisplacementLogMapper.deleteWmsDisplacementLogById(id);
    }
}
