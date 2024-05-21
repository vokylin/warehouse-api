package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsDeliveryNoticeDetail;
import com.ruoyi.project.warehouse.service.IWmsDeliveryNoticeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出库通知明细Controller
 *
 * @author Rem
 * @date 2023-04-12
 */
@RestController
@RequestMapping("/warehouse/deliveryNoticeDetail")
public class WmsDeliveryNoticeDetailController extends BaseController {
    @Autowired
    private IWmsDeliveryNoticeDetailService wmsDeliveryNoticeDetailService;

    /**
     * 查询出库通知明细列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail) {
        startPage();
        List<WmsDeliveryNoticeDetail> list = wmsDeliveryNoticeDetailService.selectWmsDeliveryNoticeDetailList(wmsDeliveryNoticeDetail);
        return getDataTable(list);
    }

    /**
     * 导出出库通知明细列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNoticeDetail:export')")
    @Log(title = "出库通知明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail) {
        List<WmsDeliveryNoticeDetail> list = wmsDeliveryNoticeDetailService.selectWmsDeliveryNoticeDetailList(wmsDeliveryNoticeDetail);
        ExcelUtil<WmsDeliveryNoticeDetail> util = new ExcelUtil<WmsDeliveryNoticeDetail>(WmsDeliveryNoticeDetail.class);
        util.exportExcel(response, list, "出库通知明细数据");
    }

    /**
     * 获取出库通知明细详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsDeliveryNoticeDetailService.selectWmsDeliveryNoticeDetailById(id));
    }

    /**
     * 新增出库通知明细
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNoticeDetail:add')")
    @Log(title = "出库通知明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail) {
        return toAjax(wmsDeliveryNoticeDetailService.insertWmsDeliveryNoticeDetail(wmsDeliveryNoticeDetail));
    }

    /**
     * 修改出库通知明细
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNoticeDetail:edit')")
    @Log(title = "出库通知明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail) {
        return toAjax(wmsDeliveryNoticeDetailService.updateWmsDeliveryNoticeDetail(wmsDeliveryNoticeDetail));
    }

    /**
     * 删除出库通知明细
     */
    @PreAuthorize("@ss.hasPermi('warehouse:deliveryNoticeDetail:remove')")
    @Log(title = "出库通知明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsDeliveryNoticeDetailService.deleteWmsDeliveryNoticeDetailByIds(ids));
    }

    /**
     * 取消分配
     */
    @Log(title = "出库通知明细", businessType = BusinessType.UPDATE)
    @PutMapping("/cancelAllot/{ids}")
    public AjaxResult cancelAllot(@PathVariable String[] ids) throws Exception {
        return wmsDeliveryNoticeDetailService.cancelAllot(ids);
    }

}
