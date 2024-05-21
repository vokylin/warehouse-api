package com.ruoyi.project.common;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.component.ReceiveNoticeManager;
import com.ruoyi.project.common.entity.NoticeWmsReceiveDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入库通知处理
 *
 * @author Rem
 */
@Slf4j
@RequestMapping("/api/receiveNotice")
@RestController
public class ReceiveNoticeController {

    @Autowired
    private ReceiveNoticeManager receiveNoticeManager;

    /**
     * 新增入库通知推送
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResult receiveNotice(@RequestBody NoticeWmsReceiveDto noticeWmsReceiveDto) {
        if (null == noticeWmsReceiveDto) {
            return AjaxResult.error("参数不能为空");
        }
        if (null == noticeWmsReceiveDto.getReceiveDetail()) {
            return AjaxResult.error("入库明细不能为空");
        }
        log.info("仓库入库被调用 ========> noticeWmsReceiveDto:{}", JSONObject.toJSONString(noticeWmsReceiveDto));
        receiveNoticeManager.saveNotice(noticeWmsReceiveDto);
        return AjaxResult.success();
    }


    /**
     * 入库未税金额调整
     */
    @RequestMapping(value = "/adjustPrice", method = RequestMethod.POST)
    public AjaxResult receiveAdjustPrice(@RequestBody NoticeWmsReceiveDto noticeWmsReceiveDto) {
        if (null == noticeWmsReceiveDto) {
            return AjaxResult.error("调整参数不能为空");
        }
        if (null == noticeWmsReceiveDto.getReceiveDetail()) {
            return AjaxResult.error("调整物料详情不能为空");
        }
        log.info("入库未税金额调整被调用 ========> noticeWmsReceiveDto:{}", JSONObject.toJSONString(noticeWmsReceiveDto));
        receiveNoticeManager.adjustPrice(noticeWmsReceiveDto);
        return AjaxResult.success();
    }

}
