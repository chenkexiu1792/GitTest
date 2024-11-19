package com.honghe.common.common.auth.service;

import com.honghe.common.common.auth.CommonTokenResult;

/**
 * @description: token验证
 * @author: wuxiao
 * @create: 2020-03-30 11:03
 **/
public interface TokenService {

    /**
     * 检查用户Token 是否有效
     * @param token 用户Token
     * @return com.honghe.common.common.auth.CommonTokenResult
     * @Create wuxiao 2020/3/30 11:12
     * @Update wuxiao 2020/3/30 11:12
     */
    CommonTokenResult checkToken(String token);

    /**
     * 重置用户Token的失效时间
     * @param token 用户Token
     * @return void
     * @Create wuxiao 2020/3/30 11:12
     * @Update wuxiao 2020/3/30 11:12
     */
    void resetTokenTime(String token);

    /**
     * 删除用户Token
     * @param userId 用户ID
     * @param companyToken 企业标识
     * @return void
     * @Create wuxiao 2020/3/30 11:13
     * @Update wuxiao 2020/3/30 11:13
     */
    boolean deleteToken(String userId, String companyToken,String platform);
}
