package com.honghe.common.common.entity;

/**
 * @Author youye
 * @Date 2019/6/11 13:33
 * @Description TODO
 **/
public class DBEntity {

    /**
     * 企业标识
     */
    private String token;

    /**
     * 基础数据库名称
     */
    private String baseDbName;

    /**
     * 会议管理数据库名称
     */
    private String meetingDbName;

    public DBEntity(){
        this.baseDbName = "hht_baseservice";
        this.meetingDbName = "hht_meetingservice";
    }

    public DBEntity(String token){
        this.token = token;
        //this.baseDbName = "hht_baseservice_" + token;
        this.baseDbName = "hht_baseservice_" + token;
        this.meetingDbName = "hht_meetingservice_" + token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBaseDbName() {
        return baseDbName;
    }

    public void setBaseDbName(String baseDbName) {
        this.baseDbName = baseDbName;
    }

    public String getMeetingDbName() {
        return meetingDbName;
    }

    public void setMeetingDbName(String meetingDbName) {
        this.meetingDbName = meetingDbName;
    }
}
