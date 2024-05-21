package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.Warehouse;

import java.util.List;

/**
 * 仓库Mapper接口
 *
 * @author Rem
 * @date 2023-03-27
 */
public interface WarehouseMapper {
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
     * 删除仓库
     *
     * @param id 仓库主键
     * @return 结果
     */
    public int deleteWarehouseById(String id);

    /**
     * 批量删除仓库
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseByIds(String[] ids);

    List<Warehouse> selectWarehouseListByCompanyId(String companyId);

    List<Warehouse> selectWarehouseByIds(List<String> whIds);

    List<Warehouse> getWarehouseByCompanyId(String companyId);

    /**
     * 根据仓库id查询仓库
     *
     * @param warehouseIds 仓库id
     * @return {@link List}<{@link Warehouse}>
     */
    List<Warehouse> selectByWarehouseIds(List<String> warehouseIds);
}
