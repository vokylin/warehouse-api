package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.web.domain.BaseTreeSelect;
import com.ruoyi.project.system.domain.*;
import com.ruoyi.project.system.mapper.CompanyMapper;
import com.ruoyi.project.system.mapper.RoleWhMapper;
import com.ruoyi.project.system.mapper.SysUserRoleMapper;
import com.ruoyi.project.system.mapper.WarehouseMapper;
import com.ruoyi.project.system.service.ICompanyService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements ICompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private RoleWhMapper roleWhMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    /**
     * 查询公司管理
     *
     * @param id 公司管理主键
     * @return 公司管理
     */
    @Override
    public Company selectCompanyById(String id) {
        return companyMapper.selectCompanyById(id);
    }

    /**
     * 查询公司管理列表
     *
     * @param company 公司管理
     * @return 公司管理集合
     */
    @Override
    public List<Company> selectCompanyList(Company company) {
        return companyMapper.selectCompanyList(company);
    }

    /**
     * 新增公司管理
     *
     * @param company 公司管理
     * @return 结果
     */
    @Override
    public int insertCompany(Company company) {
        Company companyInfo = companyMapper.selectCompanyById(company.getParentId());
        company.setCreateTime(DateUtils.getNowDate());
        company.setParentIds(companyInfo.getParentIds() + "," + company.getId());
        return companyMapper.insertCompany(company);
    }

    /**
     * 修改公司管理
     *
     * @param company 公司管理
     * @return 结果
     */
    @Override
    public int updateCompany(Company company) {
        // 查询新父节点
        Company newParentCompany = companyMapper.selectCompanyById(company.getParentId());
        // 查询修改前公司
        Company oldCompany = companyMapper.selectCompanyById(company.getId());
        if (Objects.nonNull(newParentCompany) && Objects.nonNull(oldCompany)) {

            String newParents = newParentCompany.getParentIds() + "," + newParentCompany.getId();
            String oldParents = oldCompany.getParentIds();
            company.setParentIds(newParents);
            updateCompanyChildren(company.getId(), newParents, oldParents);
        }

        company.setUpdateTime(DateUtils.getNowDate());
        return companyMapper.updateCompany(company);
    }

    private void updateCompanyChildren(String id, String newParents, String oldParents) {
        List<Company> children = companyMapper.selectChildrenCompanyById(id);
        for (Company child : children) {
            child.setParentIds(child.getParentIds().replace(oldParents, newParents));
        }
        if (children.size() > 0) {
            companyMapper.updateCompanyChildren(children);
        }
    }

    /**
     * 批量删除公司管理
     *
     * @param ids 需要删除的公司管理主键集合
     * @return 结果
     */
    @Override
    public int deleteCompanyByIds(String[] ids) {
        return companyMapper.deleteCompanyByIds(ids);
    }

    /**
     * 删除公司管理信息
     *
     * @param id 公司管理主键
     * @return 结果
     */
    @Override
    public int deleteCompanyById(String id) {
        return companyMapper.deleteCompanyById(id);
    }

    /**
     * 检查公司名称
     *
     * @param company 公司
     * @return boolean
     */
    @Override
    public boolean checkCompanyNameUnique(Company company) {
        Company companyInfo = companyMapper.checkCompanyNameUnique(company);
        if (companyInfo != null && !companyInfo.getId().equals(company.getId())) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 是否存在子节点
     *
     * @param id 公司ID
     * @return 结果
     */
    @Override
    public boolean hasChildByCompanyId(String id) {
        int result = companyMapper.hasChildByCompanyId(id);
        return result > 0;
    }

    /**
     * 选择部门树列表
     *
     * @param company 公司
     * @return {@link List}<{@link Company}>
     */
    @Override
    public List<BaseTreeSelect> selectCompanyTreeList(Company company) {
        List<Company> companyList = companyMapper.selectCompanyList(company);
        return buildCompanyTreeSelect(companyList);
    }

    /**
     * 根据用户id查询公司
     *
     * @param userId 用户id
     * @return {@link List}<{@link Company}>
     */
    @Override
    public List<BaseTreeSelect> getCompanyByUser(SysUser sysUser) {

        // 如果是超级管理员，查询所有公司
        if (sysUser.isAdmin()) {
            return selectCompanyTreeList(new Company());
        }

        // 查询用户的所有角色
        List<SysUserRole> sysUserRoles = userRoleMapper.selectUserRoleByUserId(sysUser.getUserId());

        // 查询角色关联的仓库
        if (CollectionUtils.isNotEmpty(sysUserRoles)) {

            // 查询角色仓库关联表
            List<RoleWh> roleWhs = roleWhMapper.selectRoleWhByRoleIds(sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(roleWhs)) {

                // 获取仓库id集合
                List<String> whIds = roleWhs.stream().map(RoleWh::getWhId).collect(Collectors.toList());

                // 查询仓库
                List<Warehouse> warehouses = warehouseMapper.selectWarehouseByIds(whIds);

                if (CollectionUtils.isNotEmpty(warehouses)) {

                    // 获取公司id
                    List<String> companyIds = warehouses.stream().map(Warehouse::getBelongCompany).collect(Collectors.toList());

                    // 去重
                    companyIds = companyIds.stream().distinct().collect(Collectors.toList());

                    // 查询公司
                    List<Company> companies = companyMapper.selectCompanyByIds(companyIds);
                    if (CollectionUtils.isNotEmpty(companies)) {
                        List<Company> companyList = buildCompanyTree(companies);
                        return buildCompanyTreeSelect(companyList);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 构建公司树
     *
     * @param companyList 公司列表
     * @return {@link List}<{@link Company}>
     */
    private List<Company> buildCompanyTree(List<Company> companyList) {
        List<Company> returnList = new ArrayList<>();
        List<String> tempList = companyList.stream().map(Company::getId).collect(Collectors.toList());
        for (Company company : companyList) {
            if (!tempList.contains(company.getParentId())) {
                recursionFn(companyList, company);
                returnList.add(company);
            }
        }
        if (returnList.isEmpty()) {
            returnList = companyList;
        }
        return returnList;
    }

    private void recursionFn(List<Company> companyList, Company company) {
        List<Company> childList = getChildList(companyList, company);
        company.setChildren(childList);
        for (Company child : childList) {
            if (hasChild(companyList, child)) {
                recursionFn(companyList, child);
            }
        }
    }

    /**
     * 是否存在子节点
     *
     * @param companyList 公司列表
     * @param child       子节点
     * @return boolean
     */
    private boolean hasChild(List<Company> companyList, Company child) {
        return getChildList(companyList, child).size() > 0;
    }

    /**
     * 获取子节点列表
     *
     * @param companyList 公司列表
     * @param company     公司
     * @return {@link List}<{@link Company}>
     */
    private List<Company> getChildList(List<Company> companyList, Company company) {
        List<Company> childList = new ArrayList<>();
        for (Company child : companyList) {
            if (child.getParentId().equals(company.getId())) {
                childList.add(child);
            }
        }
        return childList;
    }

    /**
     * 构建公司树
     *
     * @param companyList 公司列表
     * @return {@link List}<{@link Company}>
     */
    private List<BaseTreeSelect> buildCompanyTreeSelect(List<Company> companyList) {
        List<Company> companies = buildCompanyTree(companyList);
        return companies.stream().map(BaseTreeSelect::new).collect(Collectors.toList());
    }
}
