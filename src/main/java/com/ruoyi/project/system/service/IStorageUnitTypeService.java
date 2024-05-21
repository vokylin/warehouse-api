package com.ruoyi.project.system.service;

import com.ruoyi.framework.web.domain.BaseTreeSelect;
import com.ruoyi.project.system.domain.StorageUnitType;

import java.util.List;

/**
 * 存储单元类型Service接口
 *
 * @author Rem
 * @date 2023-03-30
 */
public interface IStorageUnitTypeService {
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
     * 批量删除存储单元类型
     *
     * @param ids 需要删除的存储单元类型主键集合
     * @return 结果
     */
    public int deleteStorageUnitTypeByIds(String[] ids);

    /**
     * 删除存储单元类型信息
     *
     * @param id 存储单元类型主键
     * @return 结果
     */
    public int deleteStorageUnitTypeById(String id);

    /**
     * 校验存储单元类型名称是否唯一
     *
     * @param storageUnitType 存储单元类型
     * @return 结果
     */
    public boolean checkStorageUnitTypeNameUnique(StorageUnitType storageUnitType);

    boolean hasChildByParentId(String storageUnitTypeId);

    /**
     * 校验存储单元类型编码是否唯一
     *
     * @param storageUnitType 存储单元类型
     * @return boolean
     */
    boolean checkStorageUnitTypeCodeUnique(StorageUnitType storageUnitType);

    List<BaseTreeSelect> buildStorageUnitTypeTreeSelect(List<StorageUnitType> storageUnitTypeList);

    /**
     * 根据代码查询存储单元类型
     *
     * @param code 代码
     * @return {@link StorageUnitType}
     */
    StorageUnitType findStorageUnitTypeByCode(String code);
}
