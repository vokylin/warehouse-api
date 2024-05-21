package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsExamineWork;
import com.ruoyi.project.warehouse.service.IWmsExamineWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 质检作业Controller
 *
 * @author Rem
 * @date 2023-04-07
 */
@RestController
@RequestMapping("/warehouse/examineWork")
public class WmsExamineWorkController extends BaseController {
    @Autowired
    private IWmsExamineWorkService wmsExamineWorkService;

    /**
     * 查询质检作业列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:examineWork:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsExamineWork wmsExamineWork) {
        wmsExamineWork.setCompanyId(getCompanyId());
        startPage();
        List<WmsExamineWork> list = wmsExamineWorkService.selectWmsExamineWorkList(wmsExamineWork);
        return getDataTable(list);
    }

    /**
     * 导出质检作业列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:examineWork:export')")
    @Log(title = "质检作业", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsExamineWork wmsExamineWork) {
        List<WmsExamineWork> list = wmsExamineWorkService.selectWmsExamineWorkList(wmsExamineWork);
        ExcelUtil<WmsExamineWork> util = new ExcelUtil<WmsExamineWork>(WmsExamineWork.class);
        util.exportExcel(response, list, "质检作业数据");
    }

    /**
     * 获取质检作业详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:examineWork:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsExamineWorkService.selectWmsExamineWorkById(id));
    }

    /**
     * 新增质检作业
     */
    @PreAuthorize("@ss.hasPermi('warehouse:examineWork:add')")
    @Log(title = "质检作业", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsExamineWork wmsExamineWork) {
        return toAjax(wmsExamineWorkService.insertWmsExamineWork(wmsExamineWork));
    }

    /**
     * 修改质检作业
     */
    @PreAuthorize("@ss.hasPermi('warehouse:examineWork:edit')")
    @Log(title = "质检作业", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsExamineWork wmsExamineWork) {
        return toAjax(wmsExamineWorkService.updateWmsExamineWork(wmsExamineWork));
    }

    /**
     * 删除质检作业
     */
    @PreAuthorize("@ss.hasPermi('warehouse:examineWork:remove')")
    @Log(title = "质检作业", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsExamineWorkService.deleteWmsExamineWorkByIds(ids));
    }


    /**
     * 通知质检
     */
    @PreAuthorize("@ss.hasPermi('warehouse:examineWork:notice')")
    @Log(title = "质检作业", businessType = BusinessType.UPDATE)
    @PutMapping("/notice")
    public AjaxResult notice(@RequestBody WmsExamineWork wmsExamineWork) throws Exception {
        return wmsExamineWorkService.noticeExamine(wmsExamineWork);
    }

    /**
     * 上架作业
     */
    @PreAuthorize("@ss.hasPermi('warehouse:examineWork:shelves')")
    @Log(title = "质检作业", businessType = BusinessType.UPDATE)
    @PutMapping("/shelves")
    public AjaxResult shelves(@RequestBody WmsExamineWork wmsExamineWork) {
        return wmsExamineWorkService.shelvesWork(wmsExamineWork);
    }


    /**
     * 退货作业
     */
    @PreAuthorize("@ss.hasPermi('warehouse:examineWork:refund')")
    @Log(title = "质检作业", businessType = BusinessType.UPDATE)
    @PutMapping("/refund")
    public AjaxResult refund(@RequestBody WmsExamineWork wmsExamineWork) {
        return wmsExamineWorkService.refundWork(wmsExamineWork);
    }

}
