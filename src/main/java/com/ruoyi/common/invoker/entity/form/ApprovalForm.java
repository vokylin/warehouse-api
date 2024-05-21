package com.ruoyi.common.invoker.entity.form;


import com.alibaba.fastjson2.JSON;

import java.util.ArrayList;

/**
 * 表单组件值
 *
 * @author zyh
 * @date 2022/07/15
 */
public class ApprovalForm extends ArrayList<BaseForm> {

    /**
     * 添加普通表单
     *
     * @param name  名字
     * @param value 价值
     */
    public void addForm(String name, String value) {
        this.add(new BaseForm(name, value));
    }

    public void addTableForm(String name, TableForm tableForm) {
        this.add(new BaseForm(name, tableForm));
    }


    /*
        图片表单格式
     {
        "name": "图片",
        "value": ["http://url1","http://url2","http://url3"]
      },
     */

    /**
     * 添加图像表单 单张图片
     *
     * @param name     名字
     * @param imageUrl 图像url
     */
    public void addImageForm(String name, String imageUrl) {
        String[] imageArray = {imageUrl};
        String imageArrayJson = JSON.toJSONString(imageArray);
        this.add(new BaseForm(name, imageArrayJson));
    }

    /**
     * 添加图像表单 多张图片
     *
     * @param name     名字
     * @param imageUrl 图像url
     */
    public void addImageForm(String name, String[] imageUrl) {
        String imageArrayJson = JSON.toJSONString(imageUrl);
        this.add(new BaseForm(name, imageArrayJson));
    }


     /*
       时间表单格式
       {
         "name": "[\"开始时间\",\"结束时间\"]",
         "value": "[\"2019-02-19\",\"2019-02-25\"]"
        }
     */

    /**
     * 添加时间表单
     *
     * @param startTimeLabelName  开始时间标签名称
     * @param startTimeLabelValue 开始时间标签值
     * @param endTimeLabelName    结束时间标签名称
     * @param endTimeLabelValue   结束时间标签值
     */
    public void addTimeForm(String startTimeLabelName, String startTimeLabelValue, String endTimeLabelName, String endTimeLabelValue) {
        TimeForm timeForm = new TimeForm(startTimeLabelName, startTimeLabelValue, endTimeLabelName, endTimeLabelValue);
        this.add(timeForm.getFormComponentValue());
    }
}
