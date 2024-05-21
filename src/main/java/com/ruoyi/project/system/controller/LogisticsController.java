package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.Logistics;
import com.ruoyi.project.system.service.ILogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 承运商Controller
 *
 * @author Rem
 * @date 2023-04-11
 */
@RestController
@RequestMapping("/system/logistics")
public class LogisticsController extends BaseController {
    @Autowired
    private ILogisticsService logisticsService;

    /**
     * 查询承运商列表
     */
    @PreAuthorize("@ss.hasPermi('system:logistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(Logistics logistics) {
        startPage();
        List<Logistics> list = logisticsService.selectLogisticsList(logistics);
        return getDataTable(list);
    }

    /**
     * 导出承运商列表
     */
    @PreAuthorize("@ss.hasPermi('system:logistics:export')")
    @Log(title = "承运商", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Logistics logistics) {
        List<Logistics> list = logisticsService.selectLogisticsList(logistics);
        ExcelUtil<Logistics> util = new ExcelUtil<Logistics>(Logistics.class);
        util.exportExcel(response, list, "承运商数据");
    }

    /**
     * 获取承运商详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:logistics:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(logisticsService.selectLogisticsById(id));
    }

    /**
     * 新增承运商
     */
    @PreAuthorize("@ss.hasPermi('system:logistics:add')")
    @Log(title = "承运商", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Logistics logistics) {
        return toAjax(logisticsService.insertLogistics(logistics));
    }

    /**
     * 修改承运商
     */
    @PreAuthorize("@ss.hasPermi('system:logistics:edit')")
    @Log(title = "承运商", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Logistics logistics) {
        return toAjax(logisticsService.updateLogistics(logistics));
    }

    /**
     * 删除承运商
     */
    @PreAuthorize("@ss.hasPermi('system:logistics:remove')")
    @Log(title = "承运商", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(logisticsService.deleteLogisticsByIds(ids));
    }
}
