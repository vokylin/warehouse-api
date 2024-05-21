package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.WmsDeliverySureDict;
import com.ruoyi.project.system.service.IWmsDeliverySureDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出库确认项Controller
 *
 * @author Rem
 * @date 2023-04-12
 */
@RestController
@RequestMapping("/system/deliverySureDict")
public class WmsDeliverySureDictController extends BaseController {
    @Autowired
    private IWmsDeliverySureDictService wmsDeliverySureDictService;

    /**
     * 查询出库确认项列表
     */
    @PreAuthorize("@ss.hasPermi('system:deliverySureDict:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsDeliverySureDict wmsDeliverySureDict) {
        startPage();
        List<WmsDeliverySureDict> list = wmsDeliverySureDictService.selectWmsDeliverySureDictList(wmsDeliverySureDict);
        return getDataTable(list);
    }


    @GetMapping("/getListByBusinessType")
    public TableDataInfo getListByBusinessType(WmsDeliverySureDict wmsDeliverySureDict) {
        startPage();
        List<WmsDeliverySureDict> list = wmsDeliverySureDictService.getListByBusinessType(wmsDeliverySureDict);
        return getDataTable(list);
    }


    /**
     * 导出出库确认项列表
     */
    @Log(title = "出库确认项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsDeliverySureDict wmsDeliverySureDict) {
        List<WmsDeliverySureDict> list = wmsDeliverySureDictService.selectWmsDeliverySureDictList(wmsDeliverySureDict);
        ExcelUtil<WmsDeliverySureDict> util = new ExcelUtil<WmsDeliverySureDict>(WmsDeliverySureDict.class);
        util.exportExcel(response, list, "出库确认项数据");
    }

    /**
     * 获取出库确认项详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsDeliverySureDictService.selectWmsDeliverySureDictById(id));
    }

    /**
     * 新增出库确认项
     */
    @PreAuthorize("@ss.hasPermi('system:deliverySureDict:add')")
    @Log(title = "出库确认项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsDeliverySureDict wmsDeliverySureDict) {
        return toAjax(wmsDeliverySureDictService.insertWmsDeliverySureDict(wmsDeliverySureDict));
    }

    /**
     * 修改出库确认项
     */
    @PreAuthorize("@ss.hasPermi('system:deliverySureDict:edit')")
    @Log(title = "出库确认项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsDeliverySureDict wmsDeliverySureDict) {
        return toAjax(wmsDeliverySureDictService.updateWmsDeliverySureDict(wmsDeliverySureDict));
    }

    /**
     * 删除出库确认项
     */
    @PreAuthorize("@ss.hasPermi('system:deliverySureDict:remove')")
    @Log(title = "出库确认项", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsDeliverySureDictService.deleteWmsDeliverySureDictByIds(ids));
    }
}
