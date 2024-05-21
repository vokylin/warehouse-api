package com.ruoyi.common.invoker.entity.form;

import java.util.ArrayList;

public class RowData extends ArrayList<BaseForm> {
    public void addData(String name, String value) {
        this.add(new BaseForm(name, value));
    }

    public void addTimeForm(String startTimeLabelName, String startTimeLabelValue, String endTimeLabelName, String endTimeLabelValue) {
        TimeForm timeForm = new TimeForm(startTimeLabelName, startTimeLabelValue, endTimeLabelName, endTimeLabelValue);
        this.add(timeForm.getFormComponentValue());
    }
}
