package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.WmsItemAttribute;
import com.ruoyi.project.system.service.IWmsItemAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 物料仓储属性Controller
 *
 * @author ruoyi
 * @date 2023-04-11
 */
@RestController
@RequestMapping("/system/attribute")
public class WmsItemAttributeController extends BaseController {
    @Autowired
    private IWmsItemAttributeService wmsItemAttributeService;

    /**
     * 查询物料仓储属性列表
     */
    @PreAuthorize("@ss.hasPermi('system:attribute:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsItemAttribute wmsItemAttribute) {
        startPage();
        List<WmsItemAttribute> list = wmsItemAttributeService.selectWmsItemAttributeList(wmsItemAttribute);
        return getDataTable(list);
    }

    /**
     * 导出物料仓储属性列表
     */
    @PreAuthorize("@ss.hasPermi('system:attribute:export')")
    @Log(title = "物料仓储属性", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsItemAttribute wmsItemAttribute) {
        List<WmsItemAttribute> list = wmsItemAttributeService.selectWmsItemAttributeList(wmsItemAttribute);
        ExcelUtil<WmsItemAttribute> util = new ExcelUtil<WmsItemAttribute>(WmsItemAttribute.class);
        util.exportExcel(response, list, "物料仓储属性数据");
    }

    /**
     * 获取物料仓储属性详细信息
     */
    @GetMapping(value = "/{itemCode}")
    public AjaxResult getInfo(@PathVariable("itemCode") String itemCode) {
        return success(wmsItemAttributeService.selectByItemCode(itemCode));
    }

    /**
     * 新增物料仓储属性
     */
    @Log(title = "物料仓储属性", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsItemAttribute wmsItemAttribute) {
        return toAjax(wmsItemAttributeService.insertWmsItemAttribute(wmsItemAttribute));
    }

    /**
     * 修改物料仓储属性
     */
    @Log(title = "物料仓储属性", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsItemAttribute wmsItemAttribute) {
        return wmsItemAttributeService.updateWmsItemAttribute(wmsItemAttribute);
    }

    /**
     * 删除物料仓储属性
     */
    @Log(title = "物料仓储属性", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsItemAttributeService.deleteWmsItemAttributeByIds(ids));
    }
}
