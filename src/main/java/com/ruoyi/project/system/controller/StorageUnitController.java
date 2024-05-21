package com.ruoyi.project.system.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.StorageUnit;
import com.ruoyi.project.system.domain.Warehouse;
import com.ruoyi.project.system.service.IStorageUnitService;
import com.ruoyi.project.system.service.IWarehouseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 存储单元Controller
 *
 * @author Rem
 * @date 2023-03-29
 */
@RestController
@RequestMapping("/system/storageUnit")
public class StorageUnitController extends BaseController {

    @Autowired
    private IStorageUnitService storageUnitService;

    @Autowired
    private IWarehouseService warehouseService;

    /**
     * 查询存储单元列表
     */
    @PreAuthorize("@ss.hasPermi('system:storageUnit:list')")
    @GetMapping("/list")
    public TableDataInfo list(StorageUnit storageUnit) {

        if (StringUtils.isBlank(storageUnit.getWarehouseId())) {
            String storageUnitId = ServletUtils.getCompanyId();
            List<Warehouse> warehouses = warehouseService.selectWarehouseListByCompanyId(storageUnitId);
            if (CollectionUtils.isEmpty(warehouses)) {
                return null;
            }
            List<String> warehouseIds = warehouses.stream().map(Warehouse::getId).collect(Collectors.toList());
            storageUnit.setWarehouseIds(warehouseIds);
        }
        if (StringUtils.isBlank(storageUnit.getParentId())) {
            storageUnit.setParentId(Constants.ROOT_NODE);
        }
        startPage();
        List<StorageUnit> list = storageUnitService.selectStorageUnitList(storageUnit);
        return getDataTable(list);
    }


    /**
     * 查询存储单元列表
     */
    @GetMapping("/listAll")
    public AjaxResult listAll(StorageUnit storageUnit) {
        List<StorageUnit> list = storageUnitService.selectAll(storageUnit);
        return success(list);
    }

    /**
     * 导出存储单元列表
     */
    @PreAuthorize("@ss.hasPermi('system:storageUnit:export')")
    @Log(title = "存储单元", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StorageUnit storageUnit) {
        List<StorageUnit> list = storageUnitService.selectStorageUnitList(storageUnit);
        ExcelUtil<StorageUnit> util = new ExcelUtil<StorageUnit>(StorageUnit.class);
        util.exportExcel(response, list, "存储单元数据");
    }

    /**
     * 获取存储单元详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(storageUnitService.selectStorageUnitById(id));
    }

    /**
     * 获取存储单元树
     */
    @GetMapping("/tree")
    public AjaxResult getTree(StorageUnit storageUnit) {
        return success(storageUnitService.selectStorageUnitTree(storageUnit));
    }

    /**
     * 新增存储单元
     */
    @PreAuthorize("@ss.hasPermi('system:storageUnit:add')")
    @Log(title = "存储单元", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StorageUnit storageUnit) {
        storageUnit.setCreateBy(getUserId());
        storageUnit.setCreateUserName(getUsername());
        return toAjax(storageUnitService.insertStorageUnit(storageUnit));
    }

    /**
     * 修改存储单元
     */
    @PreAuthorize("@ss.hasPermi('system:storageUnit:edit')")
    @Log(title = "存储单元", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StorageUnit storageUnit) {
        storageUnit.setUpdateBy(getUserId());
        storageUnit.setUpdateUserName(getUsername());
        return toAjax(storageUnitService.updateStorageUnit(storageUnit));
    }

    /**
     * 删除存储单元
     */
    @PreAuthorize("@ss.hasPermi('system:storageUnit:remove')")
    @Log(title = "存储单元", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        for (String id : ids) {
            if (storageUnitService.hasChild(id)) {
                return error("存在下级存储单元,不允许删除");
            }
        }
        return toAjax(storageUnitService.deleteStorageUnitByIds(ids));
    }


}
