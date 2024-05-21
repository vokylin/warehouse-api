package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.ProductInfo;
import com.ruoyi.project.system.service.IProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 物料主档Controller
 *
 * @author Rem
 * @date 2023-03-28
 */
@RestController
@RequestMapping("/system/productInfo")
public class ProductInfoController extends BaseController {
    @Autowired
    private IProductInfoService productInfoService;

    /**
     * 查询物料主档列表
     */
    @PreAuthorize("@ss.hasPermi('system:productInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductInfo productInfo) {
        //TODO: 调用管理中心服务查询物料主档
        startPage();
        List<ProductInfo> list = productInfoService.selectProductInfoList(productInfo);
        return getDataTable(list);
    }

    /**
     * 导出物料主档列表
     */
    @PreAuthorize("@ss.hasPermi('system:productInfo:export')")
    @Log(title = "物料主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductInfo productInfo) {
        List<ProductInfo> list = productInfoService.selectProductInfoList(productInfo);
        ExcelUtil<ProductInfo> util = new ExcelUtil<ProductInfo>(ProductInfo.class);
        util.exportExcel(response, list, "物料主档数据");
    }

    /**
     * 获取物料主档详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:productInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(productInfoService.selectProductInfoById(id));
    }

    /**
     * 新增物料主档
     */
    @PreAuthorize("@ss.hasPermi('system:productInfo:add')")
    @Log(title = "物料主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductInfo productInfo) {
        return toAjax(productInfoService.insertProductInfo(productInfo));
    }

    /**
     * 修改物料主档
     */
    @PreAuthorize("@ss.hasPermi('system:productInfo:edit')")
    @Log(title = "物料主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductInfo productInfo) {
        return toAjax(productInfoService.updateProductInfo(productInfo));
    }

    /**
     * 删除物料主档
     */
    @PreAuthorize("@ss.hasPermi('system:productInfo:remove')")
    @Log(title = "物料主档", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(productInfoService.deleteProductInfoByIds(ids));
    }
}
