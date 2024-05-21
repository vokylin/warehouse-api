package com.ruoyi.project.system.service;

import com.ruoyi.framework.web.domain.StorageUnitTreeSelect;
import com.ruoyi.project.system.domain.StorageUnit;

import java.util.List;

/**
 * 存储单元Service接口
 *
 * @author Rem
 * @date 2023-03-29
 */
public interface IStorageUnitService {
    /**
     * 查询存储单元
     *
     * @param id 存储单元主键
     * @return 存储单元
     */
    public StorageUnit selectStorageUnitById(String id);

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
     * 批量删除存储单元
     *
     * @param ids 需要删除的存储单元主键集合
     * @return 结果
     */
    public int deleteStorageUnitByIds(String[] ids);

    /**
     * 删除存储单元信息
     *
     * @param id 存储单元主键
     * @return 结果
     */
    public int deleteStorageUnitById(String id);

    /**
     * 查询存储单元树
     *
     * @param storageUnit 存储单元
     * @return {@link List}<{@link StorageUnitTreeSelect}>
     */
    List<StorageUnitTreeSelect> selectStorageUnitTree(StorageUnit storageUnit);

    boolean hasChild(String id);

    /**
     * 查询所有存储单元
     *
     * @param storageUnit 存储单元
     * @return {@link List}<{@link StorageUnit}>
     */
    List<StorageUnit> selectAll(StorageUnit storageUnit);
}
