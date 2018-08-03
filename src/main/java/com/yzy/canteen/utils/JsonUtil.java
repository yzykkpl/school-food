package com.yzy.canteen.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @description:
 * @author: yzy
 * @create: 2018-04-04 20:56
 */
public class JsonUtil {
    public static String toJson(Object object){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson =gsonBuilder.create();
        return gson.toJson(object);
    }
}
