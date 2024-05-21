package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.WmsInventoryDetail;
import com.ruoyi.project.warehouse.domain.WmsInventoryPlan;
import com.ruoyi.project.warehouse.domain.WmsItemStorageDetail;

import java.util.List;

/**
 * 盘点详情Service接口
 *
 * @author Rem
 * @date 2023-05-21
 */
public interface IWmsInventoryDetailService {
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
    public AjaxResult insertWmsInventoryDetail(WmsInventoryDetail wmsInventoryDetail);

    /**
     * 修改盘点详情
     *
     * @param wmsInventoryDetail 盘点详情
     * @return 结果
     */
    public int updateWmsInventoryDetail(WmsInventoryDetail wmsInventoryDetail);

    /**
     * 批量删除盘点详情
     *
     * @param ids 需要删除的盘点详情主键集合
     * @return 结果
     */
    public int deleteWmsInventoryDetailByIds(String[] ids);

    /**
     * 删除盘点详情信息
     *
     * @param id 盘点详情主键
     * @return 结果
     */
    public int deleteWmsInventoryDetailById(String id);

    /**
     * 批量新增盘点详情
     *
     * @param wmsItemStorageDetails wms项存储细节
     * @param wmsInventoryPlan      wms库存计划
     */
    void generateInventoryTask(List<WmsItemStorageDetail> wmsItemStorageDetails, WmsInventoryPlan wmsInventoryPlan);

    /**
     * 查询盘点详情列表
     *
     * @param id id
     * @return {@link List}<{@link WmsInventoryDetail}>
     */
    List<WmsInventoryDetail> selectDetailByPlanId(String id);

    /**
     * 查询没有盘点的盘点详情
     *
     * @param id id
     * @return {@link List}<{@link WmsInventoryDetail}>
     */
    List<WmsInventoryDetail> selectNoCountedPlanId(String id);

    AjaxResult batchUpdateWmsInventoryDetail(List<WmsInventoryDetail> wmsInventoryDetailList);
}
