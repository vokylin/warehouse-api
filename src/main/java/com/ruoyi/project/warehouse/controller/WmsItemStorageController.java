package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.service.IRoleWhService;
import com.ruoyi.project.warehouse.domain.ExportItemStorage;
import com.ruoyi.project.warehouse.domain.ItemStorageIndexCountVO;
import com.ruoyi.project.warehouse.domain.WmsItemStorage;
import com.ruoyi.project.warehouse.service.IWmsItemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存汇总Controller
 *
 * @author Rem
 * @date 2023-04-08
 */
@RestController
@RequestMapping("/warehouse/itemStorage")
public class WmsItemStorageController extends BaseController {

    @Autowired
    private IWmsItemStorageService wmsItemStorageService;

    @Autowired
    private IRoleWhService roleWhService;

    /**
     * 查询库存汇总列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:itemStorage:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsItemStorage wmsItemStorage) {
        wmsItemStorage.setCompanyId(getCompanyId());
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            List<String> warehouseIdList = roleWhService.selectLitByUser();
            if (warehouseIdList != null && warehouseIdList.size() > 0) {
                wmsItemStorage.setWarehouseIdList(warehouseIdList);
            } else {
                return getDataTable(new ArrayList<>());
            }
        }
        startPage();
        List<WmsItemStorage> list = wmsItemStorageService.selectWmsItemStorageList(wmsItemStorage);
        return getDataTable(list);
    }

    /**
     * 导出库存汇总列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:itemStorage:export')")
    @Log(title = "库存汇总", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsItemStorage wmsItemStorage) {
        ExcelUtil<ExportItemStorage> util = new ExcelUtil<>(ExportItemStorage.class);
        wmsItemStorage.setCompanyId(getCompanyId());
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            List<String> warehouseIdList = roleWhService.selectLitByUser();
            if (warehouseIdList != null && warehouseIdList.size() > 0) {
                wmsItemStorage.setWarehouseIdList(warehouseIdList);
            } else {
                List<ExportItemStorage> list = new ArrayList<>();
                util.exportExcel(response, list, "库存汇总数据");
                return;
            }
        }
        List<ExportItemStorage> list = wmsItemStorageService.selectExportList(wmsItemStorage);
        util.exportExcel(response, list, "库存汇总数据");
    }

    /**
     * 获取库存汇总详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:itemStorage:query')")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsItemStorageService.selectWmsItemStorageById(id));
    }

    /**
     * 新增库存汇总
     */
    @PreAuthorize("@ss.hasPermi('warehouse:itemStorage:add')")
    @Log(title = "库存汇总", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsItemStorage wmsItemStorage) {
        return toAjax(wmsItemStorageService.insertWmsItemStorage(wmsItemStorage));
    }

    /**
     * 修改库存汇总
     */
    @PreAuthorize("@ss.hasPermi('warehouse:itemStorage:edit')")
    @Log(title = "库存汇总", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsItemStorage wmsItemStorage) {
        return toAjax(wmsItemStorageService.updateWmsItemStorage(wmsItemStorage));
    }

    /**
     * 删除库存汇总
     */
    @PreAuthorize("@ss.hasPermi('warehouse:itemStorage:remove')")
    @Log(title = "库存汇总", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsItemStorageService.deleteWmsItemStorageByIds(ids));
    }

    /**
     * 首页统计
     */

    @GetMapping("/index")
    public TableDataInfo index() {
        startPage();
        List<ItemStorageIndexCountVO> list = wmsItemStorageService.selectStorageIndexCount("");
        return getDataTable(list);
    }
}
