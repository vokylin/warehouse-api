package com.ruoyi.project.warehouse.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.PrintList;
import com.ruoyi.project.warehouse.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warehouse")
public class PrintController {

    @Autowired
    private PrintService printService;

    @GetMapping(value = "/print")
    public AjaxResult print(Integer documentType, String noticeId) {
        PrintList printInfo = printService.getPrintInfo(documentType, noticeId);
        if (null == printInfo || printInfo.size() == 0) {
            return AjaxResult.error("打印数据为空");
        }
        return AjaxResult.success(printInfo);
    }

    /**
     * 报损单打印
     *
     * @param noticeId
     * @return
     */
    @GetMapping(value = "/breakagePrint/{noticeId}")
    public AjaxResult breakagePrint(@PathVariable String noticeId) {
        return AjaxResult.success(printService.getBreakageDocPrintInfo(noticeId));
    }

    /**
     * 进料验收单打印
     *
     * @param examineWorkId
     * @return
     */
    @GetMapping(value = "/feedstockPrint/{examineWorkId}")
    public AjaxResult feedstockPrint(@PathVariable String examineWorkId) throws Exception {
        return AjaxResult.success(printService.getFeedstockPrintInfo(examineWorkId));
    }
}
