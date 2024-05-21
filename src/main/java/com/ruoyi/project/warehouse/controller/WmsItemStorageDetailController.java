package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.common.component.StorageManager;
import com.ruoyi.project.system.service.IRoleWhService;
import com.ruoyi.project.warehouse.domain.*;
import com.ruoyi.project.warehouse.service.IWmsDeliveryNoticeDetailService;
import com.ruoyi.project.warehouse.service.IWmsItemStorageDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存详情Controller
 *
 * @author Rem
 * @date 2023-04-08
 */
@RestController
@RequestMapping("/warehouse/itemStorageDetail")
public class WmsItemStorageDetailController extends BaseController {
    @Autowired
    private IWmsItemStorageDetailService wmsItemStorageDetailService;

    @Autowired
    private IWmsDeliveryNoticeDetailService wmsDeliveryNoticeDetailService;

    @Autowired
    private StorageManager storageManager;

    @Autowired
    private IRoleWhService roleWhService;

    /**
     * 查询库存详情列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:itemStorageDetail:list')")
    public TableDataInfo list(WmsItemStorageDetail wmsItemStorageDetail) {
        startPage();
        List<WmsItemStorageDetail> list = wmsItemStorageDetailService.selectWmsItemStorageDetailList(wmsItemStorageDetail);
        return getDataTable(list);
    }


    /**
     * 查询库存详情列表
     */
    @GetMapping("/alertList")
    public TableDataInfo selectStorageAlertList() {
        startPage();
        List<StorageAlertVO> list = wmsItemStorageDetailService.selectStorageAlertList();
        return getDataTable(list);
    }


    /**
     * 查询库存详情列表
     */
    @GetMapping("/listByUser")
    public TableDataInfo listByUser(WmsItemStorageDetail wmsItemStorageDetail) {
        List<WmsItemStorageDetail> list = new ArrayList<>();
        if (SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            startPage();
            list = wmsItemStorageDetailService.selectWmsItemStorageDetailList(wmsItemStorageDetail);
        } else {
            List<String> warehouseIdList = roleWhService.selectLitByUser();
            if (warehouseIdList != null && warehouseIdList.size() > 0) {
                wmsItemStorageDetail.setWarehouseIdList(warehouseIdList);
                startPage();
                list = wmsItemStorageDetailService.selectWmsItemStorageDetailListByUser(wmsItemStorageDetail);
            }
        }
        return getDataTable(list);
    }

    /**
     * 报损查询库存详情列表
     */
    @GetMapping("/breakageList")
    public TableDataInfo breakageList(WmsItemStorageDetail wmsItemStorageDetail) {
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            List<String> warehouseIdList = roleWhService.selectLitByUser();
            if (warehouseIdList != null && warehouseIdList.size() > 0) {
                wmsItemStorageDetail.setWarehouseIdList(warehouseIdList);
            }
        }
        startPage();
        List<WmsItemStorageDetail> list = wmsItemStorageDetailService.selectBreakageList(wmsItemStorageDetail);
        return getDataTable(list);
    }


    /**
     * 导出库存详情列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:itemStorageDetail:export')")
    @Log(title = "库存详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsItemStorageDetail wmsItemStorageDetail) {
        List<WmsItemStorageDetail> list = wmsItemStorageDetailService.selectWmsItemStorageDetailList(wmsItemStorageDetail);
        ExcelUtil<WmsItemStorageDetail> util = new ExcelUtil<WmsItemStorageDetail>(WmsItemStorageDetail.class);
        util.exportExcel(response, list, "库存详情数据");
    }

    /**
     * 获取库存详情详细信息
     */
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsItemStorageDetailService.selectWmsItemStorageDetailById(id));
    }

    /**
     * 新增库存详情
     */
    @PreAuthorize("@ss.hasPermi('warehouse:itemStorageDetail:add')")
    @Log(title = "库存详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsItemStorageDetail wmsItemStorageDetail) {
        return toAjax(wmsItemStorageDetailService.insertWmsItemStorageDetail(wmsItemStorageDetail));
    }

    /**
     * 修改库存详情
     */
    @Log(title = "库存详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsItemStorageDetail wmsItemStorageDetail) {
        return wmsItemStorageDetailService.update(wmsItemStorageDetail);
    }

    /**
     * 删除库存详情
     */
    @PreAuthorize("@ss.hasPermi('warehouse:itemStorageDetail:remove')")
    @Log(title = "库存详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsItemStorageDetailService.deleteWmsItemStorageDetailByIds(ids));
    }

    /**
     * 分配
     */
    @Log(title = "库存详情", businessType = BusinessType.UPDATE)
    @PutMapping("/allot")
    public AjaxResult allot(@RequestBody WmsItemStorageDetail wmsItemStorageDetail) {
        // 更新出库通知详情数量
        return storageManager.allotItemStorage(wmsItemStorageDetail);
    }

    /**
     * 批量分配
     */
    @Log(title = "库存详情", businessType = BusinessType.UPDATE)
    @PutMapping("/allotBatch")
    public AjaxResult allotBatch(@RequestBody List<WmsItemStorageDetail> wmsItemStorageDetails) {
        for (WmsItemStorageDetail wmsItemStorageDetail : wmsItemStorageDetails) {
            storageManager.allotItemStorage(wmsItemStorageDetail);
        }
        return AjaxResult.success();
    }

    /**
     * 取消分配
     */
    @Log(title = "库存详情", businessType = BusinessType.UPDATE)
    @PutMapping("/cancelAllot")
    public AjaxResult cancelAllot(@RequestBody WmsItemStorageDetail wmsItemStorageDetail) throws Exception {
        storageManager.cancelAllot(new ItemStorageDetailDto(wmsItemStorageDetail));
        // 更新出库通知详情数量
        WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail = wmsDeliveryNoticeDetailService.selectWmsDeliveryNoticeDetailById(wmsItemStorageDetail.getDeliveryNoticeDetailId());
        WmsDeliveryNoticeDetail updateDeliveryNoticeDetail = new WmsDeliveryNoticeDetail();
        updateDeliveryNoticeDetail.setId(wmsItemStorageDetail.getDeliveryNoticeDetailId());
        updateDeliveryNoticeDetail.setAllotQuantity(wmsDeliveryNoticeDetail.getAllotQuantity().subtract(wmsItemStorageDetail.getQuantity()));
        wmsDeliveryNoticeDetailService.updateWmsDeliveryNoticeDetail(updateDeliveryNoticeDetail);
        return AjaxResult.success();
    }


    /**
     * 批量取消分配
     */
    @Log(title = "库存详情", businessType = BusinessType.UPDATE)
    @PutMapping("/cancelAllotBatch")
    public AjaxResult cancelAllotBatch(@RequestBody List<WmsItemStorageDetail> wmsItemStorageDetails) throws Exception {
        for (WmsItemStorageDetail wmsItemStorageDetail : wmsItemStorageDetails) {
            storageManager.cancelAllot(new ItemStorageDetailDto(wmsItemStorageDetail));
            // 更新出库通知详情数量
            WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail = wmsDeliveryNoticeDetailService.selectWmsDeliveryNoticeDetailById(wmsItemStorageDetail.getDeliveryNoticeDetailId());
            WmsDeliveryNoticeDetail updateDeliveryNoticeDetail = new WmsDeliveryNoticeDetail();
            updateDeliveryNoticeDetail.setId(wmsItemStorageDetail.getDeliveryNoticeDetailId());
            updateDeliveryNoticeDetail.setAllotQuantity(wmsDeliveryNoticeDetail.getAllotQuantity().subtract(wmsItemStorageDetail.getQuantity()));
            wmsDeliveryNoticeDetailService.updateWmsDeliveryNoticeDetail(updateDeliveryNoticeDetail);
        }
        return AjaxResult.success();
    }

    /**
     * 移位
     */

    @PreAuthorize("@ss.hasPermi('warehouse:itemStorageDetail:displacement')")
    @Log(title = "库存详情", businessType = BusinessType.UPDATE)
    @PutMapping("/displacement")
    public AjaxResult displacement(@RequestBody List<WmsItemStorageDetail> wmsItemStorageDetails) {
        return storageManager.displacement(wmsItemStorageDetails);
    }

    /**
     * 调拨
     */
    @PreAuthorize("@ss.hasPermi('warehouse:itemStorageDetail:allocate')")
    @Log(title = "库存详情", businessType = BusinessType.UPDATE)
    @PutMapping("/allocate")
    public AjaxResult allocate(@RequestBody AllocateDto allocateDto) {
        return storageManager.allocate(allocateDto);
    }

}
