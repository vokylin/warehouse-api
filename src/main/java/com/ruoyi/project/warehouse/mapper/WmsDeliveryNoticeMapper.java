package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.DeliveryNoticeInfo;
import com.ruoyi.project.warehouse.domain.WmsDeliveryNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出库通知Mapper接口
 *
 * @author Rem
 * @date 2023-04-12
 */
public interface WmsDeliveryNoticeMapper {
    /**
     * 查询出库通知
     *
     * @param id 出库通知主键
     * @return 出库通知
     */
    public WmsDeliveryNotice selectWmsDeliveryNoticeById(String id);

    /**
     * 查询出库通知列表
     *
     * @param wmsDeliveryNotice 出库通知
     * @return 出库通知集合
     */
    public List<WmsDeliveryNotice> selectWmsDeliveryNoticeList(WmsDeliveryNotice wmsDeliveryNotice);

    /**
     * 新增出库通知
     *
     * @param wmsDeliveryNotice 出库通知
     * @return 结果
     */
    public int insertWmsDeliveryNotice(WmsDeliveryNotice wmsDeliveryNotice);

    /**
     * 修改出库通知
     *
     * @param wmsDeliveryNotice 出库通知
     * @return 结果
     */
    public int updateWmsDeliveryNotice(WmsDeliveryNotice wmsDeliveryNotice);

    /**
     * 删除出库通知
     *
     * @param id 出库通知主键
     * @return 结果
     */
    public int deleteWmsDeliveryNoticeById(String id);

    /**
     * 批量删除出库通知
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsDeliveryNoticeByIds(String[] ids);

    /**
     * 反激活
     *
     * @param wmsDeliveryNotice wms交货通知
     * @return int
     */
    int inactive(WmsDeliveryNotice wmsDeliveryNotice);

    /**
     * 收货确认信息
     *
     * @param id id
     * @return {@link DeliveryNoticeInfo}
     */
    DeliveryNoticeInfo deliveryConfirmInfo(String id);

    int selectWmsDeliveryNoticeCountByToday();

    /**
     * 查询某月未完成的出库通知数量
     *
     * @return
     */
    int selectUnfinishedDeliveryNotice(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
