package com.ruoyi.project.common;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.component.DingTalkManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/breakageDocAudit")
public class BreakageDocAuditController {

    @Autowired
    private DingTalkManager dingTalkManager;

    /**
     * 报损单审核结果推送
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public AjaxResult receiveNotice(@RequestBody String processInstanceId) throws Exception {
        if (null == processInstanceId) {
            return AjaxResult.error("参数不能为空");
        }
        dingTalkManager.completeApproval(processInstanceId);
        return AjaxResult.success();
    }

}
