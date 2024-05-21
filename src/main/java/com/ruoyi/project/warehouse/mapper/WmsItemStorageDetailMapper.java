package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.StorageAlertVO;
import com.ruoyi.project.warehouse.domain.WmsItemStorageDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库存详情Mapper接口
 *
 * @author Rem
 * @date 2023-04-08
 */
public interface WmsItemStorageDetailMapper {
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

    /**
     * 修改库存详情
     *
     * @param wmsItemStorageDetail 库存详情
     * @return 结果
     */
    public int updateWmsItemStorageDetail(WmsItemStorageDetail wmsItemStorageDetail);

    int updateItemStorageByRelateId(WmsItemStorageDetail wmsItemStorageDetail);

    /**
     * 删除库存详情
     *
     * @param id 库存详情主键
     * @return 结果
     */
    public int deleteWmsItemStorageDetailById(String id);

    /**
     * 批量删除库存详情
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsItemStorageDetailByIds(String[] ids);

    List<WmsItemStorageDetail> selectWmsItemStorageDetailListByStorageId(@Param("storageId") String storageId, @Param("relateId") String relateId, @Param("workStatus") Integer workStatus, @Param("storageStatus") Integer storageStatus);

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
     * @param wmsItemStorageDetail wms项存储细节
     * @return {@link WmsItemStorageDetail}
     */
    WmsItemStorageDetail selectWmsItemStorageDetailByBatchNo(WmsItemStorageDetail wmsItemStorageDetail);

    /**
     * 根据出库通知单id查询库存详情
     *
     * @param id id
     */
    List<WmsItemStorageDetail> selectByDeliveryNoticeId(WmsItemStorageDetail wmsItemStorageDetail);

    /**
     * 查询所有临期货物
     *
     * @return
     */
    List<WmsItemStorageDetail> selectAdventItem();

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
     * 首页临期预警
     *
     * @return {@link List}<{@link StorageAlertVO}>
     */
    List<StorageAlertVO> selectStorageAlertList();

    List<WmsItemStorageDetail> checkStorageStatus(@Param("warehouseList") List<String> warehouseList, @Param("storageStatusList") List<String> storageStatusList);

    List<WmsItemStorageDetail> selectByInventoryPlan(@Param("warehouseList") List<String> warehouseList, @Param("storageStatusList") List<String> storageStatusList);

    /**
     * 更新关联状态
     *
     * @param storageDetail 存储细节
     */
    int updateRelateType(WmsItemStorageDetail storageDetail);

    List<WmsItemStorageDetail> selectBreakageList(WmsItemStorageDetail wmsItemStorageDetail);

    int updateAbnormalItem(String relateId);
}
