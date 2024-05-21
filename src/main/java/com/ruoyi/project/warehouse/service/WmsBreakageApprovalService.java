package com.ruoyi.project.warehouse.service;

import com.ruoyi.project.warehouse.domain.WmsBreakageApproval;

public interface WmsBreakageApprovalService {

    int insertSelective(WmsBreakageApproval record);

    WmsBreakageApproval selectByBreakageDocId(String breakageDocId);

}
