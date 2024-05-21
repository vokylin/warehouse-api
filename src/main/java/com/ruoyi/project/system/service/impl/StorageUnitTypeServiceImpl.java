package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.BaseTreeSelect;
import com.ruoyi.project.system.domain.StorageUnitType;
import com.ruoyi.project.system.mapper.StorageUnitTypeMapper;
import com.ruoyi.project.system.service.IStorageUnitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 存储单元类型Service业务层处理
 *
 * @author Rem
 * @date 2023-03-30
 */
@Service
public class StorageUnitTypeServiceImpl implements IStorageUnitTypeService {
    @Autowired
    private StorageUnitTypeMapper storageUnitTypeMapper;

    /**
     * 查询存储单元类型
     *
     * @param id 存储单元类型主键
     * @return 存储单元类型
     */
    @Override
    public StorageUnitType selectStorageUnitTypeById(String id) {
        return storageUnitTypeMapper.selectStorageUnitTypeById(id);
    }

    /**
     * 查询存储单元类型列表
     *
     * @param storageUnitType 存储单元类型
     * @return 存储单元类型
     */
    @Override
    public List<StorageUnitType> selectStorageUnitTypeList(StorageUnitType storageUnitType) {
        return storageUnitTypeMapper.selectStorageUnitTypeList(storageUnitType);
    }

    /**
     * 新增存储单元类型
     *
     * @param storageUnitType 存储单元类型
     * @return 结果
     */
    @Override
    public int insertStorageUnitType(StorageUnitType storageUnitType) {
//        StorageUnitType parentStorageUnitType = storageUnitTypeMapper.selectStorageUnitTypeById(storageUnitType.getParentId());
//        if (parentStorageUnitType != null) {
//            storageUnitType.setParentId(parentStorageUnitType.getId());
//            storageUnitType.setParentIds(parentStorageUnitType.getParentIds() + "," + parentStorageUnitType.getId());
//        } else {
//            storageUnitType.setParentId(Constants.ROOT_NODE);
//            storageUnitType.setParentIds(Constants.ROOT_NODE);
//        }
        storageUnitType.setId(IdUtils.fastSimpleUUID());
        storageUnitType.setCreateTime(DateUtils.getNowDate());
        return storageUnitTypeMapper.insertStorageUnitType(storageUnitType);
    }

    /**
     * 修改存储单元类型
     *
     * @param storageUnitType 存储单元类型
     * @return 结果
     */
    @Override
    public int updateStorageUnitType(StorageUnitType storageUnitType) {

//        // 查询新的父节点信息
//        StorageUnitType newParentStorageUnitType = storageUnitTypeMapper.selectStorageUnitTypeById(storageUnitType.getParentId());
//        // 获取旧的父节点信息
//        StorageUnitType oldStorageUnitType = storageUnitTypeMapper.selectStorageUnitTypeById(storageUnitType.getId());
//        if (Objects.nonNull(newParentStorageUnitType) && Objects.nonNull(oldStorageUnitType)) {
//            String newParentIds = newParentStorageUnitType.getParentIds() + "," + newParentStorageUnitType.getId();
//            String oldParentIds = oldStorageUnitType.getParentIds();
//            storageUnitType.setParentIds(newParentIds);
//            updateStorageUnitTypeChildren(storageUnitType.getId(), newParentIds, oldParentIds);
//
//        }
        storageUnitType.setUpdateTime(DateUtils.getNowDate());
        return storageUnitTypeMapper.updateStorageUnitType(storageUnitType);
    }

    /**
     * 更新存储单元类型的子节点
     *
     * @param id           id
     * @param newParentIds 新父id
     * @param oldParentIds 老父id
     */
    private void updateStorageUnitTypeChildren(String id, String newParentIds, String oldParentIds) {
        List<StorageUnitType> children = storageUnitTypeMapper.selectChildrenStorageUnitTypeById(id);
        for (StorageUnitType child : children) {
            child.setParentIds(child.getParentIds().replace(oldParentIds, newParentIds));
        }
        if (children.size() > 0) {
            storageUnitTypeMapper.updateStorageUnitTypeChildren(children);
        }
    }

    /**
     * 批量删除存储单元类型
     *
     * @param ids 需要删除的存储单元类型主键
     * @return 结果
     */
    @Override
    public int deleteStorageUnitTypeByIds(String[] ids) {
        return storageUnitTypeMapper.deleteStorageUnitTypeByIds(ids);
    }

    /**
     * 删除存储单元类型信息
     *
     * @param id 存储单元类型主键
     * @return 结果
     */
    @Override
    public int deleteStorageUnitTypeById(String id) {
        return storageUnitTypeMapper.deleteStorageUnitTypeById(id);
    }

    /**
     * 校验存储单元类型名称是否唯一
     *
     * @param storageUnitType 存储单元类型
     * @return 结果
     */
    @Override
    public boolean checkStorageUnitTypeNameUnique(StorageUnitType storageUnitType) {
        StorageUnitType storageUnitTypeInfo = storageUnitTypeMapper.checkStorageUnitTypeNameUnique(storageUnitType);
        if (storageUnitTypeInfo != null && !storageUnitTypeInfo.getId().equals(storageUnitType.getId())) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验存储单元类型编码是否唯一
     *
     * @param storageUnitType 存储单元类型
     * @return 结果
     */
    @Override
    public boolean checkStorageUnitTypeCodeUnique(StorageUnitType storageUnitType) {
        StorageUnitType storageUnitTypeInfo = storageUnitTypeMapper.checkStorageUnitTypeCodeUnique(storageUnitType);
        if (storageUnitTypeInfo != null && !storageUnitTypeInfo.getId().equals(storageUnitType.getId())) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 是否存在子节点
     *
     * @param parentId 父id
     * @return boolean
     */
    @Override
    public boolean hasChildByParentId(String parentId) {
        return storageUnitTypeMapper.hasChildren(parentId);
    }


    /**
     * 构建存储单元类型树
     *
     * @param storageUnitTypeList 存储单元类型列表
     * @return {@link List}<{@link StorageUnitType}>
     */
    private List<StorageUnitType> buildStorageUnitTypeTree(List<StorageUnitType> storageUnitTypeList) {
        List<StorageUnitType> returnList = new ArrayList<>();
        List<String> tempList = storageUnitTypeList.stream().map(StorageUnitType::getId).collect(Collectors.toList());
        for (StorageUnitType storageUnitType : storageUnitTypeList) {
            if (!tempList.contains(storageUnitType.getParentId())) {
                recursionFn(storageUnitTypeList, storageUnitType);
                returnList.add(storageUnitType);
            }
        }
        if (returnList.isEmpty()) {
            returnList = storageUnitTypeList;
        }
        return returnList;
    }

    private void recursionFn(List<StorageUnitType> storageUnitTypeList, StorageUnitType storageUnitType) {
        List<StorageUnitType> childList = getChildList(storageUnitTypeList, storageUnitType);
        storageUnitType.setChildren(childList);
        for (StorageUnitType child : childList) {
            if (hasChild(storageUnitTypeList, child)) {
                recursionFn(storageUnitTypeList, child);
            }
        }
    }

    /**
     * 是否存在子节点
     *
     * @param storageUnitTypeList 存储单元类型列表
     * @param child               子节点
     * @return boolean
     */
    private boolean hasChild(List<StorageUnitType> storageUnitTypeList, StorageUnitType child) {
        return getChildList(storageUnitTypeList, child).size() > 0;
    }


    /**
     * 获取子节点列表
     *
     * @param storageUnitTypeList 存储单元类型列表
     * @param storageUnitType     存储单元类型
     * @return {@link List}<{@link StorageUnitType}>
     */
    private List<StorageUnitType> getChildList(List<StorageUnitType> storageUnitTypeList, StorageUnitType storageUnitType) {
        List<StorageUnitType> childList = new ArrayList<>();
        for (StorageUnitType next : storageUnitTypeList) {
            if (StringUtils.isNotNull(next.getParentId()) && next.getParentId().equals(storageUnitType.getId())) {
                childList.add(next);
            }
        }
        return childList;
    }

    /**
     * 构建存储单元类型树
     *
     * @param storageUnitTypeList 存储单元类型列表
     * @return {@link List}<{@link StorageUnitType}>
     */
    @Override
    public List<BaseTreeSelect> buildStorageUnitTypeTreeSelect(List<StorageUnitType> storageUnitTypeList) {
        List<StorageUnitType> storageUnitTypes = buildStorageUnitTypeTree(storageUnitTypeList);
        return storageUnitTypes.stream().map(BaseTreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据代码查询存储单元类型
     *
     * @param code 代码
     * @return {@link StorageUnitType}
     */
    @Override
    public StorageUnitType findStorageUnitTypeByCode(String code) {
        return storageUnitTypeMapper.findStorageUnitTypeByCode(code);
    }


}
