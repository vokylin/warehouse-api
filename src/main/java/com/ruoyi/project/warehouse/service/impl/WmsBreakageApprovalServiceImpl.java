package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.project.warehouse.domain.WmsBreakageApproval;
import com.ruoyi.project.warehouse.mapper.WmsBreakageApprovalMapper;
import com.ruoyi.project.warehouse.service.WmsBreakageApprovalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class WmsBreakageApprovalServiceImpl implements WmsBreakageApprovalService {

    @Resource
    private WmsBreakageApprovalMapper wmsBreakageApprovalMapper;

    @Override
    public int insertSelective(WmsBreakageApproval record) {
        record.setId(IdUtils.fastSimpleUUID());
        return wmsBreakageApprovalMapper.insertSelective(record);
    }

    @Override
    public WmsBreakageApproval selectByBreakageDocId(String breakageDocId) {
        return wmsBreakageApprovalMapper.selectByBreakageDocId(breakageDocId);
    }

}
