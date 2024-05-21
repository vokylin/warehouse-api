package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.WmsReceiveNotice;

import java.util.List;

/**
 * 入库通知Service接口
 *
 * @author Rem
 * @date 2023-03-31
 */
public interface IWmsReceiveNoticeService {
    /**
     * 查询入库通知
     *
     * @param id 入库通知主键
     * @return 入库通知
     */
    public WmsReceiveNotice selectWmsReceiveNoticeById(String id);

    /**
     * 查询入库通知列表
     *
     * @param wmsReceiveNotice 入库通知
     * @return 入库通知集合
     */
    public List<WmsReceiveNotice> selectWmsReceiveNoticeList(WmsReceiveNotice wmsReceiveNotice);

    /**
     * 新增入库通知
     *
     * @param wmsReceiveNotice 入库通知
     * @return 结果
     */
    public int insertWmsReceiveNotice(WmsReceiveNotice wmsReceiveNotice);

    /**
     * 修改入库通知
     *
     * @param wmsReceiveNotice 入库通知
     * @return 结果
     */
    public int updateWmsReceiveNotice(WmsReceiveNotice wmsReceiveNotice);

    /**
     * 批量删除入库通知
     *
     * @param ids 需要删除的入库通知主键集合
     * @return 结果
     */
    public int deleteWmsReceiveNoticeByIds(String[] ids);

    /**
     * 删除入库通知信息
     *
     * @param id 入库通知主键
     * @return 结果
     */
    public int deleteWmsReceiveNoticeById(String id);

    /**
     * 批量激活入库通知
     *
     * @param ids       id
     * @param startTime 开始时间
     * @return int
     */
    int active(WmsReceiveNotice wmsReceiveNotice);

    /**
     * 查询当天通知数量
     */
    int selectWmsReceiveNoticeCountByToday();

    /**
     * 作废
     *
     * @param wmsReceiveNotice wms收到通知
     * @return int
     */
    AjaxResult invalid(WmsReceiveNotice wmsReceiveNotice) throws Exception;

    /**
     * 反激活
     *
     * @param wmsReceiveNotice wms收到通知
     * @return int
     */
    AjaxResult inactive(String[] ids);

    /**
     * 收货确认
     *
     * @param ids 世界媒体峰会工作通知
     * @return int
     */
    AjaxResult confirm(String[] ids) throws Exception;
}
