package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.common.component.DeliveryNoticeManager;
import com.ruoyi.project.warehouse.domain.DeliveryConfirmInfo;
import com.ruoyi.project.warehouse.domain.WmsDeliveryNotice;
import com.ruoyi.project.warehouse.service.IWmsDeliveryNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出库通知Controller
 *
 * @author Rem
 * @date 2023-04-12
 */
@RestController
@RequestMapping("/warehouse/deliveryNotice")
public class WmsDeliveryNoticeController extends BaseController {
    @Autowired
    private IWmsDeliveryNoticeService wmsDeliveryNoticeService;

    @Autowired
    private DeliveryNoticeManager deliveryNoticeManager;

    /**
     * 查询出库通知列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNotice:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsDeliveryNotice wmsDeliveryNotice) {
        wmsDeliveryNotice.setCompanyId(getCompanyId());
        startPage();
        List<WmsDeliveryNotice> list = wmsDeliveryNoticeService.selectWmsDeliveryNoticeList(wmsDeliveryNotice);
        return getDataTable(list);
    }

    /**
     * 导出出库通知列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNotice:export')")
    @Log(title = "出库通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsDeliveryNotice wmsDeliveryNotice) {
        List<WmsDeliveryNotice> list = wmsDeliveryNoticeService.selectWmsDeliveryNoticeList(wmsDeliveryNotice);
        ExcelUtil<WmsDeliveryNotice> util = new ExcelUtil<WmsDeliveryNotice>(WmsDeliveryNotice.class);
        util.exportExcel(response, list, "出库通知数据");
    }

    /**
     * 获取出库通知详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsDeliveryNoticeService.selectWmsDeliveryNoticeById(id));
    }

    /**
     * 新增出库通知
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNotice:add')")
    @Log(title = "出库通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsDeliveryNotice wmsDeliveryNotice) {
        return toAjax(wmsDeliveryNoticeService.insertWmsDeliveryNotice(wmsDeliveryNotice));
    }

    /**
     * 修改出库通知
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNotice:edit')")
    @Log(title = "出库通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsDeliveryNotice wmsDeliveryNotice) {
        return toAjax(wmsDeliveryNoticeService.updateWmsDeliveryNotice(wmsDeliveryNotice));
    }

    /**
     * 删除出库通知
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNotice:remove')")
    @Log(title = "出库通知", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsDeliveryNoticeService.deleteWmsDeliveryNoticeByIds(ids));
    }

    /**
     * 激活
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNotice:active')")
    @Log(title = "出库通知", businessType = BusinessType.UPDATE)
    @PutMapping("/active")
    public AjaxResult active(@RequestBody WmsDeliveryNotice wmsDeliveryNotice) {
        return wmsDeliveryNoticeService.active(wmsDeliveryNotice);
    }


    /**
     * 反激活
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNotice:inactive')")
    @Log(title = "出库通知", businessType = BusinessType.UPDATE)
    @PutMapping("/inactive")
    public AjaxResult inactive(@RequestBody WmsDeliveryNotice wmsDeliveryNotice) {
        return wmsDeliveryNoticeService.inactive(wmsDeliveryNotice);
    }

    /**
     * 生成拣货作业单
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNotice:pickWork')")
    @Log(title = "出库通知", businessType = BusinessType.UPDATE)
    @PutMapping("/pickWork")
    public AjaxResult pickWork(@RequestBody WmsDeliveryNotice wmsDeliveryNotice) {
        return wmsDeliveryNoticeService.pickWork(wmsDeliveryNotice);
    }


    /**
     * 出库确认
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNotice:deliveryConfirm')")
    @Log(title = "出库通知", businessType = BusinessType.UPDATE)
    @PutMapping("/deliveryConfirm")
    public AjaxResult deliveryConfirm(@RequestBody DeliveryConfirmInfo deliveryConfirmInfo) throws Exception {
        return deliveryNoticeManager.deliveryConfirm(deliveryConfirmInfo);
    }

    /**
     * 出库确认信息
     */
    @GetMapping("/deliveryConfirmInfo/{id}")
    public AjaxResult deliveryConfirmInfo(@PathVariable("id") String id) {
        return AjaxResult.success(wmsDeliveryNoticeService.deliveryConfirmInfo(id));
    }

    /**
     * 作废
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNotice:invalid')")
    @Log(title = "出库通知", businessType = BusinessType.UPDATE)
    @PutMapping("/invalid")
    public AjaxResult invalid(@RequestBody WmsDeliveryNotice wmsDeliveryNotice) {
        return wmsDeliveryNoticeService.invalid(wmsDeliveryNotice);
    }


}
