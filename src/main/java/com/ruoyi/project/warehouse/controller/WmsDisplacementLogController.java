package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.service.IRoleWhService;
import com.ruoyi.project.warehouse.domain.WmsDisplacementLog;
import com.ruoyi.project.warehouse.service.IWmsDisplacementLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 移位日志Controller
 *
 * @author Rem
 * @date 2023-04-25
 */
@RestController
@RequestMapping("/warehouse/displacementLog")
public class WmsDisplacementLogController extends BaseController {
    @Autowired
    private IWmsDisplacementLogService wmsDisplacementLogService;
    @Autowired
    private IRoleWhService roleWhService;

    /**
     * 查询移位日志列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:displacementLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsDisplacementLog wmsDisplacementLog) {
        wmsDisplacementLog.setCompanyId(getCompanyId());
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            List<String> warehouseIdList = roleWhService.selectLitByUser();
            if (warehouseIdList != null && warehouseIdList.size() > 0) {
                wmsDisplacementLog.setWarehouseIdList(warehouseIdList);
            } else {
                return getDataTable(new ArrayList<>());
            }
        }
        startPage();
        List<WmsDisplacementLog> list = wmsDisplacementLogService.selectWmsDisplacementLogList(wmsDisplacementLog);
        return getDataTable(list);
    }

    /**
     * 导出移位日志列表
     */
    @Log(title = "移位日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsDisplacementLog wmsDisplacementLog) {
        List<WmsDisplacementLog> list = wmsDisplacementLogService.selectWmsDisplacementLogList(wmsDisplacementLog);
        ExcelUtil<WmsDisplacementLog> util = new ExcelUtil<WmsDisplacementLog>(WmsDisplacementLog.class);
        util.exportExcel(response, list, "移位日志数据");
    }

    /**
     * 获取移位日志详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsDisplacementLogService.selectWmsDisplacementLogById(id));
    }

    /**
     * 新增移位日志
     */
    @Log(title = "移位日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsDisplacementLog wmsDisplacementLog) {
        return toAjax(wmsDisplacementLogService.insertWmsDisplacementLog(wmsDisplacementLog));
    }

    /**
     * 修改移位日志
     */
    @Log(title = "移位日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsDisplacementLog wmsDisplacementLog) {
        return toAjax(wmsDisplacementLogService.updateWmsDisplacementLog(wmsDisplacementLog));
    }

    /**
     * 删除移位日志
     */
    @Log(title = "移位日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsDisplacementLogService.deleteWmsDisplacementLogByIds(ids));
    }
}
