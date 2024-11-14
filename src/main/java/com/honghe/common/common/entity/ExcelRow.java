package com.honghe.common.common.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author youye
 * @Date 2020/7/20 17:43
 * @Description TODO
 **/
public class ExcelRow {

    /**
     * 第N 行
     */
    private int row;

    /**
     * 该行的每列中的单元格内容集合
     */
    private List<String> columnTextList;


    public ExcelRow(){
        this.columnTextList = new ArrayList<>();
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public List<String> getColumnTextList() {
        return columnTextList;
    }

    public void setColumnTextList(List<String> columnTextList) {
        this.columnTextList = columnTextList;
    }
}
