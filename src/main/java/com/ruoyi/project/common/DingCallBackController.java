package com.ruoyi.project.common;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.project.common.component.DingTalkManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 钉钉回调接口
 *
 * @author REM
 * @date 2023/05/23
 */
@Slf4j
@RestController
@RequestMapping({"/dingCallBack"})
public class DingCallBackController {

    @Autowired
    private DingTalkManager dingTalkManager;

    @RequestMapping
    public Map<String, String> callBack(
            @RequestParam(value = "msg_signature", required = false) String msgSignature,
            @RequestParam(value = "timestamp", required = false) String timeStamp,
            @RequestParam(value = "nonce", required = false) String nonce,
            @RequestBody(required = false) JSONObject json) throws Exception {
        return dingTalkManager.callBackDispose(msgSignature, timeStamp, nonce, json);
    }


}
