package com.yzy.canteen.utils;

/**
 * @description:
 * @author: yzy
 * @create: 2018-04-04 21:26
 */
public class MathUtil {
    /**
     * 比较两个double是否相等
     * @param:
     */
    public static Boolean equals(Double d1,Double d2){
        Double result=Math.abs(d1-d2);
        return result<0.01?true:false;
    }
}
