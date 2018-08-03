package com.yzy.canteen.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-23 23:41
 */
@Slf4j
public class List2StringUtil {
//    public static void main(String[] args){
//        String[] arr = new String[]{};
//        List<String> list=Arrays.asList(arr);
//        Collections.sort(list);
//    }
    public static String getStr(List list){
        if(list.size()==0) {
            log.error("list为空");
            return null;
        }
        String str=list.toString().replaceAll(" ","");
        String result=str.substring(1,str.length()-1);
        return result;
    }
    public static List<String> getList(String str){
        String[] strArr=str.split(",");
        if(strArr.length==0){
            log.error("字符数组为空");
            return null;
        }
        List<String> list=Arrays.asList(strArr);
        Collections.sort(list);
        System.out.println(list);
        return list;

    }
}
