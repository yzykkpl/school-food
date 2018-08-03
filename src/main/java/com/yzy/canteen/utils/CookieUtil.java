package com.yzy.canteen.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: Cookie工具类
 * @author: yzy
 * @create: 2018-04-01 19:29
 */
public class CookieUtil {
    public static void set(HttpServletResponse resp,String name,String value,int maxAge){
        Cookie cookie =new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        resp.addCookie(cookie);
    }

    public static Cookie get(HttpServletRequest req,String name){
        Map<String, Cookie> cookieMap = readCookieMap(req);
        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }else{
            return null;
        }

    }
    /**
     * 将cookie封装成Map
     * @param:
     */
    private static Map<String,Cookie> readCookieMap(HttpServletRequest req){
        Map<String,Cookie> cookieMap=new HashMap<>();
        Cookie[] cookies=req.getCookies();
        if(cookies!=null){
            for (Cookie cookie:cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
}
