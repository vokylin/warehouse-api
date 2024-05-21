package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.WmsReceiveNoticeDetail;

import java.util.List;

/**
 * 入库通知明细Service接口
 *
 * @author Rem
 * @date 2023-03-31
 */
public interface IWmsReceiveNoticeDetailService {
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
     * 批量删除入库通知明细
     *
     * @param ids 需要删除的入库通知明细主键集合
     * @return 结果
     */
    public int deleteWmsReceiveNoticeDetailByIds(String[] ids);

    /**
     * 删除入库通知明细信息
     *
     * @param id 入库通知明细主键
     * @return 结果
     */
    public int deleteWmsReceiveNoticeDetailById(String id);

    /**
     * 收货
     *
     * @param wmsReceiveNoticeDetail 收货通知详情
     * @return int
     */
    AjaxResult receipt(WmsReceiveNoticeDetail wmsReceiveNoticeDetail);

    /**
     * 分配
     *
     * @param wmsReceiveNoticeDetail 收货通知详情
     * @return {@link AjaxResult}
     */
    AjaxResult allot(WmsReceiveNoticeDetail wmsReceiveNoticeDetail);
}
