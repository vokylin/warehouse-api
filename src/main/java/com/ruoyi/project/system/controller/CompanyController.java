package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.Company;
import com.ruoyi.project.system.service.ICompanyService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 公司管理Controller
 *
 * @author Rem
 * @date 2023-03-27
 */
@RestController
@RequestMapping("/system/company")
public class CompanyController extends BaseController {
    @Autowired
    private ICompanyService companyService;

    /**
     * 查询公司管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:company:list')")
    @GetMapping("/list")
    public AjaxResult list(Company company) {
        List<Company> list = companyService.selectCompanyList(company);
        return success(list);
    }

    /**
     * 查询公司管理列表(排除)
     */
    @GetMapping("/list/exclude/{companyId}")
    public AjaxResult excludeChild(
            @PathVariable(value = "companyId", required = false) String companyId) {
        List<Company> companies = companyService.selectCompanyList(new Company());
        companies.removeIf(
                d ->
                        d.getId().equals(companyId)
                                || ArrayUtils.contains(StringUtils.split(d.getParentIds(), ","), companyId + ""));
        return success(companies);
    }

    /**
     * 导出公司管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:company:export')")
    @Log(title = "公司管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Company company) {
        List<Company> list = companyService.selectCompanyList(company);
        ExcelUtil<Company> util = new ExcelUtil<Company>(Company.class);
        util.exportExcel(response, list, "公司管理数据");
    }

    /**
     * 获取公司管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(companyService.selectCompanyById(id));
    }

    /**
     * 新增公司管理
     */
    @PreAuthorize("@ss.hasPermi('system:company:add')")
    @Log(title = "公司管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Company company) {

        if (!companyService.checkCompanyNameUnique(company)) {
            return error("新增公司'" + company.getName() + "'失败，公司名称已存在");
        }
        company.setCreateBy(getUserId());
        return toAjax(companyService.insertCompany(company));
    }

    /**
     * 修改公司管理
     */
    @PreAuthorize("@ss.hasPermi('system:company:edit')")
    @Log(title = "公司管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Company company) {

        if (!companyService.checkCompanyNameUnique(company)) {
            return error("修改公司'" + company.getName() + "'失败，公司名称已存在");
        } else if (StringUtils.isNotNull(company.getParentId())
                && company.getParentId().equals(company.getId())) {
            return error("修改公司'" + company.getName() + "'失败，上级公司不能是自己");
        }
        company.setUpdateBy(getUserId());
        return toAjax(companyService.updateCompany(company));
    }

    /**
     * 删除公司管理
     */
    @PreAuthorize("@ss.hasPermi('system:company:remove')")
    @Log(title = "公司管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String id) {

        if (companyService.hasChildByCompanyId(id)) {
            return error("存在下级公司,不允许删除");
        }
//        if(companyService.checkCompanyExistWarehouse(id)){
//            return error("公司存在仓库,不允许删除");
//        }
        return toAjax(companyService.deleteCompanyById(id));
    }
}
