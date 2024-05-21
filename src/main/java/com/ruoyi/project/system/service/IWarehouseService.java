package com.ruoyi.project.system.service;

import com.ruoyi.framework.web.domain.WarehouseTreeSelect;
import com.ruoyi.project.system.domain.Warehouse;

import java.util.List;

/**
 * 仓库Service接口
 *
 * @author Rem
 * @date 2023-03-27
 */
public interface IWarehouseService {
    /**
     * 查询仓库
     *
     * @param id 仓库主键
     * @return 仓库
     */
    public Warehouse selectWarehouseById(String id);

    /**
     * 查询仓库列表
     *
     * @param warehouse 仓库
     * @return 仓库集合
     */
    public List<Warehouse> selectWarehouseList(Warehouse warehouse);

    public List<Warehouse> selectWarehouseListByCompanyId(String companyId);

    /**
     * 新增仓库
     *
     * @param warehouse 仓库
     * @return 结果
     */
    public int insertWarehouse(Warehouse warehouse);

    /**
     * 修改仓库
     *
     * @param warehouse 仓库
     * @return 结果
     */
    public int updateWarehouse(Warehouse warehouse);

    /**
     * 批量删除仓库
     *
     * @param ids 需要删除的仓库主键集合
     * @return 结果
     */
    public int deleteWarehouseByIds(String[] ids);

    /**
     * 删除仓库信息
     *
     * @param id 仓库主键
     * @return 结果
     */
    public int deleteWarehouseById(String id);

    List<WarehouseTreeSelect> buildWarehouseTreeSelect(Warehouse warehouse);

    List<Warehouse> getWarehouseByCompanyId(String companyId);

    List<Warehouse> selectByWarehouseIds(List<String> warehouseIds);
}
