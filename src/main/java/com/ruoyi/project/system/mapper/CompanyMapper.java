package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.Company;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 公司管理Mapper接口
 *
 * @author Rem
 * @date 2023-03-27
 */
public interface CompanyMapper {
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
     * 删除公司管理
     *
     * @param id 公司管理主键
     * @return 结果
     */
    public int deleteCompanyById(String id);

    /**
     * 批量删除公司管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCompanyByIds(String[] ids);

    Company checkCompanyNameUnique(Company company);

    List<Company> selectChildrenCompanyById(String id);

    void updateCompanyChildren(@Param("companies") List<Company> companies);

    int hasChildByCompanyId(String id);

    List<Company> selectCompanyByIds(List<String> companyIds);
}
