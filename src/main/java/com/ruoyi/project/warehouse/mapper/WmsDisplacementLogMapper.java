package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsDisplacementLog;

import java.util.List;

/**
 * 移位日志Mapper接口
 *
 * @author Rem
 * @date 2023-04-25
 */
public interface WmsDisplacementLogMapper {
    /**
     * 查询移位日志
     *
     * @param id 移位日志主键
     * @return 移位日志
     */
    public WmsDisplacementLog selectWmsDisplacementLogById(String id);

    /**
     * 查询移位日志列表
     *
     * @param wmsDisplacementLog 移位日志
     * @return 移位日志集合
     */
    public List<WmsDisplacementLog> selectWmsDisplacementLogList(WmsDisplacementLog wmsDisplacementLog);

    /**
     * 新增移位日志
     *
     * @param wmsDisplacementLog 移位日志
     * @return 结果
     */
    public int insertWmsDisplacementLog(WmsDisplacementLog wmsDisplacementLog);

    /**
     * 修改移位日志
     *
     * @param wmsDisplacementLog 移位日志
     * @return 结果
     */
    public int updateWmsDisplacementLog(WmsDisplacementLog wmsDisplacementLog);

    /**
     * 删除移位日志
     *
     * @param id 移位日志主键
     * @return 结果
     */
    public int deleteWmsDisplacementLogById(String id);

    /**
     * 批量删除移位日志
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsDisplacementLogByIds(String[] ids);
}
