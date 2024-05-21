package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsReceiveNoticeDetail;
import com.ruoyi.project.warehouse.service.IWmsReceiveNoticeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 入库通知明细Controller
 *
 * @author Rem
 * @date 2023-03-31
 */
@RestController
@RequestMapping("/warehouse/wmsReceiveNoticeDetail")
public class WmsReceiveNoticeDetailController extends BaseController {
    @Autowired
    private IWmsReceiveNoticeDetailService wmsReceiveNoticeDetailService;

    /**
     * 查询入库通知明细列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:detail')")
    @GetMapping("/list")
    public TableDataInfo list(WmsReceiveNoticeDetail wmsReceiveNoticeDetail) {
        startPage();
        List<WmsReceiveNoticeDetail> list = wmsReceiveNoticeDetailService.selectWmsReceiveNoticeDetailList(wmsReceiveNoticeDetail);
        return getDataTable(list);
    }

    /**
     * 导出入库通知明细列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNoticeDetail:export')")
    @Log(title = "入库通知明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsReceiveNoticeDetail wmsReceiveNoticeDetail) {
        List<WmsReceiveNoticeDetail> list = wmsReceiveNoticeDetailService.selectWmsReceiveNoticeDetailList(wmsReceiveNoticeDetail);
        ExcelUtil<WmsReceiveNoticeDetail> util = new ExcelUtil<WmsReceiveNoticeDetail>(WmsReceiveNoticeDetail.class);
        util.exportExcel(response, list, "入库通知明细数据");
    }

    /**
     * 获取入库通知明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:detail')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsReceiveNoticeDetailService.selectWmsReceiveNoticeDetailById(id));
    }

    /**
     * 新增入库通知明细
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNoticeDetail:add')")
    @Log(title = "入库通知明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsReceiveNoticeDetail wmsReceiveNoticeDetail) {
        return toAjax(wmsReceiveNoticeDetailService.insertWmsReceiveNoticeDetail(wmsReceiveNoticeDetail));
    }

    /**
     * 修改入库通知明细
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNoticeDetail:edit')")
    @Log(title = "入库通知明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsReceiveNoticeDetail wmsReceiveNoticeDetail) {
        return toAjax(wmsReceiveNoticeDetailService.updateWmsReceiveNoticeDetail(wmsReceiveNoticeDetail));
    }

    /**
     * 删除入库通知明细
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNoticeDetail:remove')")
    @Log(title = "入库通知明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsReceiveNoticeDetailService.deleteWmsReceiveNoticeDetailByIds(ids));
    }


    /**
     * 收货
     *
     * @param wmsReceiveNoticeDetail 收货通知详情
     * @return {@link AjaxResult}
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:receipt')")
    @Log(title = "收货", businessType = BusinessType.UPDATE)
    @PutMapping("/receipt")
    public AjaxResult receipt(@RequestBody WmsReceiveNoticeDetail wmsReceiveNoticeDetail) {
        return wmsReceiveNoticeDetailService.receipt(wmsReceiveNoticeDetail);
    }

    /**
     * 收货
     *
     * @param wmsReceiveNoticeDetails 收货物料详情
     * @return {@link AjaxResult}
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:receipt')")
    @Log(title = "收货", businessType = BusinessType.UPDATE)
    @PutMapping("/batchReceipt")
    public AjaxResult batchReceipt(@RequestBody List<WmsReceiveNoticeDetail> wmsReceiveNoticeDetails) {
        if (wmsReceiveNoticeDetails == null || wmsReceiveNoticeDetails.size() == 0) {
            return AjaxResult.error("收货物料不能为空");
        }
        wmsReceiveNoticeDetails.forEach(wmsReceiveNoticeDetailService::receipt);
        return success();
    }


    /**
     * 分配
     *
     * @param wmsReceiveNoticeDetail 收货通知详情
     * @return {@link AjaxResult}
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:receipt')")
    @Log(title = "分配", businessType = BusinessType.UPDATE)
    @PutMapping("/allot")
    public AjaxResult allot(@RequestBody WmsReceiveNoticeDetail wmsReceiveNoticeDetail) {
        return wmsReceiveNoticeDetailService.allot(wmsReceiveNoticeDetail);
    }

    /**
     * 分配
     *
     * @param wmsReceiveNoticeDetails 分配物料详情
     * @return {@link AjaxResult}
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:receipt')")
    @Log(title = "分配", businessType = BusinessType.UPDATE)
    @PutMapping("/batchAllot")
    public AjaxResult batchAllot(@RequestBody List<WmsReceiveNoticeDetail> wmsReceiveNoticeDetails) {
        if (wmsReceiveNoticeDetails.isEmpty()) {
            return AjaxResult.error("参数错误");
        }
        wmsReceiveNoticeDetails.forEach(wmsReceiveNoticeDetailService::allot);
        return success();
    }


}
