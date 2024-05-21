package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.Warehouse;
import com.ruoyi.project.system.service.IRoleWhService;
import com.ruoyi.project.system.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 仓库Controller
 *
 * @author Rem
 * @date 2023-03-27
 */
@RestController
@RequestMapping("/system/warehouse")
public class WarehouseController extends BaseController {
    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IRoleWhService roleWhService;

    /**
     * 查询仓库列表
     */
    @PreAuthorize("@ss.hasPermi('system:warehouse:list')")
    @GetMapping("/list")
    public TableDataInfo list(Warehouse warehouse) {
        startPage();
        List<Warehouse> list = warehouseService.selectWarehouseList(warehouse);
        return getDataTable(list);
    }

    /**
     * 导出仓库列表
     */
    @PreAuthorize("@ss.hasPermi('system:warehouse:export')")
    @Log(title = "仓库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Warehouse warehouse) {
        List<Warehouse> list = warehouseService.selectWarehouseList(warehouse);
        ExcelUtil<Warehouse> util = new ExcelUtil<Warehouse>(Warehouse.class);
        util.exportExcel(response, list, "仓库数据");
    }

    /**
     * 获取仓库详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(warehouseService.selectWarehouseById(id));
    }

    /**
     * 新增仓库
     */
    @PreAuthorize("@ss.hasPermi('system:warehouse:add')")
    @Log(title = "仓库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Warehouse warehouse) {
        warehouse.setId(IdUtils.simpleUUID());
        warehouse.setCreateBy(getUserId());
        return toAjax(warehouseService.insertWarehouse(warehouse));
    }

    /**
     * 修改仓库
     */
    @PreAuthorize("@ss.hasPermi('system:warehouse:edit')")
    @Log(title = "仓库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Warehouse warehouse) {
        warehouse.setUpdateBy(getUserId());
        return toAjax(warehouseService.updateWarehouse(warehouse));
    }

    /**
     * 删除仓库
     */
    @PreAuthorize("@ss.hasPermi('system:warehouse:remove')")
    @Log(title = "仓库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(warehouseService.deleteWarehouseByIds(ids));
    }


    /**
     * 获取仓库下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(Warehouse warehouse) {
        return AjaxResult.success(warehouseService.buildWarehouseTreeSelect(warehouse));
    }

    /**
     * 加载对应角色仓库列表树
     */
    @GetMapping(value = "/roleWarehouseTreeselect/{roleId}")
    public AjaxResult roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", roleWhService.selectListByRoleId(roleId));
        ajax.put("warehouses", warehouseService.buildWarehouseTreeSelect(new Warehouse()));
        return ajax;
    }

    @GetMapping("/getWarehouseByCompanyId/{companyId}")
    public AjaxResult getWarehouseByCompanyId(@PathVariable("companyId") String companyId) {
        return AjaxResult.success(warehouseService.getWarehouseByCompanyId(companyId));
    }

    @GetMapping("/getWarehouseList")
    public AjaxResult getWarehouseList() {
        List<String> warehouseIds = roleWhService.selectLitByUser();
        return AjaxResult.success(warehouseService.selectByWarehouseIds(warehouseIds));
    }

}
