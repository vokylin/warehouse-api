package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.StorageUnitType;

import java.util.List;

/**
 * 存储单元类型Mapper接口
 *
 * @author Rem
 * @date 2023-03-30
 */
public interface StorageUnitTypeMapper {
    /**
     * 查询存储单元类型
     *
     * @param id 存储单元类型主键
     * @return 存储单元类型
     */
    public StorageUnitType selectStorageUnitTypeById(String id);

    /**
     * 查询存储单元类型列表
     *
     * @param storageUnitType 存储单元类型
     * @return 存储单元类型集合
     */
    public List<StorageUnitType> selectStorageUnitTypeList(StorageUnitType storageUnitType);

    /**
     * 新增存储单元类型
     *
     * @param storageUnitType 存储单元类型
     * @return 结果
     */
    public int insertStorageUnitType(StorageUnitType storageUnitType);

    /**
     * 修改存储单元类型
     *
     * @param storageUnitType 存储单元类型
     * @return 结果
     */
    public int updateStorageUnitType(StorageUnitType storageUnitType);

    /**
     * 删除存储单元类型
     *
     * @param id 存储单元类型主键
     * @return 结果
     */
    public int deleteStorageUnitTypeById(String id);

    /**
     * 批量删除存储单元类型
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStorageUnitTypeByIds(String[] ids);

    List<StorageUnitType> selectChildrenStorageUnitTypeById(String id);

    void updateStorageUnitTypeChildren(List<StorageUnitType> children);

    boolean hasChildren(String id);

    StorageUnitType checkStorageUnitTypeNameUnique(StorageUnitType storageUnitType);

    StorageUnitType checkStorageUnitTypeCodeUnique(StorageUnitType storageUnitType);

    StorageUnitType findStorageUnitTypeByCode(String code);
}
