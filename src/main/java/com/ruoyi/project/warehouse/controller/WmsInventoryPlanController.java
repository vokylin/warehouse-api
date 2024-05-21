package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.common.component.CodeService;
import com.ruoyi.project.warehouse.domain.WmsInventoryPlan;
import com.ruoyi.project.warehouse.service.IWmsInventoryPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 盘点计划Controller
 *
 * @author Rem
 * @date 2023-05-21
 */
@RestController
@RequestMapping("/warehouse/inventoryPlan")
public class WmsInventoryPlanController extends BaseController {
    @Autowired
    private IWmsInventoryPlanService wmsInventoryPlanService;

    @Autowired
    private CodeService codeService;

    /**
     * 查询盘点计划列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inventoryPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsInventoryPlan wmsInventoryPlan) {
        wmsInventoryPlan.setCompanyId(getCompanyId());
        startPage();
        List<WmsInventoryPlan> list = wmsInventoryPlanService.selectWmsInventoryPlanList(wmsInventoryPlan);
        return getDataTable(list);
    }

    /**
     * 导出盘点计划列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inventoryPlan:export')")
    @Log(title = "盘点计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsInventoryPlan wmsInventoryPlan) {
        List<WmsInventoryPlan> list = wmsInventoryPlanService.selectWmsInventoryPlanList(wmsInventoryPlan);
        ExcelUtil<WmsInventoryPlan> util = new ExcelUtil<WmsInventoryPlan>(WmsInventoryPlan.class);
        util.exportExcel(response, list, "盘点计划数据");
    }

    /**
     * 获取盘点计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inventoryPlan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsInventoryPlanService.selectWmsInventoryPlanById(id));
    }

    /**
     * 新增盘点计划
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inventoryPlan:add')")
    @Log(title = "盘点计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsInventoryPlan wmsInventoryPlan) {
        wmsInventoryPlan.setId(codeService.generateInventoryPlanCode());
        wmsInventoryPlan.setCreateBy(SecurityUtils.getUserId());
        wmsInventoryPlan.setCompanyId(ServletUtils.getCompanyId());
        return wmsInventoryPlanService.insertWmsInventoryPlan(wmsInventoryPlan);
    }

    /**
     * 修改盘点计划
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inventoryPlan:edit')")
    @Log(title = "盘点计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsInventoryPlan wmsInventoryPlan) {
        wmsInventoryPlan.setUpdateBy(SecurityUtils.getUserId());
        return toAjax(wmsInventoryPlanService.updateWmsInventoryPlan(wmsInventoryPlan));
    }

    /**
     * 删除盘点计划
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inventoryPlan:remove')")
    @Log(title = "盘点计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsInventoryPlanService.deleteWmsInventoryPlanByIds(ids));
    }


    /**
     * 作业
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inventoryPlan:work')")
    @Log(title = "盘点计划", businessType = BusinessType.UPDATE)
    @PutMapping("/work/{id}")
    public AjaxResult work(@PathVariable("id") String id) {
        return wmsInventoryPlanService.doWork(id);
    }

    /**
     * 盘点完成
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inventoryPlan:finish')")
    @Log(title = "盘点计划", businessType = BusinessType.UPDATE)
    @PutMapping("/finish/{id}")
    public AjaxResult finish(@PathVariable("id") String id) {
        return wmsInventoryPlanService.workFinish(id);
    }

}
