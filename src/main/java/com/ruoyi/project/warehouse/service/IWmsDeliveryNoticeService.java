package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.DeliveryNoticeInfo;
import com.ruoyi.project.warehouse.domain.WmsDeliveryNotice;

import java.util.List;

/**
 * 出库通知Service接口
 *
 * @author Rem
 * @date 2023-04-12
 */
public interface IWmsDeliveryNoticeService {
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
     * 批量删除出库通知
     *
     * @param ids 需要删除的出库通知主键集合
     * @return 结果
     */
    public int deleteWmsDeliveryNoticeByIds(String[] ids);

    /**
     * 删除出库通知信息
     *
     * @param id 出库通知主键
     * @return 结果
     */
    public int deleteWmsDeliveryNoticeById(String id);

    /**
     * 激活出库通知
     *
     * @param wmsDeliveryNotice wms交货通知
     * @return int
     */
    AjaxResult active(WmsDeliveryNotice wmsDeliveryNotice);

    /**
     * 取消激活出库通知
     *
     * @param wmsDeliveryNotice wms交货通知
     * @return int
     */
    AjaxResult inactive(WmsDeliveryNotice wmsDeliveryNotice);

    /**
     * 生成拣货作业
     *
     * @param wmsDeliveryNotice wms交货通知
     * @return int
     */
    AjaxResult pickWork(WmsDeliveryNotice wmsDeliveryNotice);


    /**
     * 收货确认信息
     *
     * @param id
     * @return
     */
    DeliveryNoticeInfo deliveryConfirmInfo(String id);

    int selectWmsDeliveryNoticeCountByToday();

    AjaxResult invalid(WmsDeliveryNotice wmsDeliveryNotice);
}
