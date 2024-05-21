package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsPickingWorkNoticeDetail;

import java.util.List;

/**
 * 拣货作业通知明细Mapper接口
 *
 * @author Rem
 * @date 2023-04-15
 */
public interface WmsPickingWorkNoticeDetailMapper {
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
     * 删除拣货作业通知明细
     *
     * @param id 拣货作业通知明细主键
     * @return 结果
     */
    public int deleteWmsPickingWorkNoticeDetailById(String id);

    /**
     * 批量删除拣货作业通知明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsPickingWorkNoticeDetailByIds(String[] ids);

    List<WmsPickingWorkNoticeDetail> selectByWorkNoticeId(String id);

    List<WmsPickingWorkNoticeDetail> selectDetailByDeliveryNoticeId(String id);

    List<WmsPickingWorkNoticeDetail> selectDetailByDeliveryNoticeDetailId(String id);

    /**
     * 更新工作注意细节状态
     *
     * @param wmsPickingWorkNoticeDetail wms挑选工作注意细节
     * @return int
     */
    int updateWorkNoticeDetailStatus(WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail);
}
