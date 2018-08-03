package com.yzy.canteen.enums;

import lombok.Getter;

/**
 * @description:
 * @author: yzy
 * @create: 2018-03-25 17:12
 */
@Getter
public enum OrderStatusEnum implements CodeEnum {
    NEW(0, "新订单"),
    FINISHED(1, "已完结"),
    CANCEL(2, "已取消"),
    APPLY_FOR_REFUND(3,"申请退款"),
    REFUNDED(4,"已退款");


    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}