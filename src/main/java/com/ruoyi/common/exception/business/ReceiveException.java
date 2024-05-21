package com.ruoyi.common.exception.business;

import com.ruoyi.common.exception.base.BaseException;

public class ReceiveException extends BaseException {
    private static final long serialVersionUID = 1L;

    public ReceiveException(String code, Object[] args) {
        super("Receive", code, args, null);
    }


}
