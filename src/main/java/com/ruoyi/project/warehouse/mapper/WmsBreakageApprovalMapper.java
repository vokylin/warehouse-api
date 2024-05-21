package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsBreakageApproval;

public interface WmsBreakageApprovalMapper {
    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(WmsBreakageApproval record);

    WmsBreakageApproval selectByBreakageDocId(String breakageDocId);
}
