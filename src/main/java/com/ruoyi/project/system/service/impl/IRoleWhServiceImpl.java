package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.project.system.domain.RoleWh;
import com.ruoyi.project.system.mapper.RoleWhMapper;
import com.ruoyi.project.system.mapper.SysRoleMapper;
import com.ruoyi.project.system.service.IRoleWhService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IRoleWhServiceImpl implements IRoleWhService {

    @Resource
    private RoleWhMapper roleWhMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<String> selectListByRoleId(Long roleId) {
        return roleWhMapper.selectListByRoleId(roleId);
    }

    @Override
    public List<String> selectLitByUser() {
        Long userId = SecurityUtils.getUserId();
        List<Long> roleIds = sysRoleMapper.selectRoleListByUserId(userId);
        String companyId = ServletUtils.getCompanyId();
        List<RoleWh> roleWhs = roleWhMapper.selectRoleWhByRoleIds(roleIds);
        return roleWhs.stream().filter(roleWh -> roleWh.getCompanyId().equals(companyId)).map(RoleWh::getWhId).collect(Collectors.toList());
    }

}
