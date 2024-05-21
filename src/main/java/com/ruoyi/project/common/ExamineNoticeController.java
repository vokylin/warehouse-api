package com.ruoyi.project.common;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.enums.ExamineStatus;
import com.ruoyi.common.enums.ReciveItemStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.entity.ExamineTaskNoticeWmsDTO;
import com.ruoyi.project.warehouse.domain.WmsExamineWork;
import com.ruoyi.project.warehouse.domain.WmsReceiveItemDetail;
import com.ruoyi.project.warehouse.service.IWmsExamineWorkService;
import com.ruoyi.project.warehouse.service.IWmsReceiveItemDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 质检结果接收
 *
 * @author Rem
 */
@Slf4j
@RequestMapping("/api/examineNotice")
@RestController
public class ExamineNoticeController {

    @Autowired
    private IWmsExamineWorkService wmsExamineWorkService;

    @Autowired
    private IWmsReceiveItemDetailService wmsReceiveItemDetailService;

    /**
     * 质检结果接收 更新
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public AjaxResult receiveNotice(@RequestBody ExamineTaskNoticeWmsDTO examineTaskNoticeWmsDTO) {
        log.info("仓库接收质检结果被调用 ========> examineTaskNoticeWmsDTO:{}", JSONObject.toJSONString(examineTaskNoticeWmsDTO));

        WmsExamineWork wmsExamineWork = wmsExamineWorkService.selectWmsExamineWorkById(examineTaskNoticeWmsDTO.getWmsExamineId());
        if (null == wmsExamineWork) {
            return AjaxResult.error("质检结果不存在");
        }

        Integer resultStatus = Integer.valueOf(examineTaskNoticeWmsDTO.getResultStatus());
        // 更新质检结果
        wmsExamineWork.setId(examineTaskNoticeWmsDTO.getWmsExamineId());
        wmsExamineWork.setAllQuantity(examineTaskNoticeWmsDTO.getAllQuantity());
        wmsExamineWork.setSampleQuantity(examineTaskNoticeWmsDTO.getSampleQuantity());
        wmsExamineWork.setKeepQuantity(examineTaskNoticeWmsDTO.getKeepQuantity());
        wmsExamineWork.setFailQuantity(examineTaskNoticeWmsDTO.getFailQuantity());
        wmsExamineWork.setScrapQuantity(examineTaskNoticeWmsDTO.getScrapQuantity());
        wmsExamineWork.setPassQuantity(examineTaskNoticeWmsDTO.getPassQuantity());
        wmsExamineWork.setSampleRefundQuantity(examineTaskNoticeWmsDTO.getSampleRefundQuantity());
        wmsExamineWork.setExamineBy(examineTaskNoticeWmsDTO.getExaminerName());
        wmsExamineWork.setExamineManager(examineTaskNoticeWmsDTO.getExamineManager());
        wmsExamineWork.setStatus(resultStatus);
        wmsExamineWork.setUpdateTime(DateUtils.getNowDate());
        wmsExamineWorkService.updateWmsExamineWork(wmsExamineWork);

        // 更新分配详情上的质检状态 数量
        WmsExamineWork examineWork = wmsExamineWorkService.selectWmsExamineWorkById(examineTaskNoticeWmsDTO.getWmsExamineId());
        WmsReceiveItemDetail wmsReceiveItemDetail = wmsReceiveItemDetailService.selectWmsReceiveItemDetailById(examineWork.getReceiveItemDetailId());
        if (null == wmsReceiveItemDetail) {
            return AjaxResult.error("收货明细不存在");
        }

        WmsReceiveItemDetail updateDetail = new WmsReceiveItemDetail();
        updateDetail.setId(wmsReceiveItemDetail.getId());
        if (resultStatus.equals(ExamineStatus.QUALIFIED.getCode())) {
            updateDetail.setStatus(ReciveItemStatus.QUALIFIED.getCode());
        } else if (resultStatus.equals(ExamineStatus.UNQUALIFIED.getCode())) {
            updateDetail.setStatus(ReciveItemStatus.UNQUALIFIED.getCode());
        }
        updateDetail.setPassQuantity(examineTaskNoticeWmsDTO.getPassQuantity());
        updateDetail.setFailQuantity(examineTaskNoticeWmsDTO.getFailQuantity());
        updateDetail.setUpdateTime(DateUtils.getNowDate());
        wmsReceiveItemDetailService.updateWmsReceiveItemDetail(updateDetail);
        return AjaxResult.success();
    }


}
