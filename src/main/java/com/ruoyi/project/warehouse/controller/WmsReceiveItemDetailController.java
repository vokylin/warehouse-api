package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsReceiveItemDetail;
import com.ruoyi.project.warehouse.service.IWmsReceiveItemDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 入库货物明细Controller
 *
 * @author Rem
 * @date 2023-03-31
 */
@RestController
@RequestMapping("/warehouse/wmsReceiveItemDetail")
public class WmsReceiveItemDetailController extends BaseController {
    @Autowired
    private IWmsReceiveItemDetailService wmsReceiveItemDetailService;

    /**
     * 查询入库货物明细列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:detail')")
    @GetMapping("/list")
    public TableDataInfo list(WmsReceiveItemDetail wmsReceiveItemDetail) {
        startPage();
        List<WmsReceiveItemDetail> list = wmsReceiveItemDetailService.selectWmsReceiveItemDetailList(wmsReceiveItemDetail);
        return getDataTable(list);
    }

    /**
     * 导出入库货物明细列表
     */
    @PreAuthorize("@ss.hasPermi('receiveItemDetail:wmsReceiveItemDetail:export')")
    @Log(title = "入库货物明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsReceiveItemDetail wmsReceiveItemDetail) {
        List<WmsReceiveItemDetail> list = wmsReceiveItemDetailService.selectWmsReceiveItemDetailList(wmsReceiveItemDetail);
        ExcelUtil<WmsReceiveItemDetail> util = new ExcelUtil<WmsReceiveItemDetail>(WmsReceiveItemDetail.class);
        util.exportExcel(response, list, "入库货物明细数据");
    }

    /**
     * 获取入库货物明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:detail')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsReceiveItemDetailService.selectWmsReceiveItemDetailById(id));
    }

    /**
     * 新增入库货物明细
     */
    @PreAuthorize("@ss.hasPermi('receiveItemDetail:wmsReceiveItemDetail:add')")
    @Log(title = "入库货物明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsReceiveItemDetail wmsReceiveItemDetail) {
        return toAjax(wmsReceiveItemDetailService.insertWmsReceiveItemDetail(wmsReceiveItemDetail));
    }

    /**
     * 修改入库货物明细
     */
    @PreAuthorize("@ss.hasPermi('receiveItemDetail:wmsReceiveItemDetail:edit')")
    @Log(title = "入库货物明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsReceiveItemDetail wmsReceiveItemDetail) {
        return toAjax(wmsReceiveItemDetailService.updateWmsReceiveItemDetail(wmsReceiveItemDetail));
    }

    /**
     * 删除入库货物明细
     */
    @PreAuthorize("@ss.hasPermi('receiveItemDetail:wmsReceiveItemDetail:remove')")
    @Log(title = "入库货物明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsReceiveItemDetailService.deleteWmsReceiveItemDetailByIds(ids));
    }

    /**
     * 上架
     *
     * @param wmsReceiveItemDetail wms收到项目细节
     * @return {@link AjaxResult}
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:shelves')")
    @Log(title = "上架", businessType = BusinessType.DELETE)
    @PutMapping("/shelves")
    public AjaxResult shelves(@RequestBody WmsReceiveItemDetail wmsReceiveItemDetail) {
        return wmsReceiveItemDetailService.shelves(wmsReceiveItemDetail);
    }

    /**
     * 批量上架
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:shelves')")
    @Log(title = "批量上架", businessType = BusinessType.DELETE)
    @PutMapping("/batchShelves")
    public AjaxResult batchShelves(@RequestBody WmsReceiveItemDetail wmsReceiveItemDetail) throws Exception {
        return toAjax(wmsReceiveItemDetailService.batchShelves(wmsReceiveItemDetail));
    }


    /**
     * 取消
     *
     * @param wmsReceiveItemDetail wms收到项目细节
     * @return {@link AjaxResult}
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:shelves')")
    @Log(title = "取消分配", businessType = BusinessType.UPDATE)
    @PutMapping("/cancel")
    public AjaxResult cancel(@RequestBody WmsReceiveItemDetail wmsReceiveItemDetail) {
        return wmsReceiveItemDetailService.cancel(wmsReceiveItemDetail);
    }

    /**
     * 质检
     *
     * @param wmsReceiveItemDetail wms收到项目细节
     * @return {@link AjaxResult}
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:shelves')")
    @Log(title = "质检作业", businessType = BusinessType.UPDATE)
    @PutMapping("/qualityCheck")
    public AjaxResult qualityCheck(@RequestBody WmsReceiveItemDetail wmsReceiveItemDetail) {
        return wmsReceiveItemDetailService.qualityCheck(wmsReceiveItemDetail);
    }

    /**
     * 取消全部
     *
     * @param wmsReceiveItemDetail wms收到项目细节
     * @return {@link AjaxResult}
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:shelves')")
    @Log(title = "取消全部", businessType = BusinessType.UPDATE)
    @PutMapping("/cancelAll")
    public AjaxResult cancelAll(@RequestBody WmsReceiveItemDetail wmsReceiveItemDetail) {
        return wmsReceiveItemDetailService.cancelAll(wmsReceiveItemDetail);
    }


}
