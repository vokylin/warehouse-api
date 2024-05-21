package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsBreakageDocDetail;
import com.ruoyi.project.warehouse.domain.WmsItemStorageDetail;
import com.ruoyi.project.warehouse.service.IWmsBreakageDocDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报损物料明细Controller
 *
 * @author Rem
 * @date 2023-05-20
 */
@RestController
@RequestMapping("/warehouse/breakageDocDetail")
public class WmsBreakageDocDetailController extends BaseController {
    @Autowired
    private IWmsBreakageDocDetailService wmsBreakageDocDetailService;

    /**
     * 查询报损物料明细列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmsBreakageDocDetail wmsBreakageDocDetail) {
        startPage();
        List<WmsBreakageDocDetail> list = wmsBreakageDocDetailService.selectWmsBreakageDocDetailList(wmsBreakageDocDetail);
        return getDataTable(list);
    }

    /**
     * 导出报损物料明细列表
     */
    @Log(title = "报损物料明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsBreakageDocDetail wmsBreakageDocDetail) {
        List<WmsBreakageDocDetail> list = wmsBreakageDocDetailService.selectWmsBreakageDocDetailList(wmsBreakageDocDetail);
        ExcelUtil<WmsBreakageDocDetail> util = new ExcelUtil<WmsBreakageDocDetail>(WmsBreakageDocDetail.class);
        util.exportExcel(response, list, "报损物料明细数据");
    }

    /**
     * 获取报损物料明细详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsBreakageDocDetailService.selectWmsBreakageDocDetailById(id));
    }

    /**
     * 新增报损物料明细
     */
    @Log(title = "报损物料明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsBreakageDocDetail wmsBreakageDocDetail) {
        return toAjax(wmsBreakageDocDetailService.insertWmsBreakageDocDetail(wmsBreakageDocDetail));
    }

    /**
     * 修改报损物料明细
     */
    @Log(title = "报损物料明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsBreakageDocDetail wmsBreakageDocDetail) {
        return toAjax(wmsBreakageDocDetailService.updateWmsBreakageDocDetail(wmsBreakageDocDetail));
    }

    /**
     * 删除报损物料明细
     */
    @Log(title = "报损物料明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsBreakageDocDetailService.deleteWmsBreakageDocDetailByIds(ids));
    }

    /**
     * 批量分配
     */
    @Log(title = "库存详情", businessType = BusinessType.UPDATE)
    @PutMapping("/allotBatch")
    public AjaxResult allotBatch(@RequestBody List<WmsItemStorageDetail> wmsItemStorageDetails) throws Exception {
        if (null == wmsItemStorageDetails || wmsItemStorageDetails.isEmpty()) {
            return AjaxResult.error("分配失败，分配数据不能为空");
        }
        for (WmsItemStorageDetail wmsItemStorageDetail : wmsItemStorageDetails) {
            wmsBreakageDocDetailService.allotItemStorage(wmsItemStorageDetail);
        }
        return AjaxResult.success("分配成功");
    }

    /**
     * 批量取消分配
     */
    @Log(title = "库存详情", businessType = BusinessType.UPDATE)
    @PutMapping("/cancelBatch")
    public AjaxResult cancelBatch(@RequestBody List<WmsBreakageDocDetail> wmsBreakageDocDetails) throws Exception {
        if (null == wmsBreakageDocDetails || wmsBreakageDocDetails.isEmpty()) {
            return AjaxResult.error("取消分配失败，取消分配数据不能为空");
        }
        for (WmsBreakageDocDetail wmsBreakageDocDetail : wmsBreakageDocDetails) {
            wmsBreakageDocDetailService.cancelAllotItemStorageAndDelete(wmsBreakageDocDetail);
        }
        return AjaxResult.success("取消分配成功");
    }

}
