package com.yzy.canteen.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yzy.canteen.config.WxConfig;
import com.yzy.canteen.constant.TokenConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @description: 存取access_token
 * @author: yzy
 * @create: 2018-04-03 15:47
 */
@Slf4j
@Component
public class AccessTokenUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private WxConfig wxConfig;

    public String getToken(){
        String appId=wxConfig.getAppId();
        String appSecret=wxConfig.getAppSecret();

        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appSecret;
        String access_token=redisTemplate.opsForValue().get("access_token");
        if(access_token==null){
            log.info("token==null");
            RestTemplate restTemplate=new RestTemplate();
            String response=restTemplate.getForObject(url,String.class);
            JsonObject jsonObject=new JsonParser().parse(response).getAsJsonObject();
            access_token=jsonObject.get("access_token").getAsString();
            Integer expire = TokenConstant.EXPIRE;
            redisTemplate.opsForValue().set("access_token",access_token, 7100, TimeUnit.SECONDS);
        }
        return access_token;
    }
}
