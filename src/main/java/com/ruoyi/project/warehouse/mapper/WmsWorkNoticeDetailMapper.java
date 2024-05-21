package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsWorkNoticeDetail;

import java.util.List;

/**
 * 作业通知明细Mapper接口
 *
 * @author Rem
 * @date 2023-04-07
 */
public interface WmsWorkNoticeDetailMapper {
    /**
     * 查询作业通知明细
     *
     * @param id 作业通知明细主键
     * @return 作业通知明细
     */
    public WmsWorkNoticeDetail selectWmsWorkNoticeDetailById(String id);

    /**
     * 查询作业通知明细列表
     *
     * @param wmsWorkNoticeDetail 作业通知明细
     * @return 作业通知明细集合
     */
    public List<WmsWorkNoticeDetail> selectWmsWorkNoticeDetailList(WmsWorkNoticeDetail wmsWorkNoticeDetail);

    /**
     * 新增作业通知明细
     *
     * @param wmsWorkNoticeDetail 作业通知明细
     * @return 结果
     */
    public int insertWmsWorkNoticeDetail(WmsWorkNoticeDetail wmsWorkNoticeDetail);

    /**
     * 修改作业通知明细
     *
     * @param wmsWorkNoticeDetail 作业通知明细
     * @return 结果
     */
    public int updateWmsWorkNoticeDetail(WmsWorkNoticeDetail wmsWorkNoticeDetail);

    /**
     * 删除作业通知明细
     *
     * @param id 作业通知明细主键
     * @return 结果
     */
    public int deleteWmsWorkNoticeDetailById(String id);

    /**
     * 批量删除作业通知明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsWorkNoticeDetailByIds(String[] ids);

    List<WmsWorkNoticeDetail> selectByWorkNoticeId(String id);

    Integer checkAllotNotFinish(String workNoticeId);

    Integer checkAllotFinish(String workNoticeId);
}
