package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.RoleWh;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleWhMapper {

    List<String> selectListByRoleId(Long roleId);

    int batchWareHouseRole(List<RoleWh> list);

    void deleteByRoleIds(@Param("roleIds") Long[] roleIds);

    void deleteByRoleId(Long roleId);

    List<RoleWh> selectRoleWhByRoleIds(@Param("roleIds") List<Long> roleIds);

    List<String> selectLitByUserId(Long userId, String companyId);
}
