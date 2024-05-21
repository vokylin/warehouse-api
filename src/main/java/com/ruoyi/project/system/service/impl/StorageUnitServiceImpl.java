package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.enums.StorageUnitTypeEnums;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.domain.StorageUnitTreeSelect;
import com.ruoyi.project.system.domain.StorageUnit;
import com.ruoyi.project.system.domain.StorageUnitType;
import com.ruoyi.project.system.domain.Warehouse;
import com.ruoyi.project.system.mapper.StorageUnitMapper;
import com.ruoyi.project.system.mapper.StorageUnitTypeMapper;
import com.ruoyi.project.system.mapper.WarehouseMapper;
import com.ruoyi.project.system.service.IStorageUnitService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 存储单元Service业务层处理
 *
 * @author Rem
 * @date 2023-03-29
 */
@Service
public class StorageUnitServiceImpl implements IStorageUnitService {

    @Autowired
    private StorageUnitMapper storageUnitMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private StorageUnitTypeMapper storageUnitTypeMapper;

    /**
     * 查询存储单元
     *
     * @param id 存储单元主键
     * @return 存储单元
     */
    @Override
    public StorageUnit selectStorageUnitById(String id) {
        return storageUnitMapper.selectStorageUnitById(id);
    }

    /**
     * 查询存储单元列表
     *
     * @param storageUnit 存储单元
     * @return 存储单元
     */
    @Override
    public List<StorageUnit> selectStorageUnitList(StorageUnit storageUnit) {
        return storageUnitMapper.selectStorageUnitList(storageUnit);
    }


    /**
     * 新增存储单元
     *
     * @param storageUnit 存储单元
     * @return 结果
     */
    @Override
    public int insertStorageUnit(StorageUnit storageUnit) {
        // TODO: 新增时是否校验code name 唯一

        // 查询存储单元类型
        StorageUnitType storageUnitType = storageUnitTypeMapper.findStorageUnitTypeByCode(storageUnit.getStorageUnitTypeCode());
        storageUnit.setId(IdUtils.fastSimpleUUID());
        storageUnit.setStorageUnitTypeCode(storageUnitType.getCode());
        storageUnit.setStorageUnitTypeName(storageUnitType.getName());
        storageUnit.setCreateTime(DateUtils.getNowDate());
        // 判断新增的是否是根节点
        if (storageUnit.getParentId().equals(Constants.ROOT_NODE)) {
            storageUnit.setParentId(Constants.ROOT_NODE);
            storageUnit.setParentIds(Constants.ROOT_NODE);
            return storageUnitMapper.insertStorageUnit(storageUnit);
        }
        // 非根节点 查询父节点信息
        StorageUnit parentStorageUnit = storageUnitMapper.selectStorageUnitById(storageUnit.getParentId());
        if (Objects.nonNull(parentStorageUnit)) {
            storageUnit.setParentIds(parentStorageUnit.getParentIds() + "," + parentStorageUnit.getId());
            storageUnit.setWarehouseId(parentStorageUnit.getWarehouseId());
        }
        return storageUnitMapper.insertStorageUnit(storageUnit);
    }


    /**
     * 修改存储单元
     *
     * @param storageUnit 存储单元
     * @return 结果
     */
    @Override
    public int updateStorageUnit(StorageUnit storageUnit) {
        // 查询当前选中的存储单元类型
        StorageUnitType storageUnitType = storageUnitTypeMapper.findStorageUnitTypeByCode(storageUnit.getStorageUnitTypeCode());
        storageUnit.setUpdateTime(DateUtils.getNowDate());
        storageUnit.setStorageUnitTypeCode(storageUnitType.getCode());
        storageUnit.setStorageUnitTypeName(storageUnitType.getName());
        // 非根节点 查询修改后父节点信息
        StorageUnit newParentStorageUnit = storageUnitMapper.selectStorageUnitById(storageUnit.getParentId());
        StorageUnit oldParentStorageUnit = storageUnitMapper.selectStorageUnitById(storageUnit.getId());
        if (Objects.nonNull(newParentStorageUnit) && Objects.nonNull(oldParentStorageUnit)) {
            String newParentIds = newParentStorageUnit.getParentIds() + "," + newParentStorageUnit.getId();
            String oldParentIds = oldParentStorageUnit.getParentIds();
            storageUnit.setParentIds(newParentIds);
            updateStorageUnitChildren(storageUnit.getId(), newParentStorageUnit.getWarehouseId(), newParentIds, oldParentIds);
        }
        return storageUnitMapper.updateStorageUnit(storageUnit);
    }

    /**
     * 更新子元素关系
     *
     * @param id           id
     * @param newParentIds 新父id
     * @param oldParentIds 老父id
     */
    private void updateStorageUnitChildren(String id, String warehouseId, String newParentIds, String oldParentIds) {
        List<StorageUnit> children = storageUnitMapper.selectChildrenStorageUnitById(id);
        for (StorageUnit child : children) {
            child.setParentIds(child.getParentIds().replace(oldParentIds, newParentIds));
            child.setWarehouseId(warehouseId);
        }
        if (children.size() > 0) {
            storageUnitMapper.updateStorageUnitChildren(children);
        }

    }

    /**
     * 批量删除存储单元
     *
     * @param ids 需要删除的存储单元主键
     * @return 结果
     */
    @Override
    public int deleteStorageUnitByIds(String[] ids) {
        return storageUnitMapper.deleteStorageUnitByIds(ids);
    }

    /**
     * 删除存储单元信息
     *
     * @param id 存储单元主键
     * @return 结果
     */
    @Override
    public int deleteStorageUnitById(String id) {
        return storageUnitMapper.deleteStorageUnitById(id);
    }

    /**
     * 是否存在子节点
     *
     * @param id 公司ID
     * @return 结果
     */
    @Override
    public boolean hasChild(String id) {
        int result = storageUnitMapper.hasChild(id);
        return result > 0;
    }

    /**
     * 查询所有存储单元
     *
     * @param storageUnit 存储单元
     * @return {@link List}<{@link StorageUnit}>
     */
    @Override
    public List<StorageUnit> selectAll(StorageUnit storageUnit) {
        return storageUnitMapper.selectAll(storageUnit);
    }

    /**
     * 检查名称或CODE是否唯一
     *
     * @param storageUnit 存储单元
     * @return boolean
     */
    public boolean checkNameOrCodeUnique(StorageUnit storageUnit) {
        StorageUnit storageUnitInfo = storageUnitMapper.checkNameOrCodeUnique(storageUnit);
        if (storageUnitInfo != null && !storageUnitInfo.getId().equals(storageUnit.getId())) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 查询存储单元树
     *
     * @param storageUnit 存储单元
     * @return {@link List}<{@link StorageUnitTreeSelect}>
     */

    public List<StorageUnitTreeSelect> selectStorageUnitTreeBak(StorageUnit storageUnit) {

        String storageUnitId = ServletUtils.getCompanyId();
        List<Warehouse> warehouses = warehouseMapper.selectWarehouseListByCompanyId(storageUnitId);
        if (CollectionUtils.isEmpty(warehouses)) {
            return null;
        }
        // 将仓库转为存储单元
        List<StorageUnit> warehouseList = warehouseToStorageUnit(warehouses);

        List<String> warehouseIds = warehouses.stream().map(Warehouse::getId).collect(Collectors.toList());

        // 根据仓库ID 查询根存储单元
        List<StorageUnit> rootStorageUnits = storageUnitMapper.selectStorageUnitListByWarehouseIds(warehouseIds);
        if (CollectionUtils.isEmpty(rootStorageUnits)) {
            return warehouses.stream().map(StorageUnitTreeSelect::new).collect(Collectors.toList());
        }
        List<String> storageUnitIds = rootStorageUnits.stream().map(StorageUnit::getId).collect(Collectors.toList());

        // 查询所有子存储单元
        List<StorageUnit> childrenStorageUnits = storageUnitMapper.selectStorageUnitListByParentIds(storageUnitIds);

        // 如果没有子存储单元
        if (CollectionUtils.isEmpty(childrenStorageUnits)) {
            // 合并仓库和根存储单元
            List<StorageUnit> storageUnitTree = buildStorageUnitTreeOfWarehouse(warehouseList, rootStorageUnits);
            return storageUnitTree.stream().map(StorageUnitTreeSelect::new).collect(Collectors.toList());
        }

        // 如果有子存储单元 则构建树
        List<StorageUnit> childrenStorageUnitsTree = buildStorageUnitTree(childrenStorageUnits);

        // 将根存储单元和子存储单元合并
        List<StorageUnit> storageUnitTree = buildStorageUnitTree(rootStorageUnits, childrenStorageUnitsTree);

        // 将仓库和存储单元合并
        List<StorageUnit> storageUnits = buildStorageUnitTreeOfWarehouse(warehouseList, storageUnitTree);

        return storageUnits.stream().map(StorageUnitTreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 查询存储单元树
     *
     * @param storageUnit 存储单元
     * @return {@link List}<{@link StorageUnitTreeSelect}>
     */
    @Override
    public List<StorageUnitTreeSelect> selectStorageUnitTree(StorageUnit storageUnit) {

        if (StringUtils.isBlank(storageUnit.getWarehouseId())) {
            String storageUnitId = ServletUtils.getCompanyId();
            List<Warehouse> warehouses = warehouseMapper.selectWarehouseListByCompanyId(storageUnitId);
            if (CollectionUtils.isEmpty(warehouses)) {
                return null;
            }
            // 将仓库转为存储单元
            List<StorageUnit> warehouseList = warehouseToStorageUnit(warehouses);

            // 根据仓库ID 查询存储单元
            List<String> warehouseIds = warehouses.stream().map(Warehouse::getId).collect(Collectors.toList());

            List<StorageUnit> storageUnits;

            // 判断是否过滤最小存储单元
            if (null != storageUnit.getIsSmallestStorageUnit() && storageUnit.getIsSmallestStorageUnit().equals(Constants.YES)) {
                storageUnits = storageUnitMapper.selectAllStorageUnitByWarehouseIds(warehouseIds);
            } else {
                storageUnits = storageUnitMapper.selectStorageUnitListByWarehouseIds(warehouseIds);
            }

            if (CollectionUtils.isEmpty(storageUnits)) {
                return warehouses.stream().map(StorageUnitTreeSelect::new).collect(Collectors.toList());
            }

            // 构建树
            List<StorageUnit> storageUnitTree = buildStorageUnitTree(storageUnits);

            // 将仓库和存储单元合并
            List<StorageUnit> storageUnitsTree = buildStorageUnitTreeOfWarehouse(warehouseList, storageUnitTree);
            return storageUnitsTree.stream().map(StorageUnitTreeSelect::new).collect(Collectors.toList());
        } else {


            List<StorageUnit> storageUnits = storageUnitMapper.selectStorageUnitList(storageUnit);
            if (CollectionUtils.isEmpty(storageUnits)) {
                return null;
            }

            // 构建树
            List<StorageUnit> storageUnitTree = buildStorageUnitTree(storageUnits);
            return storageUnitTree.stream().map(StorageUnitTreeSelect::new).collect(Collectors.toList());
        }
    }


    /**
     * 仓库转存储单元
     *
     * @param warehouses 仓库
     * @return {@link List}<{@link StorageUnit}>
     */
    private List<StorageUnit> warehouseToStorageUnit(List<Warehouse> warehouses) {

        ArrayList<StorageUnit> storageUnitList = new ArrayList<>();
        warehouses.forEach(warehouse -> {
            StorageUnit storageUnit = new StorageUnit();
            storageUnit.setId(warehouse.getId());
            storageUnit.setName(warehouse.getName());
            storageUnit.setId(warehouse.getId());
            storageUnit.setStorageUnitType(StorageUnitTypeEnums.WAREHOUSE.getCode());
            storageUnit.setIsSmallestStorageUnit(Constants.NO);
            storageUnitList.add(storageUnit);
        });
        return storageUnitList;
    }


    /**
     * 构建存储单元树仓库
     *
     * @param warehouseStorageUintList 仓库
     * @param storageUnitTree          存储单元树
     * @return {@link List}<{@link Warehouse}>
     */
    private List<StorageUnit> buildStorageUnitTreeOfWarehouse(List<StorageUnit> warehouseStorageUintList, List<StorageUnit> storageUnitTree) {
        warehouseStorageUintList.forEach(warehouseStorageUint -> storageUnitTree.forEach(storageUnit -> {
            if (warehouseStorageUint.getId().equals(storageUnit.getWarehouseId())) {
                warehouseStorageUint.getChildren().add(storageUnit);
            }
        }));
        return warehouseStorageUintList;
    }


    /**
     * 构建存储单元树 将根存储单元和子存储单元合并
     *
     * @param rootStorageUnits         根存储单元
     * @param childrenStorageUnitsTree 孩子树存储单元
     * @return {@link List}<{@link StorageUnit}>
     */
    private List<StorageUnit> buildStorageUnitTree(List<StorageUnit> rootStorageUnits, List<StorageUnit> childrenStorageUnitsTree) {
        rootStorageUnits.forEach(rootStorageUnit -> {
            childrenStorageUnitsTree.forEach(childrenStorageUnit -> {
                if (rootStorageUnit.getId().equals(childrenStorageUnit.getParentId())) {
                    rootStorageUnit.getChildren().add(childrenStorageUnit);
                }
            });
        });
        return rootStorageUnits;
    }

    /**
     * 构建存储单元树
     *
     * @param storageUnitList 存储单元列表
     * @return {@link List}<{@link StorageUnit}>
     */
    private List<StorageUnit> buildStorageUnitTree(List<StorageUnit> storageUnitList) {
        List<StorageUnit> returnList = new ArrayList<>();
        List<String> tempList = storageUnitList.stream().map(StorageUnit::getId).collect(Collectors.toList());
        for (StorageUnit storageUnit : storageUnitList) {
            if (!tempList.contains(storageUnit.getParentId())) {
                recursionFn(storageUnitList, storageUnit);
                returnList.add(storageUnit);
            }
        }
        if (returnList.isEmpty()) {
            returnList = storageUnitList;
        }
        return returnList;
    }

    /**
     * 递归
     *
     * @param storageUnitList 存储单元列表
     * @param storageUnit     存储单元
     */
    private void recursionFn(List<StorageUnit> storageUnitList, StorageUnit storageUnit) {
        List<StorageUnit> childList = getChildList(storageUnitList, storageUnit);
        storageUnit.setChildren(childList);
        for (StorageUnit child : childList) {
            if (hasChild(storageUnitList, child)) {
                recursionFn(storageUnitList, child);
            }
        }
    }

    /**
     * 是否存在子节点
     *
     * @param storageUnitList 存储单元列表
     * @param child           子节点
     * @return boolean
     */
    private boolean hasChild(List<StorageUnit> storageUnitList, StorageUnit child) {
        return getChildList(storageUnitList, child).size() > 0;
    }

    /**
     * 获取子节点列表
     *
     * @param storageUnitList 存储单元列表
     * @param storageUnit     存储单元
     * @return {@link List}<{@link StorageUnit}>
     */
    private List<StorageUnit> getChildList(List<StorageUnit> storageUnitList, StorageUnit storageUnit) {
        List<StorageUnit> childList = new ArrayList<>();
        for (StorageUnit child : storageUnitList) {
            if (child.getParentId().equals(storageUnit.getId())) {
                childList.add(child);
            }
        }
        return childList;
    }

    /**
     * 新增存储单元
     *
     * @param storageUnit 存储单元
     * @return 结果
     */
    public AjaxResult insertStorageUnitBak(StorageUnit storageUnit) {
        // TODO: 新增时是否校验code name 唯一

        // 查询存储单元类型
        StorageUnitType storageUnitType = storageUnitTypeMapper.findStorageUnitTypeByCode(storageUnit.getStorageUnitTypeCode());
        storageUnit.setStorageUnitTypeCode(storageUnitType.getCode());
        storageUnit.setStorageUnitTypeName(storageUnitType.getName());
        storageUnit.setId(IdUtils.fastSimpleUUID());
        storageUnit.setCreateTime(DateUtils.getNowDate());
        // 判断新增的是否是根节点
        if (StringUtils.isBlank(storageUnit.getParentId())) {
            storageUnit.setParentId(Constants.ROOT_NODE);
            storageUnit.setParentIds(Constants.ROOT_NODE);

            int i = storageUnitMapper.insertStorageUnit(storageUnit);
            if (i > 0) {
                return AjaxResult.success();
            }
            return AjaxResult.error("新增失败");
        }

        // 非根节点 查询父节点信息
        StorageUnit parentStorageUnit = storageUnitMapper.selectStorageUnitById(storageUnit.getParentId());
        if (Objects.nonNull(parentStorageUnit)) {
            storageUnit.setParentIds(parentStorageUnit.getParentIds() + "," + parentStorageUnit.getId());
            // 判断父节点 存储单元类型 节点类型是否是 新增节点类型的父节点类型
            if (StringUtils.isNotBlank(parentStorageUnit.getStorageUnitTypeCode())) {
                StorageUnitType parentStorageUnitType = storageUnitTypeMapper.findStorageUnitTypeByCode(parentStorageUnit.getStorageUnitTypeCode());
                if (Objects.nonNull(parentStorageUnitType)) {
                    if (StringUtils.isNotBlank(parentStorageUnitType.getId())) {
                        if (!parentStorageUnitType.getId().equals(storageUnitType.getParentId())) {
                            return AjaxResult.error("新增失败,父节点类型不是新增节点类型的父节点类型");
                        }
                    }
                }
            }
        }
        int i = storageUnitMapper.insertStorageUnit(storageUnit);
        if (i > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error("新增失败");
    }

    /**
     * 修改存储单元
     *
     * @param storageUnit 存储单元
     * @return 结果
     */
    public AjaxResult updateStorageUnitBak(StorageUnit storageUnit) {
        storageUnit.setUpdateTime(DateUtils.getNowDate());

        // 查询当前选中的存储单元类型
        StorageUnitType storageUnitType = storageUnitTypeMapper.findStorageUnitTypeByCode(storageUnit.getStorageUnitTypeCode());

        if (storageUnit.getParentId().equals(Constants.ROOT_NODE)) {
            // 查询子节点
            StorageUnit storageUnits = storageUnitMapper.selectOneChildrenStorageUnitById(storageUnit.getId());

            // 判断当前选中的存储单元类型是否是子节点的父节点类型
            if (Objects.nonNull(storageUnits)) {
                StorageUnitType childStorageUnitType = storageUnitTypeMapper.findStorageUnitTypeByCode(storageUnits.getStorageUnitTypeCode());
                if (Objects.nonNull(childStorageUnitType)) {
                    if (StringUtils.isNotBlank(childStorageUnitType.getParentId())) {
                        if (!childStorageUnitType.getParentId().equals(storageUnitType.getId())) {
                            return AjaxResult.error("修改失败,当前选中的存储单元类型不是子节点的父节点类型");
                        }
                    }
                }
            }
            storageUnit.setStorageUnitTypeCode(storageUnitType.getCode());
            storageUnit.setStorageUnitTypeName(storageUnitType.getName());
            storageUnit.setParentIds(Constants.ROOT_NODE);
            int i = storageUnitMapper.updateStorageUnit(storageUnit);
            if (i > 0) {
                return AjaxResult.success();
            }
            return AjaxResult.error("修改失败");
        }

        // 非根节点 查询修改后父节点信息
        StorageUnit newParentStorageUnit = storageUnitMapper.selectStorageUnitById(storageUnit.getParentId());

        if (Objects.nonNull(newParentStorageUnit)) {
            // 判断父节点 存储单元类型 节点类型是否是 新增节点类型的父节点类型
            if (StringUtils.isNotBlank(newParentStorageUnit.getStorageUnitTypeCode())) {
                StorageUnitType parentStorageUnitType = storageUnitTypeMapper.findStorageUnitTypeByCode(newParentStorageUnit.getStorageUnitTypeCode());
                if (Objects.nonNull(parentStorageUnitType)) {
                    if (StringUtils.isNotBlank(parentStorageUnitType.getId())) {
                        if (!parentStorageUnitType.getId().equals(storageUnitType.getParentId())) {
                            return AjaxResult.error("修改失败,父节点类型不是新增节点类型的父节点类型");
                        }
                    }
                }
            }
        }

        // 查询修改前父节点信息
        StorageUnit oldParentStorageUnit = storageUnitMapper.selectStorageUnitById(storageUnit.getId());
        if (Objects.nonNull(newParentStorageUnit) && Objects.nonNull(oldParentStorageUnit)) {
            String newParentIds = newParentStorageUnit.getParentIds() + "," + newParentStorageUnit.getId();
            String oldParentIds = oldParentStorageUnit.getParentIds();
            storageUnit.setParentIds(newParentIds);
            updateStorageUnitChildren(storageUnit.getId(), newParentStorageUnit.getWarehouseId(), newParentIds, oldParentIds);
        }

        int i = storageUnitMapper.updateStorageUnit(storageUnit);
        if (i > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error("修改失败");
    }

}
