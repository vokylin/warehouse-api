package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsMonthCheckOut;
import com.ruoyi.project.warehouse.service.IWmsMonthCheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 月结Controller
 *
 * @author gaomian
 * @date 2023-05-27
 */
@RestController
@RequestMapping("/warehouse/monthCheckOut")
public class WmsMonthCheckOutController extends BaseController {
    @Autowired
    private IWmsMonthCheckOutService wmsMonthCheckOutService;

    /**
     * 查询月结列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:monthCheckOut:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsMonthCheckOut wmsMonthCheckOut) {
        startPage();
        List<WmsMonthCheckOut> list = wmsMonthCheckOutService.selectWmsMonthCheckOutList(wmsMonthCheckOut);
        return getDataTable(list);
    }

    /**
     * 月结前检查月结月份的出/入库通知是否完成
     *
     * @param month
     * @return
     */
    @GetMapping("/checkNotice/{month}")
    public AjaxResult checkNoticeMonth(@PathVariable String month) {
        return wmsMonthCheckOutService.checkNoticeMonth(month);
    }

    /**
     * 获取最新的月结月份
     *
     * @return
     */
    @GetMapping("/getCheckOutMonth")
    public AjaxResult getCheckOutMonth() {
        return wmsMonthCheckOutService.getCheckOutMonth();
    }

    /**
     * 月结
     */
    @PreAuthorize("@ss.hasPermi('warehouse:monthCheckOut:settlement')")
    @Log(title = "月结", businessType = BusinessType.INSERT)
    @PostMapping("/settlement")
    public AjaxResult monthSettlement(@RequestBody WmsMonthCheckOut wmsMonthCheckOut) {
        return wmsMonthCheckOutService.monthSettlement(wmsMonthCheckOut);
    }

    /**
     * 反月结
     */
    @PreAuthorize("@ss.hasPermi('warehouse:monthCheckOut:reverse')")
    @Log(title = "反月结", businessType = BusinessType.UPDATE)
    @PostMapping("/reverse")
    public AjaxResult reverseSettlement(@RequestBody WmsMonthCheckOut wmsMonthCheckOut) {
        return wmsMonthCheckOutService.reverseSettlement(wmsMonthCheckOut);
    }

    /**
     * 导出月结列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:monthCheckOut:export')")
    @Log(title = "月结", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsMonthCheckOut wmsMonthCheckOut) {
        List<WmsMonthCheckOut> list = wmsMonthCheckOutService.selectWmsMonthCheckOutList(wmsMonthCheckOut);
        ExcelUtil<WmsMonthCheckOut> util = new ExcelUtil<WmsMonthCheckOut>(WmsMonthCheckOut.class);
        util.exportExcel(response, list, "月结数据");
    }

}
