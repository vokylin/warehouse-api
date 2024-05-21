package com.ruoyi.common.invoker.entity.form;

import com.alibaba.fastjson2.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 时间组件表单对象
 *
 * @author REM
 * @date 2023/05/17
 */
@Data
public class TimeForm {


    /**
     * 标签名称
     */
    public List<String> labelName;

    /**
     * 标签值
     */
    public List<String> labelValue;


    /*
       时间表单格式
       {
         "name": "[\"开始时间\",\"结束时间\"]",
         "value": "[\"2019-02-19\",\"2019-02-25\"]"
        }
     */

    /**
     * 时间组件
     *
     * @param startTimeLabelName  开始时间标签名称
     * @param startTimeLabelValue 开始时间标签值
     * @param endTimeLabelName    结束时间标签名称
     * @param endTimeLabelValue   结束时间标签值
     */
    public TimeForm(String startTimeLabelName, String startTimeLabelValue, String endTimeLabelName, String endTimeLabelValue) {

        this.labelName = new ArrayList<>();
        this.labelName.add(startTimeLabelName);
        this.labelName.add(endTimeLabelName);

        this.labelValue = new ArrayList<>();
        this.labelValue.add(startTimeLabelValue);
        this.labelValue.add(endTimeLabelValue);
    }

    /**
     * 获得表单组件值
     *
     * @return {@link BaseForm}
     */
    public BaseForm getFormComponentValue() {
        return new BaseForm(JSON.toJSONString(labelName), JSON.toJSONString(labelValue));
    }
}
