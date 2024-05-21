package com.ruoyi.project.common;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.entity.InventoryAuditDto;
import com.ruoyi.project.warehouse.service.IWmsInventoryPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * InventoryPlanController
 *
 * @author Rem
 */
@Slf4j
@RequestMapping("/api/inventoryPlan")
@RestController
public class InventoryPlanController {

    @Autowired
    private IWmsInventoryPlanService wmsInventoryPlanService;

    /**
     * 提交审核
     */
    @RequestMapping(value = "/submitAudit", method = RequestMethod.POST)
    public AjaxResult submitAudit(@RequestBody InventoryAuditDto inventoryAuditDto) {
        if (null == inventoryAuditDto) {
            log.error("盘点任务审核被调用参数为空");
        }
        log.info("盘点任务审核被调用 ========> inventoryAuditDto:{}", JSONObject.toJSONString(inventoryAuditDto));
        wmsInventoryPlanService.reviewComplete(inventoryAuditDto);
        return AjaxResult.success();
    }


}
