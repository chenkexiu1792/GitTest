package com.honghe.common.common.entity;

/**
 * @Author youye
 * @Date 2019/8/3 11:02
 * @Description TODO
 **/
public class MtSimple extends MtBase {

    /**
     * 数据ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 搜索关键字
     */
    private String keyword;


    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private Integer status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
