package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsPickingWorkNoticeDetail;
import com.ruoyi.project.warehouse.service.IWmsPickingWorkNoticeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 拣货作业通知明细Controller
 *
 * @author Rem
 * @date 2023-04-15
 */
@RestController
@RequestMapping("/warehouse/pickingWorkNoticeDetail")
public class WmsPickingWorkNoticeDetailController extends BaseController {
    @Autowired
    private IWmsPickingWorkNoticeDetailService wmsPickingWorkNoticeDetailService;

    /**
     * 查询拣货作业通知明细列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail) {
        startPage();
        List<WmsPickingWorkNoticeDetail> list = wmsPickingWorkNoticeDetailService.selectWmsPickingWorkNoticeDetailList(wmsPickingWorkNoticeDetail);
        return getDataTable(list);
    }

    /**
     * 导出拣货作业通知明细列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:pickingWorkNoticeDetail:export')")
    @Log(title = "拣货作业通知明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail) {
        List<WmsPickingWorkNoticeDetail> list = wmsPickingWorkNoticeDetailService.selectWmsPickingWorkNoticeDetailList(wmsPickingWorkNoticeDetail);
        ExcelUtil<WmsPickingWorkNoticeDetail> util = new ExcelUtil<WmsPickingWorkNoticeDetail>(WmsPickingWorkNoticeDetail.class);
        util.exportExcel(response, list, "拣货作业通知明细数据");
    }

    /**
     * 获取拣货作业通知明细详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsPickingWorkNoticeDetailService.selectWmsPickingWorkNoticeDetailById(id));
    }

    /**
     * 新增拣货作业通知明细
     */
    @PreAuthorize("@ss.hasPermi('warehouse:pickingWorkNoticeDetail:add')")
    @Log(title = "拣货作业通知明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail) {
        return toAjax(wmsPickingWorkNoticeDetailService.insertWmsPickingWorkNoticeDetail(wmsPickingWorkNoticeDetail));
    }

    /**
     * 修改拣货作业通知明细
     */
    @PreAuthorize("@ss.hasPermi('warehouse:pickingWorkNoticeDetail:edit')")
    @Log(title = "拣货作业通知明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail) {
        return toAjax(wmsPickingWorkNoticeDetailService.updateWmsPickingWorkNoticeDetail(wmsPickingWorkNoticeDetail));
    }

    /**
     * 删除拣货作业通知明细
     */
    @PreAuthorize("@ss.hasPermi('warehouse:pickingWorkNoticeDetail:remove')")
    @Log(title = "拣货作业通知明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsPickingWorkNoticeDetailService.deleteWmsPickingWorkNoticeDetailByIds(ids));
    }


    /**
     * 缺货登记
     */
    @Log(title = "库存详情", businessType = BusinessType.UPDATE)
    @PutMapping("/shortageCheckIn")
    public AjaxResult shortageCheckIn(@RequestBody WmsPickingWorkNoticeDetail wmsPickingWorkNoticeDetail) {
        return wmsPickingWorkNoticeDetailService.shortageCheckIn(wmsPickingWorkNoticeDetail);
    }
}
