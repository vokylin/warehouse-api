package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.web.domain.WarehouseTreeSelect;
import com.ruoyi.project.system.domain.Company;
import com.ruoyi.project.system.domain.Warehouse;
import com.ruoyi.project.system.mapper.WarehouseMapper;
import com.ruoyi.project.system.service.ICompanyService;
import com.ruoyi.project.system.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 仓库Service业务层处理
 *
 * @author Rem
 * @date 2023-03-27
 */
@Service
public class WarehouseServiceImpl implements IWarehouseService {
    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private ICompanyService companyService;

    /**
     * 查询仓库
     *
     * @param id 仓库主键
     * @return 仓库
     */
    @Override
    public Warehouse selectWarehouseById(String id) {
        return warehouseMapper.selectWarehouseById(id);
    }

    /**
     * 查询仓库列表
     *
     * @param warehouse 仓库
     * @return 仓库
     */
    @Override
    public List<Warehouse> selectWarehouseList(Warehouse warehouse) {
        return warehouseMapper.selectWarehouseList(warehouse);
    }

    @Override
    public List<Warehouse> selectWarehouseListByCompanyId(String companyId) {
        return warehouseMapper.selectWarehouseListByCompanyId(companyId);
    }

    /**
     * 新增仓库
     *
     * @param warehouse 仓库
     * @return 结果
     */
    @Override
    public int insertWarehouse(Warehouse warehouse) {
        warehouse.setCreateTime(DateUtils.getNowDate());
        return warehouseMapper.insertWarehouse(warehouse);
    }

    /**
     * 修改仓库
     *
     * @param warehouse 仓库
     * @return 结果
     */
    @Override
    public int updateWarehouse(Warehouse warehouse) {
        warehouse.setUpdateTime(DateUtils.getNowDate());
        return warehouseMapper.updateWarehouse(warehouse);
    }

    /**
     * 批量删除仓库
     *
     * @param ids 需要删除的仓库主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseByIds(String[] ids) {
        return warehouseMapper.deleteWarehouseByIds(ids);
    }

    /**
     * 删除仓库信息
     *
     * @param id 仓库主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseById(String id) {
        return warehouseMapper.deleteWarehouseById(id);
    }

    @Override
    public List<WarehouseTreeSelect> buildWarehouseTreeSelect(Warehouse warehouse) {
        List<Company> companyList = companyService.selectCompanyList(new Company());
        return buildCompanyTreeSelect(companyList);
    }

    @Override
    public List<Warehouse> getWarehouseByCompanyId(String companyId) {
        return warehouseMapper.getWarehouseByCompanyId(companyId);
    }

    @Override
    public List<Warehouse> selectByWarehouseIds(List<String> warehouseIds) {
        if (SecurityUtils.isAdmin(SecurityUtils.getLoginUser().getUser().getUserId())) {
            return warehouseMapper.selectWarehouseListByCompanyId(ServletUtils.getCompanyId());
        }
        return warehouseMapper.selectByWarehouseIds(warehouseIds);
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
    private List<WarehouseTreeSelect> buildCompanyTreeSelect(List<Company> companyList) {
        List<Company> companies = buildCompanyTree(companyList);
        // 公司树 companies
        return companies.stream().map(WarehouseTreeSelect::new).collect(Collectors.toList());
    }


}
