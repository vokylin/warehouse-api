package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.WmsWorkNoticeDetail;

import java.util.List;

/**
 * 作业通知明细Service接口
 *
 * @author Rem
 * @date 2023-04-07
 */
public interface IWmsWorkNoticeDetailService {
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
     * 批量删除作业通知明细
     *
     * @param ids 需要删除的作业通知明细主键集合
     * @return 结果
     */
    public int deleteWmsWorkNoticeDetailByIds(String[] ids);

    /**
     * 删除作业通知明细信息
     *
     * @param id 作业通知明细主键
     * @return 结果
     */
    public int deleteWmsWorkNoticeDetailById(String id);

    /**
     * 分配
     *
     * @param wmsWorkNoticeDetail 世界媒体峰会工作注意细节
     * @return int
     */
    AjaxResult allot(WmsWorkNoticeDetail wmsWorkNoticeDetail);

    /**
     * 退货完成
     *
     * @param wmsWorkNoticeDetail 世界媒体峰会工作注意细节
     * @return int
     */
    AjaxResult returnComplete(WmsWorkNoticeDetail wmsWorkNoticeDetail);

    /**
     * 取消分配
     *
     * @param wmsWorkNoticeDetail 世界媒体峰会工作注意细节
     * @return {@link AjaxResult}
     */
    AjaxResult cancelAllot(String[] ids) throws Exception;

    /**
     * 提交分配
     *
     * @param ids id
     * @return {@link AjaxResult}
     */
    AjaxResult submitAllot(String[] ids);

    List<WmsWorkNoticeDetail> selectByWorkNoticeId(String id);
}
