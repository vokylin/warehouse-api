package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.WmsDeliverySureDetail;
import com.ruoyi.project.system.service.IWmsDeliverySureDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出库确认记录详情Controller
 *
 * @author Rem
 * @date 2023-04-17
 */
@RestController
@RequestMapping("/system/deliverySureDetail")
public class WmsDeliverySureDetailController extends BaseController {
    @Autowired
    private IWmsDeliverySureDetailService wmsDeliverySureDetailService;

    /**
     * 查询出库确认记录详情列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmsDeliverySureDetail wmsDeliverySureDetail) {
        startPage();
        List<WmsDeliverySureDetail> list = wmsDeliverySureDetailService.selectWmsDeliverySureDetailList(wmsDeliverySureDetail);
        return getDataTable(list);
    }

    /**
     * 导出出库确认记录详情列表
     */
    @Log(title = "出库确认记录详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsDeliverySureDetail wmsDeliverySureDetail) {
        List<WmsDeliverySureDetail> list = wmsDeliverySureDetailService.selectWmsDeliverySureDetailList(wmsDeliverySureDetail);
        ExcelUtil<WmsDeliverySureDetail> util = new ExcelUtil<WmsDeliverySureDetail>(WmsDeliverySureDetail.class);
        util.exportExcel(response, list, "出库确认记录详情数据");
    }

    /**
     * 获取出库确认记录详情详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsDeliverySureDetailService.selectWmsDeliverySureDetailById(id));
    }

    /**
     * 新增出库确认记录详情
     */
    @Log(title = "出库确认记录详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsDeliverySureDetail wmsDeliverySureDetail) {
        return toAjax(wmsDeliverySureDetailService.insertWmsDeliverySureDetail(wmsDeliverySureDetail));
    }

    /**
     * 修改出库确认记录详情
     */
    @Log(title = "出库确认记录详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsDeliverySureDetail wmsDeliverySureDetail) {
        return toAjax(wmsDeliverySureDetailService.updateWmsDeliverySureDetail(wmsDeliverySureDetail));
    }

    /**
     * 删除出库确认记录详情
     */
    @Log(title = "出库确认记录详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsDeliverySureDetailService.deleteWmsDeliverySureDetailByIds(ids));
    }
}
