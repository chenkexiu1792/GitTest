package com.honghe.common.common.auth.service.impl;

import com.honghe.common.common.auth.CommonTokenResult;
import com.honghe.common.common.auth.service.TokenService;
import com.honghe.common.common.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: ${description}
 * @author: wuxiao
 * @create: 2020-03-30 11:16
 **/
@Transactional
@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
    private TokenUtil tokenUtil;

    /**
     * 检查token是否有效
     *
     * @param token
     * @return com.honghe.common.common.auth.CommonTokenResult
     * @Create wuxiao 2020/3/30 11:12
     * @Update wuxiao 2020/3/30 11:12
     */
    @Override
    public CommonTokenResult checkToken(String token) {
        return tokenUtil.checkToken(token);
    }

    /**
     * 重置token的失效时间
     *
     * @param token
     * @return void
     * @Create wuxiao 2020/3/30 11:12
     * @Update wuxiao 2020/3/30 11:12
     */
    @Override
    public void resetTokenTime(String token) {
        tokenUtil.resetTokenTime(token);
    }

    /**
     * 删除用户Token
     * @param userId       用户ID
     * @param companyToken 企业标识
     * @return void
     * @Create wuxiao 2020/3/30 11:13
     * @Update wuxiao 2020/3/30 11:13
     */
    @Override
    public boolean deleteToken(String userId, String companyToken,String platform) {
        return tokenUtil.deleteToken(userId,companyToken,platform);
    }
}
