package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.WmsDeliveryNoticeDetail;

import java.util.List;

/**
 * 出库通知明细Service接口
 *
 * @author Rem
 * @date 2023-04-12
 */
public interface IWmsDeliveryNoticeDetailService {
    /**
     * 查询出库通知明细
     *
     * @param id 出库通知明细主键
     * @return 出库通知明细
     */
    public WmsDeliveryNoticeDetail selectWmsDeliveryNoticeDetailById(String id);

    /**
     * 查询出库通知明细列表
     *
     * @param wmsDeliveryNoticeDetail 出库通知明细
     * @return 出库通知明细集合
     */
    public List<WmsDeliveryNoticeDetail> selectWmsDeliveryNoticeDetailList(WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail);

    /**
     * 新增出库通知明细
     *
     * @param wmsDeliveryNoticeDetail 出库通知明细
     * @return 结果
     */
    public int insertWmsDeliveryNoticeDetail(WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail);

    /**
     * 修改出库通知明细
     *
     * @param wmsDeliveryNoticeDetail 出库通知明细
     * @return 结果
     */
    public int updateWmsDeliveryNoticeDetail(WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail);


    public int updateByNotice(WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail);

    /**
     * 批量删除出库通知明细
     *
     * @param ids 需要删除的出库通知明细主键集合
     * @return 结果
     */
    public int deleteWmsDeliveryNoticeDetailByIds(String[] ids);

    /**
     * 删除出库通知明细信息
     *
     * @param id 出库通知明细主键
     * @return 结果
     */
    public int deleteWmsDeliveryNoticeDetailById(String id);

    /**
     * 根据出库通知id查询出库通知明细
     *
     * @param id id
     * @return {@link List}<{@link WmsDeliveryNoticeDetail}>
     */
    List<WmsDeliveryNoticeDetail> selectByNoticeId(String id);

    /**
     * 取消分配
     *
     * @param ids id
     * @return {@link AjaxResult}
     */
    AjaxResult cancelAllot(String[] ids) throws Exception;
}
