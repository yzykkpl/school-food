package com.yzy.canteen.aspect;

import com.yzy.canteen.Excetion.SellerAuthorizeException;
import com.yzy.canteen.constant.CookieConstant;
import com.yzy.canteen.constant.TokenConstant;
import com.yzy.canteen.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: 卖家AOP验证
 * @author: yzy
 * @create: 2018-04-02 11:45
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.yzy.canteen.controller.Seller*.*(..))"+
            "&& !execution(public * com.yzy.canteen.controller.SellerUserController.login(..))")
    public void verify(){}


    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        //查询cookie
        Cookie cookie = CookieUtil.get(req, CookieConstant.TOKEN);
        if(cookie==null){
            log.warn("登录校验，cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        //去redis里查询
        String tokenValue=redisTemplate.opsForValue().get(String.format(TokenConstant.TOKEN_PREFIX,cookie.getValue()));
        if(tokenValue.isEmpty()){
            log.warn("登录校验，Redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }

}
