package com.yzy.canteen.config;

import com.yzy.canteen.utils.payUtil.config.WxPayH5Config;
import com.yzy.canteen.utils.payUtil.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description: 微信支付配置
 * @author: yzy
 * @create: 2018-04-04 20:29
 */
@Component
public class WxPayConfig {
    @Autowired
    private WxConfig wxConfig;

    @Bean
    public BestPayServiceImpl bestPayService(){
        BestPayServiceImpl bestPayService=new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayH5Config(){
        WxPayH5Config wxPayH5Config=new WxPayH5Config();
        wxPayH5Config.setAppId(wxConfig.getAppId());
        wxPayH5Config.setAppSecret(wxConfig.getAppSecret());
        wxPayH5Config.setMchId(wxConfig.getMchId());
        wxPayH5Config.setMchKey(wxConfig.getKey());
        wxPayH5Config.setKeyPath(wxConfig.getCertPath());
        wxPayH5Config.setNotifyUrl(wxConfig.getNotifyUrl());
        return wxPayH5Config;
    }
}
