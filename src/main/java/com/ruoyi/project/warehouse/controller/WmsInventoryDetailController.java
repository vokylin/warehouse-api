package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsInventoryDetail;
import com.ruoyi.project.warehouse.service.IWmsInventoryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 盘点详情Controller
 *
 * @author Rem
 * @date 2023-05-21
 */
@RestController
@RequestMapping("/warehouse/inventoryDetail")
public class WmsInventoryDetailController extends BaseController {
    @Autowired
    private IWmsInventoryDetailService wmsInventoryDetailService;

    /**
     * 查询盘点详情列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmsInventoryDetail wmsInventoryDetail) {
        startPage();
        List<WmsInventoryDetail> list = wmsInventoryDetailService.selectWmsInventoryDetailList(wmsInventoryDetail);
        return getDataTable(list);
    }

    /**
     * 导出盘点详情列表
     */
    @Log(title = "盘点详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsInventoryDetail wmsInventoryDetail) {
        List<WmsInventoryDetail> list = wmsInventoryDetailService.selectWmsInventoryDetailList(wmsInventoryDetail);
        ExcelUtil<WmsInventoryDetail> util = new ExcelUtil<WmsInventoryDetail>(WmsInventoryDetail.class);
        util.exportExcel(response, list, "盘点详情数据");
    }

    /**
     * 获取盘点详情详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsInventoryDetailService.selectWmsInventoryDetailById(id));
    }

    /**
     * 新增盘点详情
     */
    @Log(title = "盘点详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsInventoryDetail wmsInventoryDetail) {
        wmsInventoryDetail.setIsAdd(Constants.YES);
        wmsInventoryDetail.setIsSave(Constants.YES);
        return wmsInventoryDetailService.insertWmsInventoryDetail(wmsInventoryDetail);
    }

    /**
     * 修改盘点详情
     */
    @Log(title = "盘点详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsInventoryDetail wmsInventoryDetail) {
        wmsInventoryDetail.setIsSave(Constants.YES);
        return toAjax(wmsInventoryDetailService.updateWmsInventoryDetail(wmsInventoryDetail));
    }

    /**
     * 批量修改盘点详情
     */
    @Log(title = "盘点详情", businessType = BusinessType.UPDATE)
    @PutMapping("/batchEdit")
    public AjaxResult batchEdit(@RequestBody List<WmsInventoryDetail> wmsInventoryDetailList) {
        return wmsInventoryDetailService.batchUpdateWmsInventoryDetail(wmsInventoryDetailList);
    }

    /**
     * 删除盘点详情
     */
    @Log(title = "盘点详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsInventoryDetailService.deleteWmsInventoryDetailByIds(ids));
    }
}
