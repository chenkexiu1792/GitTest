package com.honghe.common.common.entity;

/**
 * @Author youye
 * @Date 2020/7/21 13:41
 * @Description TODO
 **/
public class ExcelTitle {

    /**
     * 标题
     */
    private String title;

    /**
     * 是否居中显示
     */
    private boolean showCenter;

    /**
     * 列内容最大宽度
     */
    private int colummTextMaxWidth;

    private int titleWidthMultiple;

    public ExcelTitle(String title,boolean showCenter){
        this.title = title;
        this.showCenter = showCenter;
        this.colummTextMaxWidth = 0;
        this.titleWidthMultiple = 1;
    }

    public ExcelTitle(String title,boolean showCenter,int titleWidthMultiple){
        this.title = title;
        this.showCenter = showCenter;
        this.colummTextMaxWidth = 0;
        this.titleWidthMultiple = titleWidthMultiple;
    }

    public ExcelTitle(String title,boolean showCenter,int colummTextMaxWidth,int titleWidthMultiple){
        this.title = title;
        this.showCenter = showCenter;
        this.colummTextMaxWidth = colummTextMaxWidth;
        this.titleWidthMultiple = titleWidthMultiple;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShowCenter() {
        return showCenter;
    }

    public void setShowCenter(boolean showCenter) {
        this.showCenter = showCenter;
    }

    public int getColummTextMaxWidth() {
        return colummTextMaxWidth;
    }

    public void setColummTextMaxWidth(int colummTextMaxWidth) {
        this.colummTextMaxWidth = colummTextMaxWidth;
    }

    public int getTitleWidthMultiple() {
        return titleWidthMultiple;
    }

    public void setTitleWidthMultiple(int titleWidthMultiple) {
        this.titleWidthMultiple = titleWidthMultiple;
    }
}
