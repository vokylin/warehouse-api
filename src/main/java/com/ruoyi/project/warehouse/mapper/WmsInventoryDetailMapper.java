package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsInventoryDetail;

import java.util.List;

/**
 * 盘点详情Mapper接口
 *
 * @author Rem
 * @date 2023-05-21
 */
public interface WmsInventoryDetailMapper {
    /**
     * 查询盘点详情
     *
     * @param id 盘点详情主键
     * @return 盘点详情
     */
    public WmsInventoryDetail selectWmsInventoryDetailById(String id);

    /**
     * 查询盘点详情列表
     *
     * @param wmsInventoryDetail 盘点详情
     * @return 盘点详情集合
     */
    public List<WmsInventoryDetail> selectWmsInventoryDetailList(WmsInventoryDetail wmsInventoryDetail);

    /**
     * 新增盘点详情
     *
     * @param wmsInventoryDetail 盘点详情
     * @return 结果
     */
    public int insertWmsInventoryDetail(WmsInventoryDetail wmsInventoryDetail);

    /**
     * 修改盘点详情
     *
     * @param wmsInventoryDetail 盘点详情
     * @return 结果
     */
    public int updateWmsInventoryDetail(WmsInventoryDetail wmsInventoryDetail);

    /**
     * 删除盘点详情
     *
     * @param id 盘点详情主键
     * @return 结果
     */
    public int deleteWmsInventoryDetailById(String id);

    /**
     * 批量删除盘点详情
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsInventoryDetailByIds(String[] ids);

    WmsInventoryDetail selectInventoryDetail(WmsInventoryDetail wmsInventoryDetail);

    List<WmsInventoryDetail> selectByInventoryPlan(String id);
}
