package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.WmsExamineWork;

import java.util.List;

/**
 * 质检作业Service接口
 *
 * @author Rem
 * @date 2023-04-07
 */
public interface IWmsExamineWorkService {
    /**
     * 查询质检作业
     *
     * @param id 质检作业主键
     * @return 质检作业
     */
    public WmsExamineWork selectWmsExamineWorkById(String id);

    /**
     * 查询质检作业列表
     *
     * @param wmsExamineWork 质检作业
     * @return 质检作业集合
     */
    public List<WmsExamineWork> selectWmsExamineWorkList(WmsExamineWork wmsExamineWork);

    /**
     * 新增质检作业
     *
     * @param wmsExamineWork 质检作业
     * @return 结果
     */
    public int insertWmsExamineWork(WmsExamineWork wmsExamineWork);

    /**
     * 修改质检作业
     *
     * @param wmsExamineWork 质检作业
     * @return 结果
     */
    public int updateWmsExamineWork(WmsExamineWork wmsExamineWork);

    /**
     * 批量删除质检作业
     *
     * @param ids 需要删除的质检作业主键集合
     * @return 结果
     */
    public int deleteWmsExamineWorkByIds(String[] ids);

    /**
     * 删除质检作业信息
     *
     * @param id 质检作业主键
     * @return 结果
     */
    public int deleteWmsExamineWorkById(String id);

    /**
     * 通知质检
     *
     * @param wmsExamineWork wms检查工作
     * @return int
     */
    AjaxResult noticeExamine(WmsExamineWork wmsExamineWork) throws Exception;

    /**
     * 统计今日质检作业数量
     *
     * @return int
     */
    int selectWmsExamineWorkCountByToday();

    /**
     * 退货作业
     *
     * @param wmsExamineWork wms检查工作
     * @return int
     */
    AjaxResult refundWork(WmsExamineWork wmsExamineWork);

    /**
     * 上架作业
     *
     * @param wmsExamineWork wms检查工作
     * @return int
     */
    AjaxResult shelvesWork(WmsExamineWork wmsExamineWork);

    /**
     * 根据收货明细id查询质检作业
     *
     * @param id id
     * @return {@link WmsExamineWork}
     */
    WmsExamineWork selectByReceiveItemId(String id);

    WmsExamineWork selectByReceiveNoticeId(String id);
}
