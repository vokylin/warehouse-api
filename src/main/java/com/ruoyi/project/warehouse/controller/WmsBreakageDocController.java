package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.enums.BreakageDocStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.common.component.CodeService;
import com.ruoyi.project.warehouse.domain.WmsBreakageDoc;
import com.ruoyi.project.warehouse.service.IWmsBreakageDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报损单Controller
 *
 * @author Rem
 * @date 2023-05-20
 */
@RestController
@RequestMapping("/warehouse/breakageDoc")
public class WmsBreakageDocController extends BaseController {
    @Autowired
    private IWmsBreakageDocService wmsBreakageDocService;

    @Autowired
    private CodeService codeService;

    /**
     * 查询报损单列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:breakageDoc:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsBreakageDoc wmsBreakageDoc) {
        wmsBreakageDoc.setCompanyId(getCompanyId());
        startPage();
        List<WmsBreakageDoc> list = wmsBreakageDocService.selectWmsBreakageDocList(wmsBreakageDoc);
        return getDataTable(list);
    }

    /**
     * 导出报损单列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:breakageDoc:export')")
    @Log(title = "报损单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsBreakageDoc wmsBreakageDoc) {
        List<WmsBreakageDoc> list = wmsBreakageDocService.selectWmsBreakageDocList(wmsBreakageDoc);
        ExcelUtil<WmsBreakageDoc> util = new ExcelUtil<WmsBreakageDoc>(WmsBreakageDoc.class);
        util.exportExcel(response, list, "报损单数据");
    }

    /**
     * 获取报损单详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:breakageDoc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsBreakageDocService.selectWmsBreakageDocById(id));
    }

    /**
     * 新增报损单
     */
    @PreAuthorize("@ss.hasPermi('warehouse:breakageDoc:add')")
    @Log(title = "报损单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsBreakageDoc wmsBreakageDoc) {
        String code = codeService.generateBreakageDocCode();
        wmsBreakageDoc.setId(code);
        return toAjax(wmsBreakageDocService.insertWmsBreakageDoc(wmsBreakageDoc));
    }

    /**
     * 修改报损单
     */
    @PreAuthorize("@ss.hasPermi('warehouse:breakageDoc:edit')")
    @Log(title = "报损单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsBreakageDoc wmsBreakageDoc) {
        if (!wmsBreakageDoc.getStatus().equals(BreakageDocStatus.SUBMIT.getCode())) {
            return error("不是待审核提交, 不能修改");
        }
        wmsBreakageDoc.setUpdateBy(getUserId());
        wmsBreakageDoc.setUpdateTime(DateUtils.getNowDate());
        return toAjax(wmsBreakageDocService.updateWmsBreakageDoc(wmsBreakageDoc));
    }

    /**
     * 删除报损单
     */
    @PreAuthorize("@ss.hasPermi('warehouse:breakageDoc:remove')")
    @Log(title = "报损单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) throws Exception {
        return wmsBreakageDocService.deleteWmsBreakageDocByIds(ids);
    }

    /**
     * 提交报损单
     */
    @PreAuthorize("@ss.hasPermi('warehouse:breakageDoc:submit')")
    @Log(title = "报损单", businessType = BusinessType.UPDATE)
    @PutMapping("/submit/{id}")
    public AjaxResult submit(@PathVariable("id") String id) throws Exception {
        return wmsBreakageDocService.submitWmsBreakageDocById(id);
    }


    @Log(title = "报损单", businessType = BusinessType.UPDATE)
    @PutMapping("/approved/{id}")
    public AjaxResult approved(@PathVariable("id") String id) throws Exception {
        wmsBreakageDocService.approved(id);
        return AjaxResult.success();
    }
}
