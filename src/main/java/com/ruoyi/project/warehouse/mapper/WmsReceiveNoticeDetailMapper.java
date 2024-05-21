package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsReceiveNoticeDetail;

import java.util.List;

/**
 * 入库通知明细Mapper接口
 *
 * @author Rem
 * @date 2023-03-31
 */
public interface WmsReceiveNoticeDetailMapper {
    /**
     * 查询入库通知明细
     *
     * @param id 入库通知明细主键
     * @return 入库通知明细
     */
    public WmsReceiveNoticeDetail selectWmsReceiveNoticeDetailById(String id);

    /**
     * 查询入库通知明细列表
     *
     * @param wmsReceiveNoticeDetail 入库通知明细
     * @return 入库通知明细集合
     */
    public List<WmsReceiveNoticeDetail> selectWmsReceiveNoticeDetailList(WmsReceiveNoticeDetail wmsReceiveNoticeDetail);

    /**
     * 根据通知ID 查询通知详情
     *
     * @param noticeId 通知ID
     * @return {@link List}<{@link WmsReceiveNoticeDetail}>
     */
    List<WmsReceiveNoticeDetail> selectReceiveNoticeDetailListByNoticeId(String noticeId);

    /**
     * 新增入库通知明细
     *
     * @param wmsReceiveNoticeDetail 入库通知明细
     * @return 结果
     */
    public int insertWmsReceiveNoticeDetail(WmsReceiveNoticeDetail wmsReceiveNoticeDetail);

    /**
     * 修改入库通知明细
     *
     * @param wmsReceiveNoticeDetail 入库通知明细
     * @return 结果
     */
    public int updateWmsReceiveNoticeDetail(WmsReceiveNoticeDetail wmsReceiveNoticeDetail);

    /**
     * 删除入库通知明细
     *
     * @param id 入库通知明细主键
     * @return 结果
     */
    public int deleteWmsReceiveNoticeDetailById(String id);

    /**
     * 批量删除入库通知明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsReceiveNoticeDetailByIds(String[] ids);
}
