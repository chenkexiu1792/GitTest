package com.honghe.common.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.honghe.common.common.auth.CommonTokenResult;
import com.honghe.common.common.constants.Constants;
import com.honghe.common.common.redis.RedisOperator;
import com.honghe.common.exception.CommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: token生成器
 * @author: wuxiao
 * @create: 2020-03-26 19:39
 **/
@Component
public class TokenUtil {
    private final static Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    @Resource
    private RedisOperator redisOperator;

    private JWTVerifier verifier;

    /**
     * JWT 生成Token
     * JWT 构成: header, payload, signature
     * @param userId 登录成功后用户ID
     * @param userUuid 登录成功后用户UUID
     * @param companyToken 用户企业标识，无的话为 00000
     * @param platform 平台标识
     * @return java.lang.String
     * @Create wuxiao 2020/3/27 14:07
     * @Update wuxiao 2020/3/27 14:07
     */
    public CommonTokenResult createToken(String userId,String userType,String userName,String userRealName,String userUuid, String userPhone,String userEmail,String companyToken,String platform) {
        // Header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        try{
            // 用户Token 存储的Key值
            String key = companyToken + "&" + userId + "&" + platform;
            long timestamp = new Date().getTime();

            // 生成用户Token
            String token = JWT.create().withHeader(map)                     // header
                              .withClaim("iss", "Service")    // payload
                              .withClaim("aud", "user")
                              .withClaim(Constants.TOKEN_CLAIM_KEY, key + "_" + timestamp)  // 添加时间戳，每次生成动态的用户Token
                              .sign(Algorithm.HMAC256(Constants.TOKEN_SECRET));   // signature

            CommonTokenResult result = new CommonTokenResult(userId,userType,userName,userRealName,userUuid,userPhone,userEmail, companyToken, token);
            result.setTimestamp(timestamp);

            String value = JSON.toJSONString(result);
            redisOperator.expireSet(key,value, Constants.TOKEN_EXPIRES_SECOND);
            //redisOperator.expireSet(key,value, Constants.TOKEN_EXPIRES_SECOND);
            return result;

        }catch (Exception e){
            logger.error("token生成失败", e.getLocalizedMessage());
            throw new CommandException("token生成失败");
        }
    }

    public CommonTokenResult getToken(String token) {
        return new CommonTokenResult("0","","","",null, null,null,token, token);
    }

    /**
     * 用户Token 校验
     * @param token companyToken&userId
     * @return com.honghe.common.common.auth.CommonTokenResult
     * @Create wuxiao 2020/3/30 11:37
     * @Update wuxiao 2020/3/30 11:37
     */
    public CommonTokenResult checkToken(String token) {
        if (null == token || "".equals(token)) {
            return null;
        }

        try {
            if (verifier == null) {
                Algorithm algorithm = Algorithm.HMAC256(Constants.TOKEN_SECRET);
                verifier = JWT.require(algorithm).build(); //Reusable verifier instance
            }
            DecodedJWT jwt = verifier.verify(token);

            String key = jwt.getClaims().get(Constants.TOKEN_CLAIM_KEY).asString();
            if (key.contains("_")) {
                key = key.split("_")[0];
            }

            String value = redisOperator.getString(key);
            if (null != value){
                // 重置token
                this.resetTokenTime(token);
                return (CommonTokenResult) this.convertMap(CommonTokenResult.class, JSONObject.parseObject(value));
            }
        } catch (Exception e) {
            logger.error("【校验用户Token】发生异常 : ", e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 重置用户Token
     * @param token 用户Token
     * @return void
     * @Create wuxiao 2020/3/30 14:03
     * @Update wuxiao 2020/3/30 14:03
     */
    public void resetTokenTime(String token) {
        if (null == token || "".equals(token)){
            return;
        }

        try {
            if (null == verifier) {
                Algorithm algorithm = Algorithm.HMAC256(Constants.TOKEN_SECRET);
                verifier = JWT.require(algorithm).build(); //Reusable verifier instance
            }

            DecodedJWT jwt = verifier.verify(token);
            String key = jwt.getClaims().get(Constants.TOKEN_CLAIM_KEY).asString();

            if (key.contains("_")) {
                key = key.split("_")[0];
            }
            String value = redisOperator.getString(key);
            if (null == value){
                throw new RuntimeException("重置用户Token 时长失败!");
            }

            redisOperator.expireSet(key, value, Constants.TOKEN_EXPIRES_SECOND);
        } catch (Exception e) {
            logger.error("checkToken error : {}", e.getLocalizedMessage());
        }
    }

    /**
     * 删除用户Token
     * @param userId 用户ID
     * @param companyToken 企业标识
     * @param platform 平台标识
     * @return java.lang.Boolean
     * @Create wuxiao 2020/3/30 14:06
     * @Update wuxiao 2020/3/30 14:06
     */
    public Boolean deleteToken(String userId, String companyToken,String platform) {

        Boolean reValue = false;
        if (!ParamUtil.isEmpty(userId,companyToken,platform)){
            try {
                String key = companyToken + "&" + userId + "&"+ platform;
                redisOperator.del(key);
                reValue = true;
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
            }
        }

        return reValue;
    }


    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param type 要转化的类型
     * @param map  包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings("rawtypes")
    public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

}
