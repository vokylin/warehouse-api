package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.StorageUnitType;
import com.ruoyi.project.system.service.IStorageUnitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 存储单元类型Controller
 *
 * @author Rem
 * @date 2023-03-30
 */
@RestController
@RequestMapping("/system/storageUnitType")
public class StorageUnitTypeController extends BaseController {
    @Autowired
    private IStorageUnitTypeService storageUnitTypeService;

    /**
     * 查询存储单元类型列表
     */
    @PreAuthorize("@ss.hasPermi('system:storageUnitType:list')")
    @GetMapping("/list")
    public TableDataInfo list(StorageUnitType storageUnitType) {
        startPage();
        List<StorageUnitType> list = storageUnitTypeService.selectStorageUnitTypeList(storageUnitType);
        return getDataTable(list);
    }

    /**
     * 导出存储单元类型列表
     */
    @Log(title = "存储单元类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StorageUnitType storageUnitType) {
        List<StorageUnitType> list = storageUnitTypeService.selectStorageUnitTypeList(storageUnitType);
        ExcelUtil<StorageUnitType> util = new ExcelUtil<StorageUnitType>(StorageUnitType.class);
        util.exportExcel(response, list, "存储单元类型数据");
    }

    /**
     * 获取存储单元类型详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(storageUnitTypeService.selectStorageUnitTypeById(id));
    }

    /**
     * 新增存储单元类型
     */
    @PreAuthorize("@ss.hasPermi('system:storageUnitType:add')")
    @Log(title = "存储单元类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StorageUnitType storageUnitType) {

        if (!storageUnitTypeService.checkStorageUnitTypeNameUnique(storageUnitType)) {
            return error("新增存储单元类型'" + storageUnitType.getName() + "'失败，存储单元类型名称已存在");
        }
        if (!storageUnitTypeService.checkStorageUnitTypeCodeUnique(storageUnitType)) {
            return error("新增存储单元类型'" + storageUnitType.getName() + "'失败，存储单元类型编码已存在");
        }
        storageUnitType.setCreateBy(getUserId());
        storageUnitType.setCreateUserName(getUsername());
        return toAjax(storageUnitTypeService.insertStorageUnitType(storageUnitType));
    }

    /**
     * 修改存储单元类型
     */
    @PreAuthorize("@ss.hasPermi('system:storageUnitType:edit')")
    @Log(title = "存储单元类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StorageUnitType storageUnitType) {

        if (!storageUnitTypeService.checkStorageUnitTypeNameUnique(storageUnitType)) {
            return error("修改存储单元类型'" + storageUnitType.getName() + "'失败，存储单元类型名称已存在");
        }
        if (StringUtils.isNotNull(storageUnitType.getParentId()) && storageUnitType.getParentId().equals(storageUnitType.getId())) {
            return error("修改存储单元类型'" + storageUnitType.getName() + "'失败，上级存储单元类型不能是自己");
        }
        if (!storageUnitTypeService.checkStorageUnitTypeCodeUnique(storageUnitType)) {
            return error("修改存储单元类型'" + storageUnitType.getName() + "'失败，存储单元类型编码已存在");
        }
        storageUnitType.setUpdateBy(getUserId());
        storageUnitType.setUpdateUserName(getUsername());
        return toAjax(storageUnitTypeService.updateStorageUnitType(storageUnitType));
    }

    /**
     * 删除存储单元类型
     */
    @PreAuthorize("@ss.hasPermi('system:storageUnitType:remove')")
    @Log(title = "存储单元类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
//        for (String id : ids) {
//            if (storageUnitTypeService.hasChildByParentId(id)) {
//                return error("存在下级存储单元类型,不允许删除");
//            }
//        }
        return toAjax(storageUnitTypeService.deleteStorageUnitTypeByIds(ids));
    }

    /**
     * 加载存储单元类型列表树
     */
    @GetMapping("/tree")
    public AjaxResult treeData(StorageUnitType storageUnitType) {
        List<StorageUnitType> storageUnitTypes = storageUnitTypeService.selectStorageUnitTypeList(storageUnitType);
        return success(storageUnitTypeService.buildStorageUnitTypeTreeSelect(storageUnitTypes));
    }


    /**
     * 加载存储单元类型下拉列表
     *
     * @param storageUnitType 存储单元类型
     * @return {@link AjaxResult}
     */
    @GetMapping("/selectData")
    public AjaxResult selectData(StorageUnitType storageUnitType) {
        List<StorageUnitType> storageUnitTypes = storageUnitTypeService.selectStorageUnitTypeList(storageUnitType);
        return success(storageUnitTypes);
    }
}
