package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsReceiveItemDetail;

import java.util.List;

/**
 * 入库货物明细Mapper接口
 *
 * @author Rem
 * @date 2023-03-31
 */
public interface WmsReceiveItemDetailMapper {
    /**
     * 查询入库货物明细
     *
     * @param id 入库货物明细主键
     * @return 入库货物明细
     */
    public WmsReceiveItemDetail selectWmsReceiveItemDetailById(String id);

    /**
     * 根据收到通知id查询入库货物明细
     *
     * @param receiveNoticeId 收到通知id
     * @return {@link List}<{@link WmsReceiveItemDetail}>
     */
    public List<WmsReceiveItemDetail> selectWmsReceiveItemDetailByReceiveNoticeId(String receiveNoticeId);

    /**
     * 查询入库货物明细列表
     *
     * @param wmsReceiveItemDetail 入库货物明细
     * @return 入库货物明细集合
     */
    public List<WmsReceiveItemDetail> selectWmsReceiveItemDetailList(WmsReceiveItemDetail wmsReceiveItemDetail);

    /**
     * 新增入库货物明细
     *
     * @param wmsReceiveItemDetail 入库货物明细
     * @return 结果
     */
    public int insertWmsReceiveItemDetail(WmsReceiveItemDetail wmsReceiveItemDetail);

    /**
     * 修改入库货物明细
     *
     * @param wmsReceiveItemDetail 入库货物明细
     * @return 结果
     */
    public int updateWmsReceiveItemDetail(WmsReceiveItemDetail wmsReceiveItemDetail);

    /**
     * 删除入库货物明细
     *
     * @param id 入库货物明细主键
     * @return 结果
     */
    public int deleteWmsReceiveItemDetailById(String id);

    /**
     * 批量删除入库货物明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsReceiveItemDetailByIds(String[] ids);

    /**
     * 查询入库货物明细
     *
     * @param wmsReceiveItemDetail wmsReceiveItemDetail
     * @return {@link WmsReceiveItemDetail}
     */
    WmsReceiveItemDetail selectReceiveItemDetail(WmsReceiveItemDetail wmsReceiveItemDetail);
}
