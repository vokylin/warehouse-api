package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsExamineConclusion;
import com.ruoyi.project.warehouse.service.IWmsExamineConclusionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * examineConclusionController
 *
 * @author Rem
 * @date 2023-04-21
 */
@RestController
@RequestMapping("/warehouse/examineConclusion")
public class WmsExamineConclusionController extends BaseController {
    @Autowired
    private IWmsExamineConclusionService wmsExamineConclusionService;

    /**
     * 查询examineConclusion列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmsExamineConclusion wmsExamineConclusion) {
        startPage();
        List<WmsExamineConclusion> list = wmsExamineConclusionService.selectWmsExamineConclusionList(wmsExamineConclusion);
        return getDataTable(list);
    }

    /**
     * 导出examineConclusion列表
     */
    @Log(title = "examineConclusion", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsExamineConclusion wmsExamineConclusion) {
        List<WmsExamineConclusion> list = wmsExamineConclusionService.selectWmsExamineConclusionList(wmsExamineConclusion);
        ExcelUtil<WmsExamineConclusion> util = new ExcelUtil<WmsExamineConclusion>(WmsExamineConclusion.class);
        util.exportExcel(response, list, "examineConclusion数据");
    }

    /**
     * 获取examineConclusion详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsExamineConclusionService.selectWmsExamineConclusionById(id));
    }


    /**
     * 获取examineConclusion详细信息
     */
    @GetMapping(value = "/getByExamineId/{id}")
    public AjaxResult getByExamineId(@PathVariable("id") String id) {
        return success(wmsExamineConclusionService.selectByExamineId(id));
    }

    /**
     * 新增examineConclusion
     */
    @Log(title = "examineConclusion", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsExamineConclusion wmsExamineConclusion) {
        return toAjax(wmsExamineConclusionService.insertWmsExamineConclusion(wmsExamineConclusion));
    }

    /**
     * 修改examineConclusion
     */
    @Log(title = "examineConclusion", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsExamineConclusion wmsExamineConclusion) {
        return toAjax(wmsExamineConclusionService.updateWmsExamineConclusion(wmsExamineConclusion));
    }

    /**
     * 删除examineConclusion
     */
    @Log(title = "examineConclusion", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsExamineConclusionService.deleteWmsExamineConclusionByIds(ids));
    }
}
