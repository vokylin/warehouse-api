package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.DeliverySureInfo;
import com.ruoyi.project.system.domain.WmsDeliverySure;
import com.ruoyi.project.system.service.IWmsDeliverySureService;
import com.ruoyi.project.warehouse.service.IWmsDeliveryNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出库确认记录Controller
 *
 * @author Rem
 * @date 2023-04-17
 */
@RestController
@RequestMapping("/system/deliverySure")
public class WmsDeliverySureController extends BaseController {
    @Autowired
    private IWmsDeliverySureService wmsDeliverySureService;

    @Autowired
    private IWmsDeliveryNoticeService wmsDeliveryNoticeService;

    /**
     * 查询出库确认记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmsDeliverySure wmsDeliverySure) {
        startPage();
        List<WmsDeliverySure> list = wmsDeliverySureService.selectWmsDeliverySureList(wmsDeliverySure);
        return getDataTable(list);
    }

    /**
     * 导出出库确认记录列表
     */
    @Log(title = "出库确认记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsDeliverySure wmsDeliverySure) {
        List<WmsDeliverySure> list = wmsDeliverySureService.selectWmsDeliverySureList(wmsDeliverySure);
        ExcelUtil<WmsDeliverySure> util = new ExcelUtil<WmsDeliverySure>(WmsDeliverySure.class);
        util.exportExcel(response, list, "出库确认记录数据");
    }

    /**
     * 获取出库确认记录详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsDeliverySureService.selectWmsDeliverySureById(id));
    }

    @GetMapping("/getDeliverySureInfo/{id}")
    public AjaxResult getDeliverySureInfo(@PathVariable("id") String id) {
        DeliverySureInfo deliverySureInfo = wmsDeliverySureService.getDeliverySureInfo(id);
        if (deliverySureInfo != null) {
            deliverySureInfo.setDeliveryNotice(wmsDeliveryNoticeService.deliveryConfirmInfo(deliverySureInfo.getDeliveryNoticeId()));
            return success(deliverySureInfo);
        } else {
            return AjaxResult.warn("该出库通知没有出库确认记录");
        }

    }

    /**
     * 新增出库确认记录
     */
    @Log(title = "出库确认记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsDeliverySure wmsDeliverySure) {
        return toAjax(wmsDeliverySureService.insertWmsDeliverySure(wmsDeliverySure));
    }

    /**
     * 修改出库确认记录
     */
    @Log(title = "出库确认记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsDeliverySure wmsDeliverySure) {
        return toAjax(wmsDeliverySureService.updateWmsDeliverySure(wmsDeliverySure));
    }

    /**
     * 删除出库确认记录
     */
    @Log(title = "出库确认记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsDeliverySureService.deleteWmsDeliverySureByIds(ids));
    }
}
