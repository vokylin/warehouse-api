package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.WmsPickingWorkNoticeDetail;

import java.util.List;

/**
 * 拣货作业通知明细Service接口
 *
 * @author Rem
 * @date 2023-04-15
 */
public interface IWmsPickingWorkNoticeDetailService {
    /**
     * 查询拣货作业通知明细
     *
     * @param id 拣货作业通知明细主键
     * @return 拣货作业通知明细
     */
    public WmsPickingWorkNoticeDetail selectWmsPickingWorkNoticeDetailById(String id);


    /**
     * 查询拣货作业通知明细列表
     *
     * @param wmsPickingWorkNoticeDetail 拣货作业通知明细
     * @return 拣货作业通知明细集合
     */
    public List<WmsPickingWorkNoticeDetail> selectWmsPickingWorkNoticeDetailList(WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail);

    /**
     * 新增拣货作业通知明细
     *
     * @param wmsPickingWorkNoticeDetail 拣货作业通知明细
     * @return 结果
     */
    public int insertWmsPickingWorkNoticeDetail(WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail);

    /**
     * 修改拣货作业通知明细
     *
     * @param wmsPickingWorkNoticeDetail 拣货作业通知明细
     * @return 结果
     */
    public int updateWmsPickingWorkNoticeDetail(WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail);

    /**
     * 更新拣货作业状态
     *
     * @param wmsPickingWorkNoticeDetail wms挑选工作注意细节
     * @return int
     */
    int updateWorkNoticeDetailStatus(WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail);

    /**
     * 批量删除拣货作业通知明细
     *
     * @param ids 需要删除的拣货作业通知明细主键集合
     * @return 结果
     */
    public int deleteWmsPickingWorkNoticeDetailByIds(String[] ids);

    /**
     * 删除拣货作业通知明细信息
     *
     * @param id 拣货作业通知明细主键
     * @return 结果
     */
    public int deleteWmsPickingWorkNoticeDetailById(String id);


    /**
     * 缺货登记
     *
     * @param wmsPickingWorkNoticeDetail wms项存储细节
     * @return {@link AjaxResult}
     */
    AjaxResult shortageCheckIn(WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail);

    /**
     * 根据出库通知id查询明细
     *
     * @param id id
     * @return {@link List}<{@link WmsPickingWorkNoticeDetail}>
     */
    List<WmsPickingWorkNoticeDetail> selectDetailByDeliveryNoticeId(String id);

    /**
     * 根据出库通知明细id查询明细
     *
     * @param id id
     * @return {@link List}<{@link WmsPickingWorkNoticeDetail}>
     */
    List<WmsPickingWorkNoticeDetail> selectDetailByDeliveryNoticeDetailId(String id);
}
