package com.ruoyi.common.invoker.entity.request;

import lombok.Data;

@Data
public class AccessTokenRequest {

    public String appKey;

    public String appSecret;

    public AccessTokenRequest(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
    }
}
