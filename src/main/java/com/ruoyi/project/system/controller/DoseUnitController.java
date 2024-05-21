package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.DoseUnit;
import com.ruoyi.project.system.service.IDoseUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 剂量单位Controller
 *
 * @author Rem
 * @date 2023-04-27
 */
@RestController
@RequestMapping("/system/doseUnit")
public class DoseUnitController extends BaseController {
    @Autowired
    private IDoseUnitService tbDoseUnitService;

    /**
     * 查询剂量单位列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:itemStorage:unitConversion')")
    @GetMapping("/list")
    public TableDataInfo list(DoseUnit doseUnit) {
        startPage();
        List<DoseUnit> list = tbDoseUnitService.selectTbDoseUnitList(doseUnit);
        return getDataTable(list);
    }

    /**
     * 导出剂量单位列表
     */
    @PreAuthorize("@ss.hasPermi('system:doseUnit:export')")
    @Log(title = "剂量单位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DoseUnit doseUnit) {
        List<DoseUnit> list = tbDoseUnitService.selectTbDoseUnitList(doseUnit);
        ExcelUtil<DoseUnit> util = new ExcelUtil<DoseUnit>(DoseUnit.class);
        util.exportExcel(response, list, "剂量单位数据");
    }

    /**
     * 获取剂量单位详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:itemStorage:unitConversion')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(tbDoseUnitService.selectTbDoseUnitById(id));
    }
}
