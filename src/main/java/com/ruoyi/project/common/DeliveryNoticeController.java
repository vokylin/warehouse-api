package com.ruoyi.project.common;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.component.DeliveryNoticeManager;
import com.ruoyi.project.common.entity.NoticeWmsDeliveryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 出库通知处理
 *
 * @author Rem
 */
@Slf4j
@RequestMapping("/api/deliveryNotice")
@RestController
public class DeliveryNoticeController {

    @Autowired
    private DeliveryNoticeManager deliveryNoticeManager;

    /**
     * 新增出库通知推送
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResult receiveNotice(@RequestBody NoticeWmsDeliveryDto noticeWmsDeliveryDto) {

        if (null == noticeWmsDeliveryDto) {
            return AjaxResult.error("参数不能为空");
        }
        if (null == noticeWmsDeliveryDto.getDeliveryDetailList()) {
            return AjaxResult.error("出库明细不能为空");
        }
        log.info("仓库出库接口被调用 ========> noticeWmsReceiveDto:{}", JSONObject.toJSONString(noticeWmsDeliveryDto));
        deliveryNoticeManager.saveNotice(noticeWmsDeliveryDto);
        return AjaxResult.success();
    }


}
