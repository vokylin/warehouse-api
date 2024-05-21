package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.WmsExamineWork;
import com.ruoyi.project.warehouse.domain.WmsReceiveItemDetail;

import java.util.List;

/**
 * 入库货物明细Service接口
 *
 * @author Rem
 * @date 2023-03-31
 */
public interface IWmsReceiveItemDetailService {
    /**
     * 查询入库货物明细
     *
     * @param id 入库货物明细主键
     * @return 入库货物明细
     */
    public WmsReceiveItemDetail selectWmsReceiveItemDetailById(String id);

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
     * 批量删除入库货物明细
     *
     * @param ids 需要删除的入库货物明细主键集合
     * @return 结果
     */
    public int deleteWmsReceiveItemDetailByIds(String[] ids);

    /**
     * 删除入库货物明细信息
     *
     * @param id 入库货物明细主键
     * @return 结果
     */
    public int deleteWmsReceiveItemDetailById(String id);

    /**
     * 上架
     *
     * @param wmsReceiveItemDetail 收货通知详情
     * @return int
     */
    AjaxResult shelves(WmsReceiveItemDetail wmsReceiveItemDetail);

    /**
     * 质检上架
     *
     * @param wmsExamineWork
     * @return
     */
    AjaxResult shelves(WmsExamineWork wmsExamineWork);


    /**
     * 取消
     *
     * @param wmsReceiveItemDetail wms收到项目细节
     * @return int
     */
    AjaxResult cancel(WmsReceiveItemDetail wmsReceiveItemDetail);


    /**
     * 取消全部
     *
     * @param wmsReceiveItemDetail wms收到项目细节
     * @return int
     */
    AjaxResult cancelAll(WmsReceiveItemDetail wmsReceiveItemDetail);

    /**
     * 质检
     *
     * @param wmsReceiveItemDetail wms收到项目细节
     * @return int
     */
    AjaxResult qualityCheck(WmsReceiveItemDetail wmsReceiveItemDetail);

    /**
     * 批量上架
     *
     * @param receiveItemDto
     * @return int
     */
    int batchShelves(WmsReceiveItemDetail receiveItemDto) throws Exception;
}
