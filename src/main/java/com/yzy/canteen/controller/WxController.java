package com.yzy.canteen.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.config.ProjectUrlConfig;
import com.yzy.canteen.config.WxConfig;
import com.yzy.canteen.constant.TokenConstant;
import com.yzy.canteen.dataobject.UserInfo;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.form.UserForm;
import com.yzy.canteen.repository.UserInfoRepository;
import com.yzy.canteen.service.BuyerService;
import com.yzy.canteen.utils.ResultVOUtil;
import com.yzy.canteen.viewobject.ResultVO;
import lombok.extern.slf4j.Slf4j;



import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description: 微信
 * @author: yzy
 * @create: 2018-04-03 12:48
 */
@RestController
@RequestMapping("/wechat")
@Slf4j
public class WxController {
    @Autowired
    private WxConfig wxConfig;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserInfoRepository repository;
    @Autowired
    BuyerService buyerService;



    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    /**
     * 拿openid并存入redis
     * @param:
     */
    @PostMapping("/auth")
    public ResultVO<Map<String,String>> auth(@RequestParam("code") String code,@RequestParam(value="nickName",defaultValue = "undefined") String nickName){
        log.info("code={},nickName={}",code,nickName);
        String url="https://api.weixin.qq.com/sns/jscode2session?appid="+wxConfig.getAppId()+"&secret="+wxConfig.getAppSecret()+"&js_code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate=new RestTemplate();
        String response=restTemplate.getForObject(url,String.class);
        JsonObject json = new JsonParser().parse(response).getAsJsonObject();
        log.info("user--"+json);
        String openid=json.get("openid").getAsString();
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(String.format(TokenConstant.TOKEN_PREFIX,token),openid,TokenConstant.EXPIRE,TimeUnit.SECONDS);
        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        return ResultVOUtil.success(map);
    }
    //客户端用token登录，后台通过token查openid

    @GetMapping("/login")
    public ResultVO<Map<String,UserForm>> login(@RequestParam("token") String token){
        log.info("token={}",token);
        String key=String.format(TokenConstant.TOKEN_PREFIX,token);
        String openid=redisTemplate.opsForValue().get(key);
        if(openid==null) return ResultVOUtil.error(-1,"relogin");
        UserInfo userInfo = repository.findByOpenid(openid);
        if(userInfo==null){
            return ResultVOUtil.error(-2,"new user");
        }
        UserForm userForm=new UserForm();
        BeanUtils.copyProperties(userInfo,userForm);
        userForm.setToken(token);
        Map<String,UserForm> map=new HashMap<>();
        map.put("userInfo",userForm);
        redisTemplate.expire(key,TokenConstant.EXPIRE,TimeUnit.SECONDS);
        return ResultVOUtil.success(map);


    }
    @PostMapping("/register")
    public ResultVO<Map<String,UserForm>> register(@Valid UserForm userForm,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【用户注册】参数不正确，userForm={}",userForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }
        log.info("【userInfo】:"+userForm.getName());
        Map<String,UserForm> map=new HashMap<>();
        map.put("userInfo",userForm);
        String token=userForm.getToken();
        String key=String.format(TokenConstant.TOKEN_PREFIX,token);
        String openid = redisTemplate.opsForValue().get(key);
        UserInfo userInfo=repository.findByOpenid(openid);
        //如果用户已存在，返回用户信息
        if(userInfo!=null){
            return ResultVOUtil.success(map);
        }
        userInfo=new UserInfo();
        BeanUtils.copyProperties(userForm,userInfo);
        userInfo.setOpenid(openid);
        UserInfo result = repository.save(userInfo);
        if(result==null){
                log.error("用户注册失败，stdNum={}",userForm.getName());
                throw new SellException(ResultEnum.REGISTER_FAIL);
        }
        return ResultVOUtil.success(map);
    }
    @PostMapping("edit")
    public ResultVO<Map<String,UserForm>> edit(@Valid UserForm userForm,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【用户信息修改】参数不正确，userForm={}",userForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }
        Map<String,UserForm> map=new HashMap<>();
        map.put("userInfo",userForm);
        String token=userForm.getToken();
        String key=String.format(TokenConstant.TOKEN_PREFIX,token);
        String openid = redisTemplate.opsForValue().get(key);
        UserInfo userInfo=repository.findByOpenid(openid);
        if(userInfo==null){
            log.error("【用户信息修改】用户不存在，openid={}",openid);
            return ResultVOUtil.error(-1,"user existed");
        }
        BeanUtils.copyProperties(userForm,userInfo);
        UserInfo result = repository.save(userInfo);
        if(result==null){
            log.error("【用户信息修改】修改失败，name={}",userForm.getName());
            throw new SellException(ResultEnum.REGISTER_FAIL);
        }
        return ResultVOUtil.success(map);

    }


//    //后台管理员网页登录
//    @GetMapping("/qrAuthorize")
//    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
//        String url = projectUrlConfig.getWechatOpenAuthorize() + "/canteen/wechat/qrUserInfo";
//        String redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QrConnectScope.SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
//        return "redirect:" + redirectUrl;
//    }
//
//    @GetMapping("/qrUserInfo")
//    public String qrUserInfo(@RequestParam("code") String code,
//                             @RequestParam("state") String returnUrl) {
//        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
//        try {
//            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
//        } catch (WxErrorException e) {
//            log.error("【微信网页授权】{}", e);
//            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
//        }
//        log.info("wxMpOAuth2AccessToken={}", wxMpOAuth2AccessToken);
//        String openId = wxMpOAuth2AccessToken.getOpenId();
//
//        return "redirect:" + returnUrl + "?openid=" + openId;
//    }

}
