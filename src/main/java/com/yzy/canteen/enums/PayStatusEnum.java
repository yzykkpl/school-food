package com.yzy.canteen.enums;

/**
 * @description:
 * @author: yzy
 * @create: 2018-03-25 19:40
 */
import lombok.Getter;

@Getter
public enum PayStatusEnum implements CodeEnum {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    REFUNDED(2,"已退款");

    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
