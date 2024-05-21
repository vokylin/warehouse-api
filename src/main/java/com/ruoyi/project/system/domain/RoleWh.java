package com.ruoyi.project.system.domain;

import lombok.Data;

/**
 * 角色仓库表
 */
@Data
public class RoleWh {
    /**
     * id
     */
    private String id;

    /**
     * 仓库ID
     */
    private String whId;

    /**
     * 角色ID
     */
    private Long roleId;

    private String companyId;

}
