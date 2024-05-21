package com.ruoyi.project.system.service;

import com.ruoyi.framework.web.domain.BaseTreeSelect;
import com.ruoyi.project.system.domain.Company;
import com.ruoyi.project.system.domain.SysUser;

import java.util.List;

/**
 * 公司管理Service接口
 *
 * @author Rem
 * @date 2023-03-27
 */
public interface ICompanyService {
    /**
     * 查询公司管理
     *
     * @param id 公司管理主键
     * @return 公司管理
     */
    public Company selectCompanyById(String id);

    /**
     * 查询公司管理列表
     *
     * @param company 公司管理
     * @return 公司管理集合
     */
    public List<Company> selectCompanyList(Company company);

    /**
     * 新增公司管理
     *
     * @param company 公司管理
     * @return 结果
     */
    public int insertCompany(Company company);

    /**
     * 修改公司管理
     *
     * @param company 公司管理
     * @return 结果
     */
    public int updateCompany(Company company);

    /**
     * 批量删除公司管理
     *
     * @param ids 需要删除的公司管理主键集合
     * @return 结果
     */
    public int deleteCompanyByIds(String[] ids);

    /**
     * 删除公司管理信息
     *
     * @param id 公司管理主键
     * @return 结果
     */
    public int deleteCompanyById(String id);

    /**
     * 检查公司名称
     *
     * @param company 公司
     * @return boolean
     */
    boolean checkCompanyNameUnique(Company company);

    boolean hasChildByCompanyId(String id);

    List<BaseTreeSelect> selectCompanyTreeList(Company company);

    List<BaseTreeSelect> getCompanyByUser(SysUser user);
}
