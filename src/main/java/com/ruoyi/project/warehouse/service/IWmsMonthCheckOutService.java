package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.WmsMonthCheckOut;

import java.util.List;

/**
 * 月结Service接口
 *
 * @author gaomian
 * @date 2023-05-27
 */
public interface IWmsMonthCheckOutService {
    /**
     * 查询月结列表
     *
     * @param wmsMonthCheckOut 月结
     * @return 月结集合
     */
    List<WmsMonthCheckOut> selectWmsMonthCheckOutList(WmsMonthCheckOut wmsMonthCheckOut);

    /**
     * 检查是否还有未完成的出入库通知
     *
     * @param month
     * @return
     */
    AjaxResult checkNoticeMonth(String month);

    /**
     * 获取最新的月结月份
     *
     * @return
     */
    AjaxResult getCheckOutMonth();


    /**
     * 月结
     *
     * @param wmsMonthCheckOut
     * @return
     */
    AjaxResult monthSettlement(WmsMonthCheckOut wmsMonthCheckOut);

    /**
     * 反月结
     *
     * @param wmsMonthCheckOut
     * @return
     */
    AjaxResult reverseSettlement(WmsMonthCheckOut wmsMonthCheckOut);
}
