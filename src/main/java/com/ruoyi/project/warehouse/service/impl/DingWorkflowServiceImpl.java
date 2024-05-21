package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.project.warehouse.domain.DingWorkflow;
import com.ruoyi.project.warehouse.mapper.DingWorkflowMapper;
import com.ruoyi.project.warehouse.service.DingWorkflowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DingWorkflowServiceImpl implements DingWorkflowService {

    @Resource
    private DingWorkflowMapper dingWorkflowMapper;

    @Override
    public int insertSelective(DingWorkflow record) {
        return dingWorkflowMapper.insertSelective(record);
    }

    @Override
    public DingWorkflow selectById(Long id) {
        return dingWorkflowMapper.selectById(id);
    }

    @Override
    public DingWorkflow selectDingWorkflow(DingWorkflow dingWorkflow) {
        return dingWorkflowMapper.selectDingWorkflow(dingWorkflow);
    }

    @Override
    public DingWorkflow selectByInstanceId(String processInstanceId) {
        DingWorkflow dingWorkflow = new DingWorkflow();
        dingWorkflow.setDingdingId(processInstanceId);
        return dingWorkflowMapper.selectDingWorkflow(dingWorkflow);
    }


}
