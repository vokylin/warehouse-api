package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.WmsWorkNoticeAllot;
import com.ruoyi.project.warehouse.domain.WorkNoticeBatchSubmitAllotDto;

import java.util.List;

/**
 * 作业通知明细分配Service接口
 *
 * @author Rem
 * @date 2023-04-07
 */
public interface IWmsWorkNoticeAllotService {
    /**
     * 查询作业通知明细分配
     *
     * @param id 作业通知明细分配主键
     * @return 作业通知明细分配
     */
    public WmsWorkNoticeAllot selectWmsWorkNoticeAllotById(String id);

    /**
     * 查询作业通知明细分配列表
     *
     * @param wmsWorkNoticeAllot 作业通知明细分配
     * @return 作业通知明细分配集合
     */
    public List<WmsWorkNoticeAllot> selectWmsWorkNoticeAllotList(WmsWorkNoticeAllot wmsWorkNoticeAllot);

    /**
     * 新增作业通知明细分配
     *
     * @param wmsWorkNoticeAllot 作业通知明细分配
     * @return 结果
     */
    public int insertWmsWorkNoticeAllot(WmsWorkNoticeAllot wmsWorkNoticeAllot);

    /**
     * 修改作业通知明细分配
     *
     * @param wmsWorkNoticeAllot 作业通知明细分配
     * @return 结果
     */
    public int updateWmsWorkNoticeAllot(WmsWorkNoticeAllot wmsWorkNoticeAllot);

    /**
     * 批量删除作业通知明细分配
     *
     * @param ids 需要删除的作业通知明细分配主键集合
     * @return 结果
     */
    public int deleteWmsWorkNoticeAllotByIds(String[] ids);

    /**
     * 删除作业通知明细分配信息
     *
     * @param id 作业通知明细分配主键
     * @return 结果
     */
    public int deleteWmsWorkNoticeAllotById(String id);

    /**
     * 取消分配
     *
     * @param ids id
     * @return int
     */
    AjaxResult cancelWmsWorkNoticeAllotByIds(String[] ids);

    /**
     * 提交分配
     *
     * @param ids id
     * @return int
     */
    AjaxResult submitWmsWorkNoticeAllotByIds(WorkNoticeBatchSubmitAllotDto workNoticeBatchSubmitAllotDto);

    /**
     * 退货确认
     *
     * @param id id
     * @return int
     */
    AjaxResult refundConfirm(String id);

    WmsWorkNoticeAllot selectItemDetail(WmsWorkNoticeAllot wmsWorkNoticeAllotQuery);

    List<WmsWorkNoticeAllot> selectAllotListByDetailId(String id);

    void refundConfirm(WmsWorkNoticeAllot wmsWorkNoticeAllot);

    void batchUpdateStatus(String id, Integer status);
}
