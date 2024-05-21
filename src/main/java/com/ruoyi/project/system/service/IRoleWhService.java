package com.ruoyi.project.system.service;

import java.util.List;

/**
 * 仓库角色关联表
 *
 * @author Rem
 * @date 2023/03/28
 */
public interface IRoleWhService {

    /**
     * 根据角色id查询仓库id列表
     *
     * @param roleId 角色id
     * @return {@link List}<{@link String}>
     */
    List<String> selectListByRoleId(Long roleId);


    List<String> selectLitByUser();

}
