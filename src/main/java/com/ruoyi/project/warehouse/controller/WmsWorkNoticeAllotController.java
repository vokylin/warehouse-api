package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsWorkNoticeAllot;
import com.ruoyi.project.warehouse.domain.WorkNoticeBatchSubmitAllotDto;
import com.ruoyi.project.warehouse.service.IWmsWorkNoticeAllotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作业通知明细分配Controller
 *
 * @author Rem
 * @date 2023-04-07
 */
@RestController
@RequestMapping("/warehouse/workNoticeAllot")
public class WmsWorkNoticeAllotController extends BaseController {
    @Autowired
    private IWmsWorkNoticeAllotService wmsWorkNoticeAllotService;

    /**
     * 查询作业通知明细分配列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmsWorkNoticeAllot wmsWorkNoticeAllot) {
        startPage();
        List<WmsWorkNoticeAllot> list = wmsWorkNoticeAllotService.selectWmsWorkNoticeAllotList(wmsWorkNoticeAllot);
        return getDataTable(list);
    }

    /**
     * 导出作业通知明细分配列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:workNoticeAllot:export')")
    @Log(title = "作业通知明细分配", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsWorkNoticeAllot wmsWorkNoticeAllot) {
        List<WmsWorkNoticeAllot> list = wmsWorkNoticeAllotService.selectWmsWorkNoticeAllotList(wmsWorkNoticeAllot);
        ExcelUtil<WmsWorkNoticeAllot> util = new ExcelUtil<WmsWorkNoticeAllot>(WmsWorkNoticeAllot.class);
        util.exportExcel(response, list, "作业通知明细分配数据");
    }

    /**
     * 获取作业通知明细分配详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsWorkNoticeAllotService.selectWmsWorkNoticeAllotById(id));
    }

    /**
     * 新增作业通知明细分配
     */
    @PreAuthorize("@ss.hasPermi('warehouse:workNoticeAllot:add')")
    @Log(title = "作业通知明细分配", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsWorkNoticeAllot wmsWorkNoticeAllot) {
        return toAjax(wmsWorkNoticeAllotService.insertWmsWorkNoticeAllot(wmsWorkNoticeAllot));
    }

    /**
     * 修改作业通知明细分配
     */
    @PreAuthorize("@ss.hasPermi('warehouse:workNoticeAllot:edit')")
    @Log(title = "作业通知明细分配", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsWorkNoticeAllot wmsWorkNoticeAllot) {
        return toAjax(wmsWorkNoticeAllotService.updateWmsWorkNoticeAllot(wmsWorkNoticeAllot));
    }

    /**
     * 删除作业通知明细分配
     */
    @PreAuthorize("@ss.hasPermi('warehouse:workNoticeAllot:remove')")
    @Log(title = "作业通知明细分配", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsWorkNoticeAllotService.deleteWmsWorkNoticeAllotByIds(ids));
    }

    /**
     * 取消分配
     */
    @Log(title = "作业通知明细分配", businessType = BusinessType.UPDATE)
    @PutMapping("/cancel/{ids}")
    public AjaxResult cancel(@PathVariable String[] ids) {
        return wmsWorkNoticeAllotService.cancelWmsWorkNoticeAllotByIds(ids);
    }

    /**
     * 作业通知明细 批量入库
     */
    @Log(title = "作业通知明细分配", businessType = BusinessType.UPDATE)
    @PutMapping("/submitAllot")
    public AjaxResult submit(@RequestBody WorkNoticeBatchSubmitAllotDto workNoticeBatchSubmitAllotDto) {
        return wmsWorkNoticeAllotService.submitWmsWorkNoticeAllotByIds(workNoticeBatchSubmitAllotDto);
    }

    /**
     * 退货确认
     */
    @Log(title = "作业通知明细分配", businessType = BusinessType.UPDATE)
    @PutMapping("/refundConfirm/{id}")
    public AjaxResult refundConfirm(@PathVariable String id) {
        return wmsWorkNoticeAllotService.refundConfirm(id);
    }
}
