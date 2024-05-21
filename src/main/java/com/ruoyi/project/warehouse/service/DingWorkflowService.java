package com.ruoyi.project.warehouse.service;

import com.ruoyi.project.warehouse.domain.DingWorkflow;
import org.springframework.stereotype.Service;

@Service
public interface DingWorkflowService {

    int insertSelective(DingWorkflow record);

    DingWorkflow selectById(Long id);

    DingWorkflow selectDingWorkflow(DingWorkflow dingWorkflow);

    DingWorkflow selectByInstanceId(String processInstanceId);
}
