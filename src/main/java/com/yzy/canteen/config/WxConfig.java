package com.yzy.canteen.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yzy
 * @create: 2018-04-03 15:18
 */
@ConfigurationProperties(prefix = "weixin")
@Component
@Data
public class WxConfig{
    private String appSecret;

    private String appId;

    private String mchId;

    private String key;

    private String certPath;

    private String notifyUrl;

    private String openAppId;

    private String openAppSecret;



}
