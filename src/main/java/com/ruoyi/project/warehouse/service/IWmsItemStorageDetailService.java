package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.StorageAlertVO;
import com.ruoyi.project.warehouse.domain.WmsItemStorageDetail;

import java.util.List;

/**
 * 库存详情Service接口
 *
 * @author Rem
 * @date 2023-04-08
 */
public interface IWmsItemStorageDetailService {
    /**
     * 查询库存详情
     *
     * @param id 库存详情主键
     * @return 库存详情
     */
    public WmsItemStorageDetail selectWmsItemStorageDetailById(String id);

    /**
     * 查询库存详情列表
     *
     * @param wmsItemStorageDetail 库存详情
     * @return 库存详情集合
     */
    public List<WmsItemStorageDetail> selectWmsItemStorageDetailList(WmsItemStorageDetail wmsItemStorageDetail);

    /**
     * 新增库存详情
     *
     * @param wmsItemStorageDetail 库存详情
     * @return 结果
     */
    public int insertWmsItemStorageDetail(WmsItemStorageDetail wmsItemStorageDetail);

    AjaxResult update(WmsItemStorageDetail wmsItemStorageDetail);

    /**
     * 修改库存详情
     *
     * @param wmsItemStorageDetail 库存详情
     * @return 结果
     */
    public int updateWmsItemStorageDetail(WmsItemStorageDetail wmsItemStorageDetail);

    /**
     * 批量删除库存详情
     *
     * @param ids 需要删除的库存详情主键集合
     * @return 结果
     */
    public int deleteWmsItemStorageDetailByIds(String[] ids);

    /**
     * 删除库存详情信息
     *
     * @param id 库存详情主键
     * @return 结果
     */
    public int deleteWmsItemStorageDetailById(String id);

    /**
     * 根据用户拥有的仓库权限查询库存详情
     *
     * @param wmsItemStorageDetail wms项存储细节
     * @return {@link List}<{@link WmsItemStorageDetail}>
     */
    List<WmsItemStorageDetail> selectWmsItemStorageDetailListByUser(WmsItemStorageDetail wmsItemStorageDetail);


    /**
     * 根据批次号查询库存详情
     *
     * @param selectDetail 选择细节
     * @return {@link WmsItemStorageDetail}
     */
    WmsItemStorageDetail selectWmsItemStorageDetailByBatchNo(WmsItemStorageDetail selectDetail);

    /**
     * 根据出库通知单id查询库存详情
     *
     * @param wmsItemStorageDetail wmsItemStorageDetail
     */
    List<WmsItemStorageDetail> selectByDeliveryNoticeId(WmsItemStorageDetail wmsItemStorageDetail);

    /**
     * 查询临期货物
     *
     * @return {@link List}<{@link WmsItemStorageDetail}>
     */
    List<WmsItemStorageDetail> selectAdventItem();

    /**
     * 首页临期预警
     *
     * @return {@link List}<{@link StorageAlertVO}>
     */
    List<StorageAlertVO> selectStorageAlertList();

    /**
     * 更新过期货物状态
     *
     * @return
     */
    int updateTheExpiredItemStatus();

    /**
     * 更新临期货物状态
     *
     * @return int
     */
    int updateAdventStatus(List<String> ids);

    /**
     * 临期状态检查
     *
     * @param wmsItemStorageDetail wms项存储细节
     */
    void setShelfLifeStatus(WmsItemStorageDetail wmsItemStorageDetail);

    List<WmsItemStorageDetail> checkStorageStatus(List<String> warehouseList, List<String> storageStatusList);

    List<WmsItemStorageDetail> selectByInventoryPlan(List<String> warehouseList, List<String> storageStatusList);

    /**
     * 更新关联状态
     *
     * @param storageDetail 存储细节
     */
    int updateRelateType(WmsItemStorageDetail storageDetail);

    /**
     * 查询报损列表
     *
     * @param wmsItemStorageDetail wms项存储细节
     * @return {@link List}<{@link WmsItemStorageDetail}>
     */
    List<WmsItemStorageDetail> selectBreakageList(WmsItemStorageDetail wmsItemStorageDetail);

    void updateAbnormalItem(String relateId);
}
