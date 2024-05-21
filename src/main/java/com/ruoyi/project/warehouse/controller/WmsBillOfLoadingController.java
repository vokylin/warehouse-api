package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsBillOfLoading;
import com.ruoyi.project.warehouse.service.IWmsBillOfLoadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 发运单Controller
 *
 * @author ruoyi
 * @date 2023-04-16
 */
@RestController
@RequestMapping("/warehouse/billOfLoading")
public class WmsBillOfLoadingController extends BaseController {
    @Autowired
    private IWmsBillOfLoadingService wmsBillOfLoadingService;

    /**
     * 查询发运单列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:billOfLoading:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsBillOfLoading wmsBillOfLoading) {
        wmsBillOfLoading.setCompanyId(getCompanyId());
        startPage();
        List<WmsBillOfLoading> list = wmsBillOfLoadingService.selectWmsBillOfLoadingList(wmsBillOfLoading);
        return getDataTable(list);
    }

    /**
     * 导出发运单列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:billOfLoading:export')")
    @Log(title = "发运单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsBillOfLoading wmsBillOfLoading) {
        List<WmsBillOfLoading> list = wmsBillOfLoadingService.selectWmsBillOfLoadingList(wmsBillOfLoading);
        ExcelUtil<WmsBillOfLoading> util = new ExcelUtil<WmsBillOfLoading>(WmsBillOfLoading.class);
        util.exportExcel(response, list, "发运单数据");
    }


    /**
     * 获取发运单详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsBillOfLoadingService.selectWmsBillOfLoadingById(id));
    }

    /**
     * 新增发运单
     */
    @PreAuthorize("@ss.hasPermi('warehouse:billOfLoading:add')")
    @Log(title = "发运单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsBillOfLoading wmsBillOfLoading) {
        return toAjax(wmsBillOfLoadingService.insertWmsBillOfLoading(wmsBillOfLoading));
    }

    /**
     * 修改发运单
     */
    @Log(title = "发运单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsBillOfLoading wmsBillOfLoading) {
        return toAjax(wmsBillOfLoadingService.updateWmsBillOfLoading(wmsBillOfLoading));
    }

    /**
     * 删除发运单
     */
    @Log(title = "发运单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsBillOfLoadingService.deleteWmsBillOfLoadingByIds(ids));
    }

    /**
     * 发运完成
     */
    @PreAuthorize("@ss.hasPermi('warehouse:billOfLoading:finish')")
    @Log(title = "发运单", businessType = BusinessType.UPDATE)
    @PutMapping("/finish")
    public AjaxResult finish(@RequestBody WmsBillOfLoading wmsBillOfLoading) throws Exception {
        return toAjax(wmsBillOfLoadingService.finishWmsBillOfLoading(wmsBillOfLoading));
    }
}
