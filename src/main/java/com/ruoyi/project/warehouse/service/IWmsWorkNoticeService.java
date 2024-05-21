package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.WmsWorkNotice;

import java.util.List;

/**
 * 作业通知Service接口
 *
 * @author Rem
 * @date 2023-04-07
 */
public interface IWmsWorkNoticeService {
    /**
     * 查询作业通知
     *
     * @param id 作业通知主键
     * @return 作业通知
     */
    public WmsWorkNotice selectWmsWorkNoticeById(String id);

    /**
     * 查询作业通知列表
     *
     * @param wmsWorkNotice 作业通知
     * @return 作业通知集合
     */
    public List<WmsWorkNotice> selectWmsWorkNoticeList(WmsWorkNotice wmsWorkNotice);

    /**
     * 新增作业通知
     *
     * @param wmsWorkNotice 作业通知
     * @return 结果
     */
    public int insertWmsWorkNotice(WmsWorkNotice wmsWorkNotice);

    /**
     * 修改作业通知
     *
     * @param wmsWorkNotice 作业通知
     * @return 结果
     */
    public int updateWmsWorkNotice(WmsWorkNotice wmsWorkNotice);

    /**
     * 批量删除作业通知
     *
     * @param ids 需要删除的作业通知主键集合
     * @return 结果
     */
    public int deleteWmsWorkNoticeByIds(String[] ids);

    /**
     * 删除作业通知信息
     *
     * @param id 作业通知主键
     * @return 结果
     */
    public int deleteWmsWorkNoticeById(String id);

    /**
     * 查询今日作业通知数量
     *
     * @return int
     */
    int selectWmsWorkNoticeCountByToday(Integer workType);


    /**
     * 退货
     *
     * @param ids id
     * @return int
     */
    int returns(String[] ids);

    /**
     * 作业完成
     *
     * @param wmsWorkNotice wms交货通知
     * @return int
     */
    AjaxResult completeWork(WmsWorkNotice wmsWorkNotice);

    /**
     * 取消作业
     *
     * @param wmsWorkNotice wms交货通知
     * @return int
     */
    AjaxResult cancelWork(WmsWorkNotice wmsWorkNotice) throws Exception;

    /**
     * 登记完成
     *
     * @param wmsWorkNotice wms交货通知
     * @return int
     */
    AjaxResult registerComplete(WmsWorkNotice wmsWorkNotice) throws Exception;

    /**
     * 根据关联id查询作业通知
     *
     * @param id id
     * @return {@link WmsWorkNotice}
     */
    List<WmsWorkNotice> selectWmsWorkNoticeByRelateId(String id);

    /**
     * 退货完成
     *
     * @param wmsWorkNotice 世界媒体峰会工作通知
     * @return int
     */
    AjaxResult returnComplete(WmsWorkNotice wmsWorkNotice);

    /**
     * 整单作业
     *
     * @param wmsWorkNotice 世界媒体峰会工作通知
     * @return int
     */
    AjaxResult wholeWork(WmsWorkNotice wmsWorkNotice);

    int updatePickWorkNoticeStatus(WmsWorkNotice wmsWorkNotice);
}
