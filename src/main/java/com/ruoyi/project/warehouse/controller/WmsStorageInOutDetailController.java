package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsStorageInOutDetail;
import com.ruoyi.project.warehouse.service.IWmsStorageInOutDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出入库记录详情Controller
 *
 * @author Rem
 * @date 2023-04-08
 */
@RestController
@RequestMapping("/warehouse/storageInOutDetail")
public class WmsStorageInOutDetailController extends BaseController {
    @Autowired
    private IWmsStorageInOutDetailService wmsStorageInOutDetailService;

    /**
     * 查询出入库记录详情列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmsStorageInOutDetail wmsStorageInOutDetail) {
        startPage();
        List<WmsStorageInOutDetail> list = wmsStorageInOutDetailService.selectWmsStorageInOutDetailList(wmsStorageInOutDetail);
        return getDataTable(list);
    }

    /**
     * 导出出入库记录详情列表
     */
    @Log(title = "出入库记录详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsStorageInOutDetail wmsStorageInOutDetail) {
        List<WmsStorageInOutDetail> list = wmsStorageInOutDetailService.selectWmsStorageInOutDetailList(wmsStorageInOutDetail);
        ExcelUtil<WmsStorageInOutDetail> util = new ExcelUtil<WmsStorageInOutDetail>(WmsStorageInOutDetail.class);
        util.exportExcel(response, list, "出入库记录详情数据");
    }

    /**
     * 获取出入库记录详情详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsStorageInOutDetailService.selectWmsStorageInOutDetailById(id));
    }

    /**
     * 新增出入库记录详情
     */
    @Log(title = "出入库记录详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsStorageInOutDetail wmsStorageInOutDetail) {
        return toAjax(wmsStorageInOutDetailService.insertWmsStorageInOutDetail(wmsStorageInOutDetail));
    }

    /**
     * 修改出入库记录详情
     */
    @Log(title = "出入库记录详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsStorageInOutDetail wmsStorageInOutDetail) {
        return toAjax(wmsStorageInOutDetailService.updateWmsStorageInOutDetail(wmsStorageInOutDetail));
    }

    /**
     * 删除出入库记录详情
     */
    @Log(title = "出入库记录详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsStorageInOutDetailService.deleteWmsStorageInOutDetailByIds(ids));
    }
}
