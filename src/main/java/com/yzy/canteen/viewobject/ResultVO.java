package com.yzy.canteen.viewobject;

import lombok.Data;

/**
 * @description:http请求的最外层对象
 * @author: yzy
 * @create: 2018-03-25 11:26
 */
@Data
public class ResultVO<T> {
    //错误码
    private Integer code;
    //信息
    private String msg;
    //具体内容
    private T data;
}
