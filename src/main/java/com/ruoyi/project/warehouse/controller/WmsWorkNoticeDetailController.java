package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsWorkNoticeDetail;
import com.ruoyi.project.warehouse.service.IWmsWorkNoticeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作业通知明细Controller
 *
 * @author Rem
 * @date 2023-04-07
 */
@RestController
@RequestMapping("/warehouse/workNoticeDetail")
public class WmsWorkNoticeDetailController extends BaseController {
    @Autowired
    private IWmsWorkNoticeDetailService wmsWorkNoticeDetailService;

    /**
     * 查询作业通知明细列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmsWorkNoticeDetail wmsWorkNoticeDetail) {
        startPage();
        List<WmsWorkNoticeDetail> list = wmsWorkNoticeDetailService.selectWmsWorkNoticeDetailList(wmsWorkNoticeDetail);
        return getDataTable(list);
    }

    /**
     * 导出作业通知明细列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:workNoticeDetail:export')")
    @Log(title = "作业通知明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsWorkNoticeDetail wmsWorkNoticeDetail) {
        List<WmsWorkNoticeDetail> list = wmsWorkNoticeDetailService.selectWmsWorkNoticeDetailList(wmsWorkNoticeDetail);
        ExcelUtil<WmsWorkNoticeDetail> util = new ExcelUtil<WmsWorkNoticeDetail>(WmsWorkNoticeDetail.class);
        util.exportExcel(response, list, "作业通知明细数据");
    }

    /**
     * 获取作业通知明细详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsWorkNoticeDetailService.selectWmsWorkNoticeDetailById(id));
    }

    /**
     * 新增作业通知明细
     */
    @PreAuthorize("@ss.hasPermi('warehouse:workNoticeDetail:add')")
    @Log(title = "作业通知明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsWorkNoticeDetail wmsWorkNoticeDetail) {
        return toAjax(wmsWorkNoticeDetailService.insertWmsWorkNoticeDetail(wmsWorkNoticeDetail));
    }

    /**
     * 修改作业通知明细
     */
    @PreAuthorize("@ss.hasPermi('warehouse:workNoticeDetail:edit')")
    @Log(title = "作业通知明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsWorkNoticeDetail wmsWorkNoticeDetail) {
        return toAjax(wmsWorkNoticeDetailService.updateWmsWorkNoticeDetail(wmsWorkNoticeDetail));
    }

    /**
     * 删除作业通知明细
     */
    @PreAuthorize("@ss.hasPermi('warehouse:workNoticeDetail:remove')")
    @Log(title = "作业通知明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsWorkNoticeDetailService.deleteWmsWorkNoticeDetailByIds(ids));
    }

    /**
     * 分配
     */
    @Log(title = "作业通知明细", businessType = BusinessType.UPDATE)
    @PutMapping("/allot")
    public AjaxResult allot(@RequestBody WmsWorkNoticeDetail wmsWorkNoticeDetail) {
        return wmsWorkNoticeDetailService.allot(wmsWorkNoticeDetail);
    }

    /**
     * 分配
     */
    @Log(title = "作业通知明细", businessType = BusinessType.UPDATE)
    @PutMapping("/batchAllot")
    public AjaxResult batchAllot(@RequestBody List<WmsWorkNoticeDetail> wmsWorkNoticeDetails) {
        if (wmsWorkNoticeDetails.isEmpty()) {
            return AjaxResult.error("分配失败，分配数据为空");
        }
        wmsWorkNoticeDetails.forEach(wmsWorkNoticeDetailService::allot);
        return success();
    }


    /**
     * 取消分配
     */
    @Log(title = "作业通知明细", businessType = BusinessType.UPDATE)
    @PutMapping("/cancelAllot/{ids}")
    public AjaxResult cancelAllot(@PathVariable String[] ids) throws Exception {
        return wmsWorkNoticeDetailService.cancelAllot(ids);
    }

    /**
     * 提交分配
     */
    @Log(title = "作业通知明细", businessType = BusinessType.UPDATE)
    @PutMapping("/submitAllot/{ids}")
    public AjaxResult submitAllot(@PathVariable String[] ids) {
        return wmsWorkNoticeDetailService.submitAllot(ids);
    }

}
