package com.ruoyi.common.invoker.entity.form;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TableForm extends ArrayList<RowData> {

    public void addRow(RowData row) {
        this.add(row);
    }
}
