package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.StorageUnit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 存储单元Mapper接口
 *
 * @author Rem
 * @date 2023-03-29
 */
public interface StorageUnitMapper {
    /**
     * 查询存储单元
     *
     * @param id 存储单元主键
     * @return 存储单元
     */
    public StorageUnit selectStorageUnitById(String id);

    public StorageUnit selectStorageUnitByParentId(String id);

    /**
     * 查询存储单元列表
     *
     * @param storageUnit 存储单元
     * @return 存储单元集合
     */
    public List<StorageUnit> selectStorageUnitList(StorageUnit storageUnit);

    /**
     * 新增存储单元
     *
     * @param storageUnit 存储单元
     * @return 结果
     */
    public int insertStorageUnit(StorageUnit storageUnit);

    /**
     * 修改存储单元
     *
     * @param storageUnit 存储单元
     * @return 结果
     */
    public int updateStorageUnit(StorageUnit storageUnit);

    /**
     * 删除存储单元
     *
     * @param id 存储单元主键
     * @return 结果
     */
    public int deleteStorageUnitById(String id);

    /**
     * 批量删除存储单元
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStorageUnitByIds(String[] ids);

    /**
     * 根据仓库id查询存储单元
     *
     * @param warehouseIds 仓库id
     * @return {@link List}<{@link StorageUnit}>
     */
    List<StorageUnit> selectStorageUnitListByWarehouseIds(List<String> warehouseIds);


    List<StorageUnit> selectAllStorageUnitByWarehouseIds(List<String> warehouseIds);

    /**
     * 根据父id查询存储单元
     *
     * @param storageUnitIds 存储单元id
     * @return {@link List}<{@link StorageUnit}>
     */
    List<StorageUnit> selectStorageUnitListByParentIds(List<String> storageUnitIds);

    int hasChild(String id);

    StorageUnit checkNameOrCodeUnique(StorageUnit storageUnit);

    List<StorageUnit> selectChildrenStorageUnitById(String id);

    StorageUnit selectOneChildrenStorageUnitById(String id);

    void updateStorageUnitChildren(@Param("children") List<StorageUnit> children);

    /**
     * 查询所有存储单元
     *
     * @param storageUnit 存储单元
     * @return {@link List}<{@link StorageUnit}>
     */
    List<StorageUnit> selectAll(StorageUnit storageUnit);
}
