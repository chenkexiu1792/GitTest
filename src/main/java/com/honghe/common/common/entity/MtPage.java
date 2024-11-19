package com.honghe.common.common.entity;

/**
 * @Author youye
 * @Date 2020/3/23 10:33
 * @Description TODO
 **/
public class MtPage extends MtBase {

    /**
     * 搜索内容
     */
    private String keyword;

    /**
     * 类型
     */
    private String type;

    /**
     * 当前页码
     *
     */
    private int page;

    /**
     * 单页记录数量
     *
     */
    private int pageSize;

    /**
     * 排序字段
     *
     */
    private String sortName;

    /**
     * 排序方向
     *
     */
    private String sortOrder;


    public MtPage() {
        this.keyword = null;
        this.page = 0;
        this.pageSize = 20;
        this.sortName = "";
        this.sortOrder = "";
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
