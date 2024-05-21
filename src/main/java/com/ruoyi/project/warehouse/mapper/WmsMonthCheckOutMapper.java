package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsMonthCheckOut;

import java.util.List;

/**
 * 月结Mapper接口
 *
 * @author gaomian
 * @date 2023-05-27
 */
public interface WmsMonthCheckOutMapper {

    /**
     * 查询月结列表
     *
     * @param wmsMonthCheckOut 月结
     * @return 月结集合
     */
    List<WmsMonthCheckOut> selectWmsMonthCheckOutList(WmsMonthCheckOut wmsMonthCheckOut);


    /**
     * 获取物料信息
     *
     * @return
     */
    List<WmsMonthCheckOut> findItemInfo();

    /**
     * 获取某月的月结数量
     *
     * @param wmsMonthCheckOut
     * @return
     */
    int findCheckOut(WmsMonthCheckOut wmsMonthCheckOut);

    /**
     * 批量新增月结数据
     *
     * @param monthCheckOutList
     */
    void insertBatch(List<WmsMonthCheckOut> monthCheckOutList);


    /**
     * 反月结
     *
     * @param wmsMonthCheckOut
     */
    void reverseSettlement(WmsMonthCheckOut wmsMonthCheckOut);

    /**
     * 获取最近的月结月份
     *
     * @return
     */
    String getLastCheckOutMonth();

}
