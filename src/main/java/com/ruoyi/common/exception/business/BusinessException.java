package com.ruoyi.common.exception.business;

import com.ruoyi.common.exception.base.BaseException;

public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String defaultMessage) {
        super(defaultMessage);
    }
}
