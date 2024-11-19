package com.honghe.common.common.result;

import java.util.List;

/**
 * 分页对象
 *
 * @auther Libing
 * @Time 2017/9/8 10:54
 */
public class Page<E> {
    private List<E>    dataList ;      // 存放实体类集合
    private int        currentPage ;   // 当前页
    private int        pageSize ;      // 每页显示的条数
    private int        totalPage ;     // 总页数
    private int        totalCount ;    // 总条数
    private int        offset;         // 偏移量

    public Page(){
        this.currentPage = 0;
        this.pageSize = 0;
        this.totalCount = 0;
        this.totalPage = 0;
    }

    public Page(List<E> dataList, int currentPage, int pageSize, int totalCount){
        this.dataList = dataList;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        int totalPage = totalCount / pageSize;
        if(totalCount % pageSize > 0){
            totalPage +=1;
        }
        this.totalPage = totalPage;
        this.totalCount = totalCount;
    }

    public List<E> getDataList() {
        return dataList;
    }

    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public static Page getValidPage(String page, String pageSize){
        Page newPage = new Page();

        if (null != page && null != pageSize && !"".equals(page) && !"".equals(pageSize)){
            int pageInt = Integer.valueOf(page);
            int pageSizeInt = Integer.valueOf(pageSize);

            if (pageSizeInt > 0){
                newPage.setCurrentPage(pageInt <= 0 ? 1 : pageInt);
                newPage.setPageSize(pageSizeInt);
                newPage.setOffset((newPage.getCurrentPage() - 1) * newPage.getPageSize());
            }
        }

        return newPage;
    }

    public static Page getValidPage(int page, int pageSize){
        Page newPage = new Page();
        if (page >= 0 && pageSize > 0){
            newPage.setCurrentPage(page <= 0 ? 1 : page);
            newPage.setPageSize(pageSize);
            newPage.setOffset((newPage.getCurrentPage() - 1) * newPage.getPageSize());
        }

        return newPage;
    }
}
