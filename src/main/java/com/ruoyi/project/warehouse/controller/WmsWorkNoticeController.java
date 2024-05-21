package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsWorkNotice;
import com.ruoyi.project.warehouse.service.IWmsWorkNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作业通知Controller
 *
 * @author Rem
 * @date 2023-04-07
 */
@RestController
@RequestMapping("/warehouse/workNotice")
public class WmsWorkNoticeController extends BaseController {
    @Autowired
    private IWmsWorkNoticeService wmsWorkNoticeService;

    /**
     * 查询作业通知列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmsWorkNotice wmsWorkNotice) {
        wmsWorkNotice.setCompanyId(getCompanyId());
        startPage();
        List<WmsWorkNotice> list = wmsWorkNoticeService.selectWmsWorkNoticeList(wmsWorkNotice);
        return getDataTable(list);
    }

    /**
     * 导出作业通知列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:workNotice:export')")
    @Log(title = "作业通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsWorkNotice wmsWorkNotice) {
        List<WmsWorkNotice> list = wmsWorkNoticeService.selectWmsWorkNoticeList(wmsWorkNotice);
        ExcelUtil<WmsWorkNotice> util = new ExcelUtil<WmsWorkNotice>(WmsWorkNotice.class);
        util.exportExcel(response, list, "作业通知数据");
    }

    /**
     * 获取作业通知详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsWorkNoticeService.selectWmsWorkNoticeById(id));
    }

    /**
     * 新增作业通知
     */
    @PreAuthorize("@ss.hasPermi('warehouse:workNotice:add')")
    @Log(title = "作业通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsWorkNotice wmsWorkNotice) {
        return toAjax(wmsWorkNoticeService.insertWmsWorkNotice(wmsWorkNotice));
    }

    /**
     * 修改作业通知
     */
    @PreAuthorize("@ss.hasPermi('warehouse:workNotice:edit')")
    @Log(title = "作业通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsWorkNotice wmsWorkNotice) {
        return toAjax(wmsWorkNoticeService.updateWmsWorkNotice(wmsWorkNotice));
    }

    /**
     * 删除作业通知
     */
    @PreAuthorize("@ss.hasPermi('warehouse:workNotice:remove')")
    @Log(title = "作业通知", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsWorkNoticeService.deleteWmsWorkNoticeByIds(ids));
    }

    /**
     * 整单作业
     */
    @Log(title = "作业通知", businessType = BusinessType.UPDATE)
    @PutMapping("/wholeWork")
    public AjaxResult wholeWork(@RequestBody WmsWorkNotice wmsWorkNotice) {
        return wmsWorkNoticeService.wholeWork(wmsWorkNotice);
    }

    /**
     * 退货
     */
    @Log(title = "作业通知", businessType = BusinessType.UPDATE)
    @PutMapping("/returnItems/{ids}")
    public AjaxResult returns(@PathVariable String[] ids) {
        return toAjax(wmsWorkNoticeService.returns(ids));
    }

    /**
     * 作业完成
     */
    @Log(title = "拣货作业", businessType = BusinessType.UPDATE)
    @PutMapping("/completeWork")
    public AjaxResult completeWork(@RequestBody WmsWorkNotice wmsWorkNotice) {
        return wmsWorkNoticeService.completeWork(wmsWorkNotice);
    }

    /**
     * 作业取消
     */
    @Log(title = "拣货作业", businessType = BusinessType.UPDATE)
    @PutMapping("/cancelWork")
    public AjaxResult cancelWork(@RequestBody WmsWorkNotice wmsWorkNotice) throws Exception {
        return wmsWorkNoticeService.cancelWork(wmsWorkNotice);
    }

    /**
     * 登记完成
     */
    @PreAuthorize("@ss.hasPermi('warehouse:pickWork:registerComplete')")
    @Log(title = "拣货作业", businessType = BusinessType.UPDATE)
    @PutMapping("/registerComplete")
    public AjaxResult registerComplete(@RequestBody WmsWorkNotice wmsWorkNotice) throws Exception {
        return wmsWorkNoticeService.registerComplete(wmsWorkNotice);
    }

    /**
     * 退货完成
     */
    @PreAuthorize("@ss.hasPermi('warehouse:refundWorkNotice:returnComplete')")
    @Log(title = "出库通知", businessType = BusinessType.UPDATE)
    @PutMapping("/returnComplete")
    public AjaxResult returnComplete(@RequestBody WmsWorkNotice wmsWorkNotice) {
        return wmsWorkNoticeService.returnComplete(wmsWorkNotice);
    }

}
