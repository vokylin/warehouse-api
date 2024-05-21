package com.ruoyi.common.invoker.entity.form;

import com.alibaba.fastjson2.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 通用表单组件对象
 *
 * @author REM
 * @date 2023/05/17
 */
@Data
public class BaseForm {

    /**
     * 名称
     */
    public String name;

    /**
     * 值
     */
    public String value;

    /**
     * 表格详情
     */
    public List<BaseForm> details;

    /**
     * 表单组件值
     *
     * @param name  名称
     * @param value 值
     */
    public BaseForm(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 表单组件值
     *
     * @param name    名字
     * @param value   价值
     * @param details 细节
     */
    public BaseForm(String name, TableForm tableForm) {
        this.name = name;
        this.value = JSON.toJSONString(tableForm);
        this.details = new ArrayList<>();
        tableForm.forEach(rowData -> this.details.addAll(rowData));
    }

}
