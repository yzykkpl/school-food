package com.yzy.canteen.Excetion;


import lombok.Getter;
import com.yzy.canteen.enums.ResultEnum;

/**
 * @description:
 * @author: yzy
 * @create: 2018-03-25 19:43
 */

public class SellException extends RuntimeException{
    @Getter
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}