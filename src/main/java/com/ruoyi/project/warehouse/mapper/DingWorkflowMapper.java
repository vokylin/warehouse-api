package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.DingWorkflow;

public interface DingWorkflowMapper {
    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(DingWorkflow record);

    DingWorkflow selectById(Long id);

    DingWorkflow selectDingWorkflow(DingWorkflow dingWorkflow);
}
