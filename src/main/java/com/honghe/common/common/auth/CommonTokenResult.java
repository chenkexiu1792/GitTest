package com.honghe.common.common.auth;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 通用token返回结果
 * @author: wuxiao
 * @create: 2020-03-30 11:05
 **/
public class CommonTokenResult implements Serializable{

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户真实姓名
     */
    private String userReamlName;

    /**
     * 用户UUID
     */
    private String userUuid;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户企业标识
     */
    private String companyToken;

    /**
     * 当前用户token
     */
    private String token;

    /**
     * 时间戳
     */
    private long timestamp;


    public CommonTokenResult() {

    }

    public CommonTokenResult(String userId, String companyToken) {
        this.userId = userId;
        this.companyToken = companyToken;
    }

    public CommonTokenResult(String userId,String userType,String userName,String userRealName,String userUuid,String userPhone,String userEmail, String companyToken, String token) {
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.userReamlName = userRealName;
        this.userUuid = userUuid;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.companyToken = companyToken;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getCompanyToken() {
        return companyToken;
    }

    public void setCompanyToken(String companyToken) {
        this.companyToken = companyToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserReamlName() {
        return userReamlName;
    }

    public void setUserReamlName(String userReamlName) {
        this.userReamlName = userReamlName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "CommonTokenResult{" +
                "userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", userName='" + userName + '\'' +
                ", userReamlName='" + userReamlName + '\'' +
                ", userUuid='" + userUuid + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", companyToken='" + companyToken + '\'' +
                ", token='" + token + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
